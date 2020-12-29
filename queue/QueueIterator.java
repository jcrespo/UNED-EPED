package queue;

import interfaces.*;

public class QueueIterator<T> implements IteratorIF<T> {

	private QueueIF<T> handler;
	private QueueIF<T> restart;
	
	/**
	 * Constructor para QueueIterator.
	 * @param handler el manejador de colas.
	 */
	public QueueIterator (QueueIF<T> handler) {
		this.handler = handler;
		this.restart = new QueueDynamic<T> (handler);
	}

	/**
	 * Devuelve el siguiente elemento de la iteración.
	 * @return el siguiente elemento de la iteración.
	 */
	@Override
	public T getNext() {
		T element = handler.getFirst();
		handler.remove();
		return element;
	}

	@Override
	public boolean hasNext() {
		return !handler.isEmpty();
	}

	@Override
	public void reset() {
		handler = new QueueDynamic<T> (restart);
	}

}
