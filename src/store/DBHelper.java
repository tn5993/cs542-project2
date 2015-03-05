package store;

import btree.BTree;

public class DBHelper implements IPutable, IGetable, IRemovable {
	private BTree<Number> numberTree;
	private BTree<String> stringTree;
	private static DBHelper Instance;

	private DBHelper() {
		numberTree = new BTree<Number>(4);
		stringTree = new BTree<String>(4);
	}

	public synchronized static DBHelper getInstance() {
		if (Instance == null) {
			Instance = new DBHelper();
		}
		
		return Instance;
	}

	@Override
	public void put(String key, Number value) {
		synchronized (Instance) { // Thread safe
			numberTree.insert(value, key);
		}
	}

	@Override
	public void remove(String value) {
		synchronized (Instance) { // Thread safe
			numberTree.delete(value);
			stringTree.delete(value);
		}
	}

	@Override
	public String get(Number key) {
		synchronized (Instance) { // Thread safe
			return numberTree.search(key);
		}
	}


	@Override
	public String get(String key) {
		synchronized (Instance) {
			return stringTree.search(key);
		}
	}

	@Override
	public void put(String key, String value) {
		synchronized (Instance) {
			stringTree.insert(value, key);
		}
	}
}
