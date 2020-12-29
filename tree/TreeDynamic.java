package tree;

import interfaces.*;
import queue.QueueDynamic;
import list.ListDynamic;
import stack.StackDynamic;

public class TreeDynamic<T> implements TreeIF<T> {
	private T root;
	private QueueIF<TreeIF<T>> children;

	public TreeDynamic() {
		this.root = null;
		this.children = new QueueDynamic<TreeIF<T>>();
	}

	public TreeDynamic(T element) {
		this();
		this.root = element;
	}

	public TreeDynamic(TreeIF<T> tree) {
		this.root = tree.getRoot();
		this.children = new QueueDynamic<TreeIF<T>>();
		ListIF<TreeIF<T>> tChildren = tree.getChildren();
		while (!tChildren.isEmpty()) {
			TreeIF<T> aChild = tChildren.getFirst();
			TreeIF<T> cChild = new TreeDynamic<T>(aChild);
			children.add(cChild);
			tChildren = tChildren.getTail();
		}
	}

	/**
	 * Devuelve el elemento raiz del arbol.
	 * 
	 * @return el elemento raiz del arbol.
	 */
	public T getRoot() {
		return this.root;
	}

	/**
	 * Establece el elemento raiz.
	 * 
	 * @param element
	 *            El elemento a establecer.
	 */
	public void setRoot(T element) {
		if (element != null)
			this.root = element;
	}

	/**
	 * Devuelve una lista con los hijos de un arbol.
	 * 
	 * @return los hijos de un arbol.
	 */
	// Manuel

	public ListIF<TreeIF<T>> getChildren() {
		ListIF<TreeIF<T>> lChildren = new ListDynamic<TreeIF<T>>();
		StackIF<TreeIF<T>> sChildren = new StackDynamic<TreeIF<T>>();
		IteratorIF<TreeIF<T>> childrenIt = children.getIterator();
		while (childrenIt.hasNext()) {
			TreeIF<T> aChild = childrenIt.getNext();
			sChildren.push(aChild);
		}
		while (!sChildren.isEmpty()) {
			TreeIF<T> aChild = sChildren.getTop();
			lChildren.insert(aChild);
			sChildren.pop();
		}
		return lChildren;
	}


	/**
	 * Inserta un subarbol como ultimo hijo.
	 * 
	 * @param child
	 *            el hijo a insertar.
	 */
	public void addChild(TreeIF<T> child) {
		if (this.root != null) children.add(child);
	}

	/**
	 * Extrae un subarbol como hijo.
	 * 
	 * @param index
	 *            el indice del subarbol con base en 0.
	 */
	public void removeChild(int index) {
		QueueIF<TreeIF<T>> aux = new QueueDynamic<TreeIF<T>>();
		int length = children.getLength();

		for (int i = 0; i < length; i++) {
			TreeIF<T> aChild = children.getFirst();
			if (i != index) {
				aux.add(aChild);
			}
			children.remove();
		}
		children = aux;
	}

	/**
	 * Devuelve cierto si el arbol es un nodo hoja.
	 * 
	 * @return cierto si el arbol es un nodo hoja.
	 */
	public boolean isLeaf() {
		return children.isEmpty();
	}

	/**
	 * Devuelve cierto si el arbol es vacio.
	 * 
	 * @return cierto si el arbol es vacio.
	 */
	public boolean isEmpty() {
		return this.root == null && this.children.isEmpty();
	}

	/**
	 * Devuelve cierto si la lista contiene el elemento.
	 * 
	 * @param element
	 *            El elemento buscado.
	 * @return cierto si la lista contiene el elemento.
	 */
	public boolean contains(T element) {
		if (this.root.equals(element))
			return true;
		else {
			IteratorIF<TreeIF<T>> childrenIt = children.getIterator();
			boolean found = false;
			while (!found && childrenIt.hasNext()) {
				TreeIF<T> aChild = childrenIt.getNext();
				found = aChild.contains(element);
			}
			return found;
		}

	}

	/**
	 * Devuelve un iterador para la lista.
	 * 
	 * @param traversalType
	 *            El tipo de recorrido.
	 * @return un iterador para la lista.
	 */
	public IteratorIF<T> getIterator(int type) {
		TreeIF<T> handler = new TreeDynamic<T>(this);
		return new TreeIterator<T>(handler, type);
	}

	/**
	 * Implementación de prueba. Forzado el recorrido a PREORDER
	 */
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("TreeDynamic - [");
		IteratorIF<T> treeIt = getIterator(TreeIF.PREORDER);
		while (treeIt.hasNext()) {
			T element = treeIt.getNext();
			buff.append(element);
			if (treeIt.hasNext())
				buff.append(", ");
		}
		buff.append("]");
		return buff.toString();
	}
}
