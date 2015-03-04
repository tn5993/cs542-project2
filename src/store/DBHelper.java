package store;

import btree.BTree;

public class DBHelper<K extends Comparable<K>> implements IPutable<K>,
		IGetable<K>, IRemovable {
	private BTree<K> tree;
	private static DBHelper<?> helperInstance;

	private DBHelper() {
		tree = BTree.newInstance(5);
	}

	@SuppressWarnings("unchecked")
	public synchronized static <K extends Comparable<K>> DBHelper<K> getInstance() {
		if (helperInstance == null) {
			helperInstance = new DBHelper<K>();
		}
		
		return (DBHelper<K>) helperInstance;
	}

	@Override
	public void put(K key, String value) {
		synchronized (helperInstance) { // Thread safe
			tree.insert(key, value);
		}
	}

	@Override
	public void remove(String value) {
		synchronized (helperInstance) { // Thread safe
			tree.delete(value);
		}
	}

	@Override
	public String get(K key) {
		synchronized (helperInstance) { // Thread safe
			return tree.search(key);
		}
	}

	public BTree<K> getTree() {
		return tree;
	}
	
	public void printTree() {
		this.tree.print();
	}
}
