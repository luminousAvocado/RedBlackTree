package application;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class RBTree<K extends Comparable<K>, V> {

	
	protected final Node<K, V> NIL = new Node<K, V>(null, null, 'B');
	protected Node<K, V> root = NIL;
	protected int size = 0;
	
	
	
	// Create a default empty RB Tree
	public RBTree(){
	}
	
	
	
	// Clears the list, resets root = NIL
	public void clearTree(){
		root = NIL;
	}
	
	
	
	// Returns root node of the RB Tree
	public Node<K, V> getRoot(){
		return root;
	}
	
	
	
	/*
	 *	Finds and returns grandparent node
	 *	Checks to see that there is a parent node OR
	 *		checks to see there is a grandparent (
	 *		returns grandparent
	 *	Else returns null for no grandparent
	*/
	private Node<K, V> findGP(Node<K, V> current){
		
		Node<K, V> grandparent;
		
		if(current.parent != null || current.parent.parent != null){	
			grandparent = current.parent.parent;			
		}
		else{
			return null;
		}
		
		return grandparent;
	}
	
	
	
	/*
	 *	Finds and returns uncle node
	 *	Checks to sees there there is grandparent
	 *		Find uncle depending on parent's position relative to GP
	 *	Else returns null for no uncle
	*/
	private Node<K, V> findUncle(Node<K, V> current){
		
		Node<K, V> uncle;
		
		if(findGP(current) != null){
			
			Node<K, V> grandparent = findGP(current);
			
			if(current.parent == grandparent.left){
				uncle = grandparent.right;
			}
			else{
				uncle = grandparent.left;
			}
		}
		
		else{
			return null;
		}
		
		return uncle;
	}
	
	
	
	/*
	 *	Left rotation based on pivot node
	 *	this.root denotes root of tree
	*/
	private void leftRotate(Node<K, V> pivot){
		
		Node<K, V> root = pivot.parent;
		Node<K, V> tempGP = findGP(pivot);
		
		// This block rotates
		root.right = pivot.left;
		pivot.left = root;
		
		
		// This block re-sets grandparent's left or right pointer
		// the nested if-else is because pivot.parent could be either L/R child
		if (tempGP != null) {
			if(pivot.parent == findGP(pivot).left){
				tempGP.left = pivot;
			}
			else{
				tempGP.right = pivot;
			}
		} else {
			this.root = pivot;
		}
		
		
		// This block sets parent pointers
		root.parent = pivot;
		pivot.parent = tempGP;
	}
	
	
	
	/*
	 *	Right rotation based on pivot node
	 *	this.root denotes root of tree 
	*/
	private void rightRotate(Node<K, V> pivot){
		
		Node<K, V> root = pivot.parent;
		Node<K, V> tempGP = findGP(pivot);
		
		// This block rotates
		root.left = pivot.right;
		pivot.right = root;
		
		
		// This block re-sets grandparent's left or right pointer
		// the nested if-else is because pivot.parent could be either L/R child
		if (tempGP != null) {
			if(pivot.parent == findGP(pivot).left){
				tempGP.left = pivot;
			}
			else{
				tempGP.right = pivot;
			}
		} else {
			this.root = pivot;
		}
		
		
		// This block sets parent pointers
		root.parent = pivot;
		pivot.parent = tempGP;
	}
	
	
	
	/*	
	 *	Finds an element in the list
	 * 	Returns true if the element is in the tree
	 * 	Returns false if element not in tree
	*/
	public boolean search(K k){
		
		Node<K, V> current = root;
		
		while(current != NIL){
			
			// If k is less than current.Key, go to left (BST property)
			if(k.compareTo(current.getKey()) < 0){
				current = current.left;
			}
			
			// If k is greater than current.Key, go to right (BST property)
			else if(k.compareTo(current.getKey()) > 0){
				current = current.right;
			}
			
			// If k is == to current.Key, element found
			else{
				return true;
			}
		}
		
		// Returns false if element not found
		return false;
	}
	
	
	
	/*
	 *	Begins insertion process for RB Tree
	 *	insert() calls insertBST()
	 *	if insert() doesn't return NIL,
	 *		check for violations
	 *	if-else block probably not needed since
	 *		search() determines duplicate or not
	*/
	public boolean insert(K k, V v){
		
		Node<K, V> current = insertBST(k, v);
		
		if(current != NIL){
			
			checkRules(current);
		}
		else{
			return false;
		}
		
		return true;
	}
	
	
	
	/*	
	 * 	Creates a new node at position according to BST
	 * 	Insert key k, with value v into the binary search tree
	 * 	Returns with new node if the element is inserted successfully
	*/
	private Node<K, V> insertBST(K k, V v){
		
		// If empty list, create a new root node
		if(root == NIL){
			root = createNewNode(k, v, 'R');
			root.left = NIL;
			root.right = NIL;
			size++;
			return root;
		}
		else{
			Node<K, V> parent = null;
			Node<K, V> current = root;
			
			// This while block traverses down the list	
			while(current != NIL){			
				
				if(k.compareTo(current.getKey()) < 0){       	
					parent = current;							
					current = parent.left;						
				}	
				
				else if(k.compareTo(current.getKey()) > 0){		
					parent = current;							
					current = parent.right;				
				}	
				
				// Returns NIL since duplicate element found
				else{										
					return NIL;						
				}
			}
			
			// Creates a new node
			current = createNewNode(k, v, 'R');
			current.parent = parent;
			current.left = NIL;
			current.right = NIL;
			
			// Sets pointer for parent's left/right to new node
			if(k.compareTo(parent.getKey()) < 0){
				parent.left = current;
			}
			else{
				parent.right = current;
			}
			size++;
			return current;
		}
	}
	
	
	
	/*
	 *	Checks the 5 cases for violations and adjusts
	 *	Case 4: (N = P) since parent moves down, and current up,
	 *		set current = parent so that we can test Case 5
	*/
	private void checkRules(Node<K, V> current){
		
		// Case 1
		if(current == root){
			root.setColor('B');
			return;
		}
		
		// Case 2
		if(current.parent.getColor() == 'B'){
			return;
		}
		
		// Case 3
		if(current.parent.getColor() == 'R' && findUncle(current).getColor() == 'R'){
			current.parent.setColor('B');
			findUncle(current).setColor('B');
			findGP(current).setColor('R');
			
			//Recursively check with GP in case of violations
			checkRules(findGP(current));
			return;
		}
		
		// Case 4
		if(current.parent.getColor() == 'R' && findUncle(current).getColor() == 'B'){
			// Case 4a
			if(current == current.parent.right && current.parent == findGP(current).left){
				leftRotate(current);
				
				// N = P, sets the current as the previous Parent, to check with Case 5
				current = current.left;
			}
			// Case 4b
			if(current == current.parent.left && current.parent == findGP(current).right){
				rightRotate(current);
				
				// N = P, sets the current as the previous Parent, to check with Case 5
				current = current.right;			
			}
		}
		
		// Case 5
		if(current.parent.getColor() == 'R' && findUncle(current).getColor() == 'B'){
			// Case 5a
			if(current == current.parent.left && current.parent == findGP(current).left){
				current.parent.setColor('B');
				findGP(current).setColor('R');
				rightRotate(current.parent);
			}
			// Case 5b
			if(current == current.parent.right && current.parent == findGP(current).right){
				current.parent.setColor('B');
				findGP(current).setColor('R');
				leftRotate(current.parent);
			}
		}
	}
	
	
	
	/*
	 *	Creates a new node with specified parameters, and returns new node
	*/
	protected Node<K, V> createNewNode(K k, V v, char color){
		
		return new Node<>(k, v, color);
	}
	
	
	
	/*
	 * Begins traversal depending on type that called it
	 * Returns a String of Node keys in correct order
	 * 
	*/
	public String traversal(String type){
		
		List<String> keysList = new ArrayList<>();
		List<Node<K, V>> traversalList = new ArrayList<>();
		String keysString;
		
		
		switch(type){
		
			case "pre": traversalList = preorder();
						for(int index = 0; index < traversalList.size(); index++){
							keysList.add(String.valueOf(traversalList.get(index).getKey()));
						}
						break;
			case "ino": traversalList = inorder();
						for(int index = 0; index < traversalList.size(); index++){
							keysList.add(String.valueOf(traversalList.get(index).getKey()));
						}
						break;
			case "pos": traversalList = postorder();
						for(int index = 0; index < traversalList.size(); index++){
							keysList.add(String.valueOf(traversalList.get(index).getKey()));
						}
						break;
			case "brd": traversalList = breadthFirst();
						for(int index = 0; index < traversalList.size(); index++){
							keysList.add(String.valueOf(traversalList.get(index).getKey()));
						}
						break;
		}
		
		keysString = keysList.toString();
		
		return keysString;
	}
	
	
	
	/*
	 *	Preorder Traversal, returns an arrayList
	*/
	private List<Node<K, V>> preorder(){
		
		ArrayList<Node<K, V>> returnList = new ArrayList<>();
		Stack<Node<K, V>> newStack = new Stack<>();
		Node<K, V> current;
		
		
		// Push adds to top of stack
		newStack.push(root);
		
		
		/*
		 *	Root is already in stack
		 *	current = top of stack node, pop remove last
		 *	add current to List
		 *	push current.right to stack != NIL
		 *	push current.left to stack != NIL
		 *	 
		*/
		while(!newStack.empty()){
			current = newStack.pop();
			returnList.add(current);
			if(current.right != NIL){
				newStack.push(current.right);
			}
			if(current.left != NIL){
				newStack.push(current.left);
			}
		}
		
		return returnList;
	}
	
	
	
	/*
	 *	Inorder Traversal, returns an arrayList 
	*/
	private List<Node<K, V>> inorder(){
		
		ArrayList<Node<K, V>> returnList = new ArrayList<>();
		Stack<Node<K, V>> newStack = new Stack<>();
		Node<K, V> current = root;
		
		
		/*
		 *	Starts with current = root
		 *	if current != NIL
		 *		push current to top of stack
		 *		current = current.left
		 *	else current = top of stack, remove
		 *		add current to list
		 *		current = current.right 
		*/
		while(!newStack.empty() || current != NIL){
			if(current != NIL){
				newStack.push(current);
				current = current.left;
			}
			else{
				current = newStack.pop();
				returnList.add(current);
				current = current.right;
			}
		}
		
		return returnList;
	}
	
	
	
	/*
	 *	Postorder Traversal, returns an arrayList
	*/
	private List<Node<K, V>> postorder(){
		
		ArrayList<Node<K, V>> returnList = new ArrayList<>();
		Stack<Node<K, V>> newStack = new Stack<>();
		Set<Node<K, V>> visitedNodes = new LinkedHashSet<>();
		Node<K, V> current;
		
		
		newStack.push(root);
		
		
		/*
		 *	Starts with root pushed on to stack
		 *	current = top of stack
		 *	if current.left != NIL && current.left not visited
		 *		push current.left top of stack
		 *	else if current.right != NIL && current.right not visisted
		 *		push current.right top of stack
		 *	else add current to list
		 *		mark current as visisted
		 *		remove top element of stack
		 *		 
		*/
		while(!newStack.empty()){
			current = newStack.peek();
			if(current.left != NIL && visitedNodes.contains(current.left) == false){
				newStack.push(current.left);
			}
			else if(current.right != NIL && visitedNodes.contains(current.right) == false){
				newStack.push(current.right);
			}
			else{
				returnList.add(current);
				visitedNodes.add(current);
				newStack.pop();
			}
		}
		
		return returnList;
	}
	
	
	
	/*
	 *	Breadth-First Traversal, returns an arrayList
	*/
	private List<Node<K, V>> breadthFirst(){
		
		ArrayList<Node<K, V>> returnList = new ArrayList<>();
		Queue<Node<K, V>> newQueue = new LinkedList<>();
		Node<K, V> current;
		
		
		newQueue.offer(root);
		
		
		/*
		 *	Starts with root added to queue
		 * 	Sets current to head of queue
		 * 	Adds children of current if != NIL
		 * 	Adds current to List, remove from Queue
		 * 	Loop with current always = head of queue
		*/
		while(newQueue.isEmpty() != true){
			
			current = newQueue.peek();
			
			if(current.left != NIL){
				newQueue.offer(current.left);
			}
			if(current.right != NIL){
				newQueue.offer(current.right);
			}
			
			returnList.add(current);
			newQueue.remove(current);
		}
		
		return returnList;
	}
}
