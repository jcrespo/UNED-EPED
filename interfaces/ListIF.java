package interfaces;

public interface ListIF<T> {
	/**
	 * Devuelve la cabeza de una lista.
	 *
	 * @return la cabeza de una lista.
	 */
	public T getFirst();

	/**
	 * Devuelve la lista excluyendo la cabeza.
	 *
	 * @return la lista excluyendo la cabeza.
	 */
	public ListIF<T> getTail();

	/**
	 * Inserta un nuevo elemento a la lista.
	 *
	 * @param element El elemento a añadir.
	 * @return la lista incluyendo el elemento.
	 */
	public ListIF<T> insert(T element);

	/**
	 * Devuelve cierto si la lista esta vacia.
	 *
	 * @return cierto si la lista esta vacia.
	 */
	public boolean isEmpty();

	/**
	 * Devuelve cierto si la lista esta llena.
	 *
	 * @return cierto si la lista esta llena.
	 */
	public boolean isFull();

	/**
	 * Devuelve el numero de elementos de la lista.
	 *
	 * @return el numero de elementos de la lista.
	 */
	public int getLength();

	/**
	 * Devuelve un iterador para la lista.
	 *
	 * @return un iterador para la lista.
	 */
	public IteratorIF<T> getIterator();

	/**
	 * Devuelve cierto si la lista contiene el elemento.
	 *
	 * @param element El elemento buscado.
	 * @return cierto si la lista contiene el elemento.
	 */
	public boolean contains(T element);

	/**
	 * Ordena los elementos de la lista.
	 *
	 * @param element El comparador de elementos.
	 * @return la lista ordenada.
	 */
	public ListIF<T> sort(ComparatorIF<T> comparator);

}
