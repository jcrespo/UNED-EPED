package stack;

import interfaces.*;
import list.*;

public class StackList<T> implements StackIF<T> {

	private ListIF<T> list;

	public StackList() {
		this.list = new ListDynamic<T>();
	}

	public StackList(StackIF<T> stack) {
		// TODO: ¿Ejercicio 7, pila inversa?
	}

	public StackList(ListIF<T> list) {

	}

	@Override
	public T getTop() {
		return list.getFirst();
	}

	@Override
	public StackIF<T> push(T element) {
		list.insert(element);
		return this;
	}

	@Override
	public StackIF<T> pop() {
		list = list.getTail();
		return this;
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return list.isFull();
	}

	@Override
	public int getLength() {
		return list.getLength();
	}

	@Override
	public boolean contains(T element) {
		return list.contains(element);
	}

	@Override
	public IteratorIF<T> getIterator() {
		StackIF<T> handler = new StackList<T>(list);
		return new StackIterator<T>(handler);
	}

	@Override
	public int hashCode() {
		return 31
				* ((list.getFirst() == null) ? 0 : list.getFirst().hashCode())
				+ ((list.getTail() == null) ? 0 : list.getTail().hashCode());

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null)
			return false;
		if (!(o instanceof StackList))
			return false;
		else {
			StackList<T> s = (StackList<T>) o;
			return s.list.getFirst().equals(list.getFirst())
					&& s.list.getTail().equals(list.getTail());
		}
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("StackList - [");
		IteratorIF<T> stackIt = getIterator();
		while (stackIt.hasNext()) {
			T element = stackIt.getNext();
			buff.append(element);
			if (stackIt.hasNext())
				buff.append(", ");
		}
		buff.append("]");
		return buff.toString();
	}

}
