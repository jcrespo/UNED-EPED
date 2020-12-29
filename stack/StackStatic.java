package stack;

import interfaces.*;

public class StackStatic<T> implements StackIF<T> {

	private Object[] elements;
	private int capacity;
	private int top;

	public StackStatic(int capacity) {
		this.elements = new Object[capacity];
		this.capacity = capacity;
		this.top = -1;
	}

	public StackStatic(StackIF<T> stack) {
		if (stack != null) {
			this.capacity = stack.getLength();
			this.elements = new Object[this.capacity];
			this.top = -1;
			for (int i = capacity - 1; i >= 0; i--) {
				T element = stack.getTop();
				elements[i] = element;
				stack.pop();
				top++;
			}
			for (int i = 0; i <= capacity - 1; i++) {
				T element = (T) elements[i];
				stack.push(element);
			}
		}
	}

	public StackStatic(ListIF<T> list) {
		if (list != null) {
			this.capacity = list.getLength();
			this.elements = new Object[this.capacity];
			this.top = -1;
			ListIF<T> aList = list;
			for (int index = 0; index < list.getLength(); index++) {
				this.elements[index] = aList.getFirst();
				aList = list.getTail();
			}
		}
	}

	public T getTop() {
		if (isEmpty())
			return null;
		return (T) elements[top];
	}

	public StackIF<T> push(T element) {
		if (element != null)
			if (!isFull()) {
				top = top + 1;
				elements[top] = element;
			}
		return this;
	}

	@Override
	public StackIF<T> pop() {
		if (!isEmpty()) {
			top = top - 1;
		}
		return this;
	}

	@Override
	public boolean isEmpty() {
		return top == -1;
	}

	@Override
	public boolean isFull() {
		return top == (capacity - 1);
	}

	@Override
	public int getLength() {
		return top + 1;
	}

	@Override
	public boolean contains(T element) {
		IteratorIF<T> stackIt = this.getIterator();
		boolean found = false;
		while (!found && stackIt.hasNext()) {
			T anElement = stackIt.getNext();
			found = anElement.equals(element);
		}
		return found;
	}

	@Override
	public IteratorIF<T> getIterator() {
		StackIF<T> handler = new StackStatic<T>(this);
		return new StackIterator<T>(handler);
	}
	
	@Override
    public int hashCode()
    {
        return 31 * ( ( elements == null ) ? 0 : elements.hashCode() );

    }
    
    @SuppressWarnings( "unchecked" )
    @Override
    public boolean equals( Object o )
    {
        if( o == this )
            return true;
        if( o == null )
            return false;
        if( !( o instanceof StackStatic ) )
            return false;
        else
        {
            StackStatic<T> s = ( StackStatic<T> ) o;
            return s.elements.equals( elements );
        }
    }
    
    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append( "StackStatic - [" );
        IteratorIF<T> stackIt = getIterator();
        while( stackIt.hasNext() )
        {
            T element = stackIt.getNext();
            buff.append( element );
            if( stackIt.hasNext() )
                buff.append( ", " );
        }
        buff.append( "]" );
        return buff.toString();
    }
}