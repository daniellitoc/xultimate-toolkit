package org.danielli.xultimate.shard;

import java.util.List;

/**
 * 执行操作。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public interface Action {

	/**
	 * 执行方法。
	 * 
	 * @param elements 被执行元素列表。
	 */
	<E> void execute(List<E> elements);
}
