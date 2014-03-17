package org.danielli.xultimate.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.HeuristicCompletionException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

public class RoutingDataSourceTransactionManager implements PlatformTransactionManager {

	private final static Logger LOGGER = LoggerFactory.getLogger(RoutingDataSourceTransactionManager.class);

	private final Map<String, PlatformTransactionManager> transactionManagers;
	private PlatformTransactionManager checkedTransactionManager;

	/**
	 * Creates a new {@link CopyOfCopyOfChainedTransactionManager} delegating to the given {@link PlatformTransactionManager}s.
	 * 
	 * @param transactionManagers must not be {@literal null} or empty.
	 */
	public RoutingDataSourceTransactionManager(Map<String, PlatformTransactionManager> transactionManagers, PlatformTransactionManager checkedTransactionManager) {
		Assert.notEmpty(transactionManagers, "this map transactionManagers must not be empty; it must contain at least one entry");
		Assert.notNull(checkedTransactionManager, "CheckedTransactionManager must not be null!");

		this.transactionManagers = transactionManagers;
		this.checkedTransactionManager = checkedTransactionManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.transaction.PlatformTransactionManager#getTransaction(org.springframework.transaction.TransactionDefinition)
	 */
	@SuppressWarnings("unchecked")
	public MultiTransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {

		MultiTransactionStatus mts = (MultiTransactionStatus) TransactionSynchronizationManager.getResource("mts");

		if (!TransactionSynchronizationManager.isSynchronizationActive()) {
			TransactionSynchronizationManager.initSynchronization();
			mts = new MultiTransactionStatus(checkedTransactionManager);
			TransactionSynchronizationManager.bindResource("mts", mts);
			mts.setNewSynchonization();
			TransactionSynchronizationManager.bindResource("platformTransactionManagers", new LinkedHashSet<PlatformTransactionManager>());
			TransactionSynchronizationManager.bindResource("count", 0);
		}

		
		try {
			PlatformTransactionManager platformTransactionManager = transactionManagers.get(RoutingDataSourceUtils.getRoutingDataSourceKey());
			if (platformTransactionManager == null) {
				((LinkedHashSet<PlatformTransactionManager>) TransactionSynchronizationManager.getResource("platformTransactionManagers")).add(checkedTransactionManager);
				mts.registerTransactionManager(definition, checkedTransactionManager);
			} else {
				Integer count = (Integer) TransactionSynchronizationManager.getResource("count");
				TransactionSynchronizationManager.unbindResource("count");
				TransactionSynchronizationManager.bindResource("count", count + 1);
				((LinkedHashSet<PlatformTransactionManager>) TransactionSynchronizationManager.getResource("platformTransactionManagers")).add(platformTransactionManager);
				mts.registerTransactionManager(definition, platformTransactionManager);
			}
		} catch (Exception ex) {
			if (((Integer) TransactionSynchronizationManager.getResource("count")).compareTo(0) == 0) {
				Map<PlatformTransactionManager, TransactionStatus> transactionStatuses = mts.getTransactionStatuses();

				for (PlatformTransactionManager transactionManager : ((LinkedHashSet<PlatformTransactionManager>) TransactionSynchronizationManager.getResource("platformTransactionManagers"))) {
					try {
						if (transactionStatuses.get(transactionManager) != null) {
							transactionManager.rollback(transactionStatuses.get(transactionManager));
						}
					} catch (Exception ex2) {
						LOGGER.warn("Rollback exception (" + transactionManager + ") " + ex2.getMessage(), ex2);
					}
				}

				if (mts.isNewSynchonization()) {
					TransactionSynchronizationManager.clearSynchronization();
					TransactionSynchronizationManager.unbindResource("mts");
					TransactionSynchronizationManager.unbindResource("platformTransactionManagers");
					TransactionSynchronizationManager.unbindResource("count");
				}

				throw new CannotCreateTransactionException(ex.getMessage(), ex);
			}
		}

		return mts;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.transaction.PlatformTransactionManager#commit(org.springframework.transaction.TransactionStatus)
	 */
	@SuppressWarnings("unchecked")
	public void commit(TransactionStatus status) throws TransactionException {
		if (((Integer) TransactionSynchronizationManager.getResource("count")).compareTo(0) == 0) {
			MultiTransactionStatus multiTransactionStatus = (MultiTransactionStatus) status;

			boolean commit = true;
			Exception commitException = null;
			PlatformTransactionManager commitExceptionTransactionManager = null;

			for (PlatformTransactionManager transactionManager : reverse((LinkedHashSet<PlatformTransactionManager>) TransactionSynchronizationManager.getResource("platformTransactionManagers"))) {

				if (commit) {

					try {
						multiTransactionStatus.commit(transactionManager);
					} catch (Exception ex) {
						commit = false;
						commitException = ex;
						commitExceptionTransactionManager = transactionManager;
					}

				} else {

					// after unsucessfull commit we must try to rollback remaining transaction managers

					try {
						multiTransactionStatus.rollback(transactionManager);
					} catch (Exception ex) {
						LOGGER.warn("Rollback exception (after commit) (" + transactionManager + ") " + ex.getMessage(), ex);
					}
				}
			}

			if (multiTransactionStatus.isNewSynchonization()) {
				TransactionSynchronizationManager.clearSynchronization();
				TransactionSynchronizationManager.unbindResource("mts");
				TransactionSynchronizationManager.unbindResource("platformTransactionManagers");
				TransactionSynchronizationManager.unbindResource("count");
			}

			if (commitException != null) {
				boolean firstTransactionManagerFailed = commitExceptionTransactionManager == getLastTransactionManager();
				int transactionState = firstTransactionManagerFailed ? HeuristicCompletionException.STATE_ROLLED_BACK
						: HeuristicCompletionException.STATE_MIXED;
				throw new HeuristicCompletionException(transactionState, commitException);
			}
		} else {
			Integer count = (Integer) TransactionSynchronizationManager.getResource("count");
			TransactionSynchronizationManager.unbindResource("count");
			TransactionSynchronizationManager.bindResource("count", count - 1);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.transaction.PlatformTransactionManager#rollback(org.springframework.transaction.TransactionStatus)
	 */
	@SuppressWarnings("unchecked")
	public void rollback(TransactionStatus status) throws TransactionException {
		if (((Integer) TransactionSynchronizationManager.getResource("count")).compareTo(0) == 0) {
			Exception rollbackException = null;
			PlatformTransactionManager rollbackExceptionTransactionManager = null;

			MultiTransactionStatus multiTransactionStatus = (MultiTransactionStatus) status;

			for (PlatformTransactionManager transactionManager : reverse((LinkedHashSet<PlatformTransactionManager>) TransactionSynchronizationManager.getResource("platformTransactionManagers"))) {
				try {
					multiTransactionStatus.rollback(transactionManager);
				} catch (Exception ex) {
					if (rollbackException == null) {
						rollbackException = ex;
						rollbackExceptionTransactionManager = transactionManager;
					} else {
						LOGGER.warn("Rollback exception (" + transactionManager + ") " + ex.getMessage(), ex);
					}
				}
			}

			if (multiTransactionStatus.isNewSynchonization()) {
				TransactionSynchronizationManager.clearSynchronization();
				TransactionSynchronizationManager.unbindResource("mts");
				TransactionSynchronizationManager.unbindResource("platformTransactionManagers");
				TransactionSynchronizationManager.unbindResource("count");
			}

			if (rollbackException != null) {
				throw new UnexpectedRollbackException("Rollback exception, originated at (" + rollbackExceptionTransactionManager
						+ ") " + rollbackException.getMessage(), rollbackException);
			}
		} else {
			Integer count = (Integer) TransactionSynchronizationManager.getResource("count");
			TransactionSynchronizationManager.unbindResource("count");
			TransactionSynchronizationManager.bindResource("count", count - 1);
		}
	}

	private <T> Iterable<T> reverse(Collection<T> collection) {

		List<T> list = new ArrayList<T>(collection);
		Collections.reverse(list);
		return list;
	}

	private PlatformTransactionManager getLastTransactionManager() {
		return transactionManagers.get(lastTransactionManagerIndex());
	}

	private int lastTransactionManagerIndex() {
		return transactionManagers.size() - 1;
	}

}
