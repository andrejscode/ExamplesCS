import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.Comparator;

interface BinTree<A extends Comparable<A>> {
	BinTree<A> include(A key);
	BinTree<A> remove(A key);
	boolean    contains(A key);
	Node<A>    min();
	Node<A>    max();
	boolean    isEmpty();
}

class Empty<A extends Comparable<A>> implements BinTree<A> {
	public Node<A>  include(A key) { 
		return new Node<A>(key, new Empty<A>(), new Empty<A>()); 
	}
	public Empty<A> remove(A key) { 
		return this;  
	} 
	public boolean  contains(A key) { 
		return false; 
	}
	public Node<A>  min() { 
		return null; 	
	}
	public Node<A>  max() { 
		return null;  
	}
	public boolean  isEmpty() { 
		return true;  
	}
}

class Node<A extends Comparable<A>>  implements BinTree<A> {
	A key;
	BinTree<A> left, right;

	Node (A key, BinTree<A> left, BinTree<A> right) { 
		this.key = key; 
		this.left = left; 
		this.right = right;	   
	}	

	public Node<A> include(A key)   {
		if (this.key.compareTo(key) == 0) 	  
			return this;

		if (this.key.compareTo(key) >  0) 	  
			return new Node<A>(this.key, left, right.include(key));
				  
		return new Node<A>(this.key, left.include(key), right);
	}

	public BinTree<A> remove(A key) {
		if (this.key.compareTo(key) == 0) 	  
			return removeThis(key);

		if (this.key.compareTo(key) >  0)
			return new Node<A>(this.key, left, right.remove(key));

		return new Node<A>(this.key, left.remove(key), right);
	} 

	private BinTree<A> removeThis(A key) {
		if ( right.isEmpty() &&  left.isEmpty())  
			return new Empty<A>();

		if ( right.isEmpty() && !left.isEmpty())  
			return left;

		if (!right.isEmpty() &&  left.isEmpty())  
			return right;

		return removeWith2(key);
	}
	
	private Node<A> removeWith2(A key) {
		if (right.min() != right) 
			return new Node<A>(right.min().key, left, right.remove(right.min().key));

		return new Node<A>(left.max().key, left.remove(left.max().key), right);
	}

	public boolean contains(A key)  {
		if (this.key.compareTo(key) == 0) 	  
			return true;

		if (this.key.compareTo(key) >  0)  	  
			return right.contains(key);

		return left.contains(key);
	}

	public Node<A> min() {
		if (left.isEmpty())			  
			return this;

		return left.min();
	}

	public Node<A> max() {
		if (right.isEmpty())			  
			return this;

		return right.max();
	} 

	public boolean isEmpty() { 
		return false; 
	}
}

public class HappyTree {
	public static void main (String[] args) throws java.lang.Exception {
		BinTree<Integer> ourTree = new Empty<Integer>();
		ourTree = ourTree.include(new Integer(2));
		System.out.println(ourTree.contains(new Integer(2)));
		ourTree = ourTree.remove(new Integer(2));
		System.out.println(ourTree.contains(new Integer(2)));
	} 
}
