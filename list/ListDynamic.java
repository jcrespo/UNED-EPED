package list;

import interfaces.*;

public class ListDynamic<T> implements ListIF<T> {

	private T first;
	private ListIF<T> tail;
	
	public ListDynamic () {
		first = null;
		tail = null;
	}
	
	public ListDynamic(ListIF<T> list) {
		this();
		if (list != null)
			if (!list.isEmpty()) {
				first = list.getFirst();
				tail = new ListDynamic<T>(list.getTail());
			}
	}
	
	@Override
	public T getFirst() {
		return first;
	}

	@Override
	public ListIF<T> getTail() {
		if (isEmpty())
			return this;
		return tail;
	}

	@Override
	public ListIF<T> insert(T element) {
		if (element != null) {
			ListDynamic<T> next = new ListDynamic<T>();
			next.first = first;
			next.tail = tail;
			first = element;
			tail = next;
		}
		return this;
	}

	@Override
	public boolean isEmpty() {
		return first == null && tail == null;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public int getLength() {
		if (isEmpty())
			return 0;
		else
			return 1 + tail.getLength();
	}

	@Override
	public IteratorIF<T> getIterator() {
		ListIF<T> handler = new ListDynamic<T>(this);
		return new ListIterator<T>(handler);
	}

	/**
     * Devuelve cierto si la lista contiene el elemento.
     * @param element El elemento buscado.
     * @return true si la lista contiene el elemento.
     */	
	@Override
	public boolean contains(T element){
		if(isEmpty())return false;
		return first.equals(element) || tail.contains(element);
	}
	

	/**
	 * Inserta un elemento al final de una lista
	 * @param element El elemento a insertar.
	 * @return La lista con el elemento insertado.
	 */
	public ListIF<T> append (T element) {
		if (isEmpty()) return insert(element);
		tail = ((ListDynamic<T>) tail).append(element);
		return this;
	}


	/*
	@Override
	public ListIF<T> sort(ComparatorIF<T> comparator) {
		if (isEmpty())
			return this;
		else
			return ((ListDynamic<T>) tail.sort(comparator)).sortInsert(first, comparator);
	}
	*/

	/**
	 * Inserta un elemento en orden en una lista ordenada.
	 *
	 * @param element El elemento a insertar.
	 * @param comparator El comparador de elementos.
	 * @return la lista ordenada.
	 */
	private ListIF<T> sortInsert(T element, ComparatorIF<T> comparator) {
		if (isEmpty())
			return this.insert(element);
		else if (comparator.isLess(element, first))
			return this.insert(element);
		else
			return ((ListDynamic<T>) tail).sortInsert(element, comparator)
					.insert(first);
	}
	
	
	
	/**
	 * Ordena los elementos de la lista.
	 *
	 * @param element El comparador de elementos.
	 * @return la lista ordenada.
	 */
	
	@Override
	public ListIF<T> sort(ComparatorIF<T> comparator) {
		if (getLength() <= 1)
			return this;
		else {
			int middle = (int) (getLength() / 2);
			int index = 0;
			ListIF<T> lLeft = new ListDynamic<T>();
			ListIF<T> lRight = new ListDynamic<T>();
			IteratorIF<T> listIt = getIterator();
			while (listIt.hasNext()) {
				T element = listIt.getNext();
				if (index < middle)
					lLeft.insert(element);
				if (index >= middle)
					lRight.insert(element);
				index = index + 1;
			}
			lLeft = lLeft.sort(comparator);
			lRight = lRight.sort(comparator);
			return sortMerge(lLeft, lRight, comparator);
		}
	}
	
	
	/**
	 * Ordenaci�n por mezcla.
	 * @param lLeft Parte izquierda.
	 * @param lRight Parte derecha.
	 * @param comparator El comparador de elementos.
	 * @return la lista ordenada.
	 */
	
	private ListIF<T> sortMerge(ListIF<T> lLeft, ListIF<T> lRight, ComparatorIF<T> comparator) {
		ListDynamic<T> result = new ListDynamic<T>();
		while (lLeft.getLength() > 0 || lRight.getLength() > 0) {
			if (lLeft.getLength() > 0 && lRight.getLength() > 0) {
				T eLeft = lLeft.getFirst();
				T eRight = lRight.getFirst();
				if (comparator.isGreater(eLeft, eRight)) {
					result.append(eLeft);
					lLeft = lLeft.getTail();
				} else {
					result.append(eRight);
					lRight = lRight.getTail();
				}
			} else if (lLeft.getLength() > 0) {
				T eLeft = lLeft.getFirst();
				result.append(eLeft);
				lLeft = lLeft.getTail();
			} else if (lRight.getLength() > 0) {
				T eRight = lRight.getFirst();
				result.append(eRight);
				lRight = lRight.getTail();
			}
		}
		return result;
	}
	
	
	//@Override
	public int hashcode() {
		return 31 * ((first == null) ? 0 : first.hashCode())
				+ ((tail == null) ? 0 : tail.hashCode());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null)
			return false;
		if (!(o instanceof ListDynamic))
			return false;
		else {
			ListDynamic<T> l = (ListDynamic<T>) o;
			return l.first.equals(first) && l.tail.equals(tail);
		}
	}

	@Override
	public String toString() {
	StringBuffer buff = new StringBuffer();
	buff.append ("ListDynamic [");
	IteratorIF<T> listIt = getIterator ();
	while (listIt.hasNext ()) {
		T element = listIt.getNext ();
		buff.append (element);
		if (listIt.hasNext ())
			buff.append (", ");
	}
	
	buff.append ("]");
	return buff.toString ();
	}
	

}
