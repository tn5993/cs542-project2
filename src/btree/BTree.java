package btree;

import java.util.Set;

public class BTree<Key extends Comparable<Key>> {
	private Integer t; // minimum number of keys in any node other than the root
	private Node<Key> root;

	private BTree(Integer t) {
		this.t = t;
		this.root = new Node<Key>(true);
	}

	public static <Key extends Comparable<Key>> BTree<Key> newInstance(Integer t) {
		return new BTree<Key>(t);
	}

	public Pair<Node<Key>, Integer> get(Key key) {
		return search(root, key);
	}
	
	private Pair<Node<Key>, Integer> search(Node<Key> x, Key key) {
		Integer i = 0;
		while (i < x.getKeyLength() && key.compareTo(x.getKey(i)) > 0) {
			i = i + 1;
		}
		if (i < x.getKeyLength() && key.equals(x.getKey(i))) {
			return Pair.newInstance(x, i);
		} else if (x.getIsLeaf()) {
			return null;
		} else {
			return search(x.getChild(i), key);
		}
	}

	private void splitChild(Node<Key> x, Integer i) {
		Node<Key> y = x.getChild(i);
		Node<Key> z = Node.newInstance(y.getIsLeaf());
		
		for (int j = 0; j < t - 1; j++) {
			z.insertKey(y.getKey(t));
			y.removeKey(t);
		}
		
		if (!y.getIsLeaf()) {
			for (int j = 0; j < t; j++) {
				z.insertChild(y.getChild(t));
				y.removeChild(t);
			}
		}
		
		x.insertChild(i+1, z);
		x.insertKey(y.getKey(t-1));
		y.removeKey(t-1);
	}

	public void put(Key k) {
		Node<Key> r = this.root;
		if (r.getKeyLength() == (2 * t - 1)) {
			Node<Key> s = new Node<Key>(false);
			this.root = s;
			s.insertChild(r);
			splitChild(s, 0);
			insertNonFull(s, k);
		} else {
			insertNonFull(r, k);
		}
	}

	private void insertNonFull(Node<Key> x, Key k) {
		if (x.getIsLeaf()) {
			x.insertKey(k);
		} else {
			Integer i = x.getKeyLength() - 1;
			while (i >= 0 && k.compareTo(x.getKey(i)) < 0) {
				i = i - 1;
			}
			i = i + 1;
			if (x.getChild(i).getKeyLength() == 2 * t - 1) {
				splitChild(x, i);
				if (k.compareTo(x.getKey(i)) > 0) {
					i = i + 1;
				}
			}
			insertNonFull(x.getChild(i), k);
		}
	}
	
	public void remove(Key key) {
		delete(root, key);
	}
	
	public void delete(Node<Key> x, Key key) {
		if (x.getIsLeaf()) {
			deleteFromLeaf(x, key);
		}
	}
	
	private void deleteFromLeaf(Node<Key> x, Key key) {
		Integer i = 0;
		while (i < x.getKeyLength() && key.compareTo(x.getKey(i)) > 0) {
			i = i + 1;
		}
		
		if (i < x.getKeyLength() && key.compareTo(x.getKey(i)) == 0) {
			x.removeKey(i);
		}
	}
	
	private void deleteFromInternalNode(int i) {
		
	}
	
	private Key findGreatestInSubTree(Node<Key> root) 
	{
		if (root.getIsLeaf()) {
			return root.getLastKey();
		} else {
			return findGreatestInSubTree(root.getLastChild());
		}
	}
	
	private Key findSmallestInSubTree(Node<Key> root) {
		if (root.getIsLeaf()) {
			return root.getFirstKey();
		} else {
			return findSmallestInSubTree(root.getFirstChild());
		}
	}

	public String walk(int depth)
	{
	    return root.toString();
	}

	public static void main(String[] args) {
		BTree<Integer> tree = BTree.newInstance(6);
		tree.put(10);
		tree.put(12);
		tree.put(13);
		tree.put(11);
		tree.put(14);
		tree.put(15);
	
		System.out.println(tree.walk(0));
		tree.remove(13);
		System.out.println(tree.walk(0));
	}

}
