package application;


public class Node<K extends Comparable<K>, V> {

	private K key;
	protected V value;
	protected char color;
	protected Node<K, V> left = null;
	protected Node<K, V> right = null;
	protected Node<K, V> parent = null;
	
	
	
	public Node(K key, V value, char color){
		this.key = key;
		this.value = value;
		this.color = color;
	}
	
	public K getKey(){
		return key;
	}
	
	public V getValue(){
		return value;
	}
	
	public void setColor(char color){
		this.color = color;
	}
	
	public char getColor(){
		return color;
	}
	
	public String toString(){
		return "\nKey: " + key + "\nValue: " + value + "\nColor: " + color;
	}
	
	
	
}
