package org.danielli.xultimate.util.collections;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.Builder;

/**
 * 创建List的Builder设计模式。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 *
 * @param <E> 元素类型。
 */
public class ListBuilder<E> implements Builder<List<E>> {

	private List<E> list;
	
	public ListBuilder(List<E> list) {
		this.list = list;
	}
	
	public ListBuilder<E> add(E e) {
		list.add(e);
		return this;
	}
	
	public ListBuilder<E> remove(E e) {
		list.remove(e);
		return this;
	}
	
	public ListBuilder<E> removeAll(Collection<?> c) {
		list.removeAll(c);
		return this;
	}
	
	public ListBuilder<E> addAll(Collection<? extends E> c) {
		list.addAll(c);
		return this;
	}
	
	public ListBuilder<E> addAll(int index, Collection<? extends E> c) {
		list.addAll(index, c);
		return this;
	}
	
	public ListBuilder<E> retainAll(Collection<?> c) {
		list.retainAll(c);
		return this;
	}
	
	public ListBuilder<E> clear() {
		list.clear();
		return this;
	}
	
	public ListBuilder<E> set(int index, E element) {
		list.set(index, element);
		return this;
	}
	
	public ListBuilder<E> add(int index, E element) {
		list.add(index, element);
		return this;
	}
	
	public ListBuilder<E> remove(int index) {
		list.remove(index);
		return this;
	}
	
	@Override
	public List<E> build() {
		return list;
	}
	
}
