package interfaces;

public interface IteratorIF<T> {
	/**
	 * Devuelve el siguiente elemento de la iteracion.
	 *
	 * @return el siguiente elemento de la iteracion.
	 */
	public T getNext();

	/**
	 * Indica si hay m�s elemento en la iteraci�n.
	 *
	 * @return true si hay m�s elemento en la iteraci�n
	 */
	public boolean hasNext();

	/**
	 * Restablece el iterador para volver al inicio.
	 */
	public void reset();
}
