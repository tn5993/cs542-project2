package store;

import btree.BTree;
import btree.Node;
import btree.Pair;

public class DBHelper<K extends Comparable<K>> implements IPutable<K>, IGetable<K>, IRemovable<K> {
	private BTree<K> tree;
	private static DBHelper<?> helperInstance;
	
	private DBHelper() {
		tree = BTree.newInstance(5);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static <K extends Comparable<K>> DBHelper<K> getInstance () {
		if (helperInstance == null) {
			helperInstance = new DBHelper<K>();
		}
		
		return (DBHelper<K>) helperInstance;
	}
	
	@Override
	public void put(K key) {
		synchronized (helperInstance) { //Thread safe
			tree.put(key);
		}
	}

	@Override
	public void remove(K key) {
		synchronized (helperInstance) { //Thread safe
			tree.remove(key);
		}
	}

	@Override
	public Pair<Node<K>, Integer> get(K key) {
		synchronized (helperInstance) { //Thread safe
			return tree.get(key);
		}
	}

	public BTree<K> getTree() {
		return tree;
	}
}
