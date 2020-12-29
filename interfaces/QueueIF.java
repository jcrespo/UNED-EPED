package interfaces;

public interface QueueIF<T> {

	public T getFirst();

	public QueueIF<T> add(T element);

	public QueueIF<T> remove();

	public boolean isEmpty();

	public boolean isFull();

	public int getLength();

	public boolean contains(T element);

	public IteratorIF<T> getIterator();
	
}