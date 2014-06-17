package org.danielli.xultimate.shard;

/**
 * 操作器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 *
 * @param <E> 操作元素类型。
 */
public interface Operator<E> {

	/**
	 * 操作方法。
	 * 
	 * @param element1 第一个元素。
	 * @param element2 第二个元素。
	 * @return 结果元素。
	 */
	E operate(E element1, E element2);
	
}
