package interfaces;

public interface StackIF<T> {

	/**
	 * Devuelve la cima de la pila.
	 * 
	 * @return la cima de la pila.
	 */
	public T getTop();

	/**
	 * Inserta un elemento a la pila.
	 *
	 * @param element
	 *            El elemento a ser añadido.
	 * @return la pila incluyendo el elemento.
	 */
	public StackIF<T> push(T element);

	/**
	 * Extrae de la pila el elemento en la cima.
	 *
	 * @return la pila excluyendo la cabeza.
	 */
	public StackIF<T> pop();

	/**
	 * Devuelve cierto si la pila esta vacia.
	 *
	 * @return cierto si la pila esta vacia.
	 */

	public boolean isEmpty();

	/**
	 * Devuelve cierto si la pila esta llena.
	 *
	 * @return cierto si la pila esta llena.
	 */
	public boolean isFull();

	/**
	 * Devuelve el numero de elementos de la pila.
	 *
	 * @return el numero de elementos de la pila.
	 */
	public int getLength();

	/**
	 * Devuelve cierto si la pila contiene el elemento.
	 *
	 * @param element
	 *            El elemento buscado.
	 * @return cierto si la pila contiene el elemento.
	 */
	public boolean contains(T element);

	/**
	 * Devuelve un iterador para la pila.
	 *
	 * @return un iterador para la pila.
	 */
	public IteratorIF<T> getIterator();
	
	
	// public int hashCode();

}
