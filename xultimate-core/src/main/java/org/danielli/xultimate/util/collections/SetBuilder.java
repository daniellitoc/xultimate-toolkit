package org.danielli.xultimate.util.collections;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.builder.Builder;

/**
 * 创建Set的Builder设计模式。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 *
 * @param <E> 元素类型。
 */
public class SetBuilder<E> implements Builder<Set<E>> {

	private Set<E> set;
	
	public SetBuilder(Set<E> set) {
		this.set = set;
	}
	
	public SetBuilder<E> add(E e) {
		set.add(e);
		return this;
	}
	
	public SetBuilder<E> remove(E e) {
		set.remove(e);
		return this;
	}
	
	public SetBuilder<E> removeAll(Collection<?> c) {
		set.removeAll(c);
		return this;
	}
	
	public SetBuilder<E> addAll(Collection<? extends E> c) {
		set.addAll(c);
		return this;
	}
	
	public SetBuilder<E> retainAll(Collection<?> c) {
		set.retainAll(c);
		return this;
	}
	
	public SetBuilder<E> clear() {
		set.clear();
		return this;
	}
	
	@Override
	public Set<E> build() {
		return set;
	}
	
}
