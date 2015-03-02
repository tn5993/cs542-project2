package store;

public interface IRemovable<K extends Comparable<K>> {
	void remove(K key);
}
