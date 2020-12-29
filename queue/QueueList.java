package queue;

import list.ListDynamic;
import interfaces.*;

public class QueueList<T> implements QueueIF<T>{
	
	private ListIF<T> first;
	private ListIF<T> last;
	
	public QueueList() {
		first = new ListDynamic<T>();
		last  = first;
	}
	
	public QueueList (QueueIF<T> queue) {
		this();
		if (queue != null)
			for (int i = 0; i < queue.getLength(); i++) {
				T element = queue.getFirst();
				add(element);
				queue.remove();
				queue.add(element);
			}
	}
	
	public QueueList (ListIF<T> list) {
		first = new ListDynamic<T> (list);
		ListIF<T> l = list;
		while (!l.isEmpty()) l = l.getTail();
		last = l;
	}

	@Override
	public T getFirst() {
		return first.getFirst();
	}

	@Override
	public QueueIF<T> add(T element) {
		last.insert(element);
		last = last.getTail();
		return this;
	}

	@Override
	public QueueIF<T> remove() {
		first = first.getTail();
		return this;
	}

	@Override
	public boolean isEmpty() {
		return first.isEmpty();
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public int getLength() {
		return first.getLength();
	}

	@Override
	public boolean contains(T element) {
		return first.contains(element);
	}

	@Override
	public IteratorIF<T> getIterator() {
		return first.getIterator();
	}
	
	//@Override
	public int hashcode() {
		return 31 * ((first == null) ? 0 : first.hashCode())
				+ ((last == null) ? 0 : last.hashCode());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null)
			return false;
		if (o.getClass() != this.getClass()) return false;
		else {
			QueueList<T> q = (QueueList<T>) o;
			return q.first.equals(first) &&
				   q.last.equals(last);
		}
	}

	@Override
	public String toString() {
	StringBuffer buff = new StringBuffer();
	buff.append ("QueueList ‐ [");
	IteratorIF<T> queueIt = getIterator ();
	while (queueIt.hasNext ()) {
		T element = queueIt.getNext ();
		buff.append (element);
		if (queueIt.hasNext ())
			buff.append (", ");
	}
	
	buff.append ("]");
	return buff.toString ();
	}
	

}
