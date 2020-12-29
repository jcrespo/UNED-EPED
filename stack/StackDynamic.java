package stack;

import interfaces.*;

public class StackDynamic<T> implements StackIF<T> {
	
	private T element;
	private StackDynamic<T> next;
	
	public StackDynamic(){
		element	=null;
		next	=null;
	}
	
	public StackDynamic(StackIF<T> stack){
		this();
		if(stack != null){
			if(!stack.isEmpty()){
				element	= stack.getTop();
				next	= new StackDynamic<T>(stack.pop());
				stack.push(element);
			}
		}
	
	}
	
	public StackDynamic(ListIF<T> list){
		this();
		if(list != null) {
			if(!list.isEmpty()){
				element	= list.getFirst();
				next	= new StackDynamic<T>(list.getTail());
			}
		}
	}
	
	@Override
	public T getTop(){
		return element;
	}
	
	@Override
	public StackIF<T> push(T element){
		if(element!=null){
			StackDynamic<T> stack = new StackDynamic<T>();
			stack.element	= this.element;
			stack.next		= this.next;
			this.element	= element;
			this.next		= stack;
		}
		return this;
	}
	
	@Override
	public StackIF<T> pop(){
		if(!isEmpty()){
			element = next.element;
			next	= next.next;
		}
		return this;
	}
	
	@Override
	public boolean isEmpty(){
		return element == null && next == null;
	}
	
	@Override
	public boolean isFull(){
		return false;
	}
	
	@Override
	public int getLength(){
		if(isEmpty()) return 0;
		else return 1 + next.getLength();
	}
	
	@Override
	public boolean contains(T element){
		if(isEmpty()) return false;
		else return this.element.equals(element) || next.contains(element);		
	}
	
	@Override
	public IteratorIF<T>  getIterator(){
		StackIF<T> handler= new StackDynamic<T>(this);
		return new StackIterator<T> (handler);
	}
	 
	@Override
	public int hashCode(){
		return 31*((element==null) ? 0: element.hashCode()) + ((next==null) ? 0: next.hashCode());
	}

	
	/*
	@SuppressWarnings("Unchecked")
	@Override
	public boolean equals(Object o){
		
	}
	*/
	
	@Override
	public String toString() {
	StringBuffer buff = new StringBuffer();
	buff.append ("StackDynamic [");
	IteratorIF<T> stackIt = getIterator ();
	while (stackIt.hasNext ()) {
		T element = stackIt.getNext ();
		buff.append (element);
		if (stackIt.hasNext ())
			buff.append (", ");
	}
	
	buff.append ("]");
	return buff.toString ();
	}
	
}
	
