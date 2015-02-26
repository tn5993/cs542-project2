package btree;

public class BTree<Key extends Comparable<Key>> {
	private Integer t; // minimum number of keys in any node other than the root
	private Node<Key> root;

	public BTree(Integer t) {
		this.t = t;
		this.root = new Node<Key>(true);
	}

	public static <Key extends Comparable<Key>> BTree<Key> newInstance(Integer t) {
		return new BTree<Key>(t);
	}

	public Pair<Node<Key>, Integer> get(Key key) {
		return search(root, key);
	}
	
	public Pair<Node<Key>, Integer> search(Node<Key> x, Key key) {
		Integer i = 0;
		while (i < x.getLength() && key.compareTo(x.getKey(i)) > 0) {
			i = i + 1;
		}
		if (i < x.getLength() && key.equals(x.getKey(i))) {
			return Pair.newInstance(x, i);
		} else if (x.getIsLeaf()) {
			return null;
		} else {
			return search(x.getChild(i), key);
		}
	}

	public void splitChild(Node<Key> x, Integer i) {
		Node<Key> y = x.getChild(i);
		Node<Key> z = Node.newInstance(y.getIsLeaf());
		
		for (Integer j = 0; j < t - 1; j++) {
			z.insertKey(y.getKey(j+t));
			y.removeKey(j+t);
		}
		
		if (!y.getIsLeaf()) {
			for (Integer j = 0; j < t; j++) {
				z.insertChild(y.getChild(j+t));
				y.removeChild(j+t);
			}
		}
		x.insertChild(i+1, z);
		x.insertKey(y.getKey(t-1));
		y.removeKey(t-1);
	}

	public void insert(Key k) {
		Node<Key> r = this.root;
		if (r.getLength() == (2 * t - 1)) {
			Node<Key> s = new Node<Key>(false);
			this.root = s;
			s.insertChild(r);
			splitChild(s, 0);
			insertNonFull(s, k);
		} else {
			insertNonFull(r, k);
		}
	}

	public void insertNonFull(Node<Key> x, Key k) {
		if (x.getIsLeaf()) {
			x.insertKey(k);
		} else {
			Integer i = x.getLength() - 1;
			while (i >= 0 && k.compareTo(x.getKey(i)) < 0) {
				i = i - 1;
			}
			i = i + 1;
			if (x.getChild(i).getLength() == 2 * t - 1) {
				splitChild(x, i);
				if (k.compareTo(x.getKey(i)) > 0) {
					i = i + 1;
				}
			}
			insertNonFull(x.getChild(i), k);
		}
	}

	public String walk(int depth)
	{
	    return root.toString();
	}

	public static void main(String[] args) {
		BTree<Integer> tree = BTree.newInstance(2);
		tree.insert(10);
		tree.insert(12);
		tree.insert(13);
		tree.insert(11);
		tree.insert(14);
		tree.insert(15);
		tree.insert(16);

		Pair<Node<Integer>, Integer> result = tree.get(11);
		System.out.println(tree.walk(0));
	}

}
