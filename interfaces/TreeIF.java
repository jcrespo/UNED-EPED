package interfaces;

public interface TreeIF<T> {
	public int PREORDER = 0;
	public int POSTORDER = 1;
	public int LRBREADTH = 2;
	public int RLBREADTH = 3;

	public T getRoot();

	public void setRoot(T element);

	public ListIF<TreeIF<T>> getChildren();

	public void addChild(TreeIF<T> child);

	public void removeChild(int index);

	public boolean isLeaf();

	public boolean isEmpty();

	public boolean contains(T element);

	public IteratorIF<T> getIterator(int travesalType);

}