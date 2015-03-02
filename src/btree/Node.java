package btree;

import java.util.ArrayList;
import java.util.List;

public class Node<K extends Comparable<K>> {
	private Boolean isLeaf;
	private List<K> keys;
	private List<Node<K>> children;

	public Node(Boolean isLeaf) {
		this.isLeaf = isLeaf;
		this.keys = new ArrayList<K>();
		this.children = new ArrayList<Node<K>>();
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public K getKey(Integer i) {
		return keys.get(i);
	}
	
	public void insertKey(K key) {
		if (keys.isEmpty()) {
			keys.add(key);
		} else {
			Integer i = 0;
			while (i < keys.size() && keys.get(i).compareTo(key) < 0) {
				i = i + 1;
			}
			keys.add(i, key);
		}
	}
	
	public void removeKey(int i) {
		keys.remove(i);
	}
	
	
	public void insertChild(Integer i, Node<K> c) {
		children.add(i, c);
	}
	
	public void insertChild(Node<K> c) {
		children.add(c);
	}

	public void removeChild(int i) {
		children.remove(i);
	}

	public Integer getKeyLength() {
		return keys.size();
	}
	
	public Integer getChildLength() {
		return children.size();
	}

	public Node<K> getFirstChild() {
		Node<K> result = null;
		if (!children.isEmpty()) {
			result = children.get(0);
		}
		return result;
	}
	
	public K getFirstKey() {
		K key = null;
		if (!keys.isEmpty()) {
			key = keys.get(0);
		}
		return key;
	}
	
	public K getLastKey() {
		return keys.get(keys.size()-1);
	}
	
	public Node<K> getLastChild() {
		return children.get(children.size()-1);
	}
	
	public Node<K> getChild(Integer i) {
		return this.children.get(i);
	}
	
	public static <K extends Comparable<K>> Node<K> newInstance(Boolean isLeaf) {
		return new Node<K>(isLeaf);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(Node: ");
		builder.append("Keys: ").append(keys.toString());
		builder.append(" Children: ");
		if (children.isEmpty()) {
			builder.append("null").append(")");
		} else {
			builder.append(children.toString()).append(")");
		}

	    return builder.toString();
	}

}
