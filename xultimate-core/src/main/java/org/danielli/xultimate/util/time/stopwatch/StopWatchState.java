package org.danielli.xultimate.util.time.stopwatch;

import org.danielli.xultimate.util.time.stopwatch.StopWatch.TaskInfo;

/**
 * 秒表运行时状态。
 *
 * @author Daniel Li
 * @since 17 Jun 2013
 */
public interface StopWatchState {
	
	/**
	 * 开始秒表计时。
	 * 
	 * @param stopWatch 秒表。
	 */
	void start(StopWatch stopWatch);
	
	/**
	 * 结束秒表计时。
	 * 
	 * @param stopWatch 秒表。
	 */
	void stop(StopWatch stopWatch);
	
	/**
	 * 重置秒表计时。
	 * 
	 * @param stopWatch 秒表。
	 */
	void reset(StopWatch stopWatch);
	
	/**
	 * 暂停秒表计时。
	 * 
	 * @param stopWatch 秒表。
	 */
	void suspend(StopWatch stopWatch);
	
	/**
	 * 恢复秒表计时。
	 * 
	 * @param stopWatch 秒表。
	 */
	void resume(StopWatch stopWatch);
	
	/**
	 * 标记任务。
	 * 
	 * @param stopWatch 秒表。
	 * @param taskName 任务名称。
	 */
	void mark(StopWatch stopWatch, String taskName);
}

/**
 * 秒表运行时状态抽象类。
 *
 * @author Daniel Li
 * @since 17 Jun 2013
 */
abstract class AbstractStopWatchState implements StopWatchState {
	
	@Override
	public void start(StopWatch stopWatch) {
		throw new IllegalStateException("Stopwatch already started. ");
	}
	
	@Override
	public void stop(StopWatch stopWatch) {
		throw new IllegalStateException("Stopwatch is not running. ");
	}
	
	@Override
	public void reset(StopWatch stopWatch) {
		stopWatch.state = StopWatchUnStartedState.INSTANCE;
		stopWatch.taskList.clear();
		stopWatch.startTime = 0L;
		stopWatch.stopTime = 0L;
		stopWatch.currentTaskStartTime = 0L;
	}
	
	@Override
	public void suspend(StopWatch stopWatch) {
		throw new IllegalStateException("Stopwatch must be running to suspend. ");
	}
	
	@Override
	public void resume(StopWatch stopWatch) {
		throw new IllegalStateException("Stopwatch must be suspended to resume. ");
	}
	
	@Override
	public void mark(StopWatch stopWatch, String taskName) {
		throw new IllegalStateException("Stopwatch is not running. ");
	}
	
}

/**
 * 秒表未起动状态。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
class StopWatchUnStartedState extends AbstractStopWatchState {

	static final StopWatchState INSTANCE = new StopWatchUnStartedState();
	
	private StopWatchUnStartedState() { }
	
	@Override
	public void start(StopWatch stopWatch) {
		stopWatch.startTime = System.nanoTime();
		stopWatch.currentTaskStartTime = stopWatch.startTime;
		stopWatch.state = StopWatchRunningState.INSTANCE;
	}
}

/**
 * 秒表运行状态。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
class StopWatchRunningState extends AbstractStopWatchState {

	static final StopWatchState INSTANCE = new StopWatchRunningState();
	
	private StopWatchRunningState() { }

	@Override
	public void stop(StopWatch stopWatch) {
		stopWatch.stopTime = System.nanoTime();
		stopWatch.state = StopWatchStoppedState.INSTANCE;
	}

	@Override
	public void suspend(StopWatch stopWatch) {
		stopWatch.stopTime = System.nanoTime();
		stopWatch.state = StopWatchSuspendedState.INSTANCE;
	}

	@Override
	public void mark(StopWatch stopWatch, String taskName) {
		long currentTime = System.nanoTime();
		stopWatch.taskList.add(new TaskInfo(taskName, stopWatch.currentTaskStartTime, currentTime));
		stopWatch.currentTaskStartTime = currentTime;
	}
	
}

/**
 * 秒表停止状态。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
class StopWatchStoppedState extends AbstractStopWatchState {

	public static final StopWatchState INSTANCE = new StopWatchStoppedState();
	
	private StopWatchStoppedState() { }
	
	@Override
	public void start(StopWatch stopWatch) {
		throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
	}
}

/**
 * 秒表暂停状态。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
class StopWatchSuspendedState extends AbstractStopWatchState {

	public static final StopWatchState INSTANCE = new StopWatchSuspendedState();
	
	private StopWatchSuspendedState() { }

	@Override
	public void stop(StopWatch stopWatch) {
		throw new IllegalStateException("Stopwatch must be resume before being stopped. ");
	}

	@Override
	public void resume(StopWatch stopWatch) {
		long interval = System.nanoTime() - stopWatch.stopTime;
		stopWatch.startTime += interval;
		stopWatch.currentTaskStartTime += interval;
		stopWatch.state = StopWatchRunningState.INSTANCE;
	}	
}