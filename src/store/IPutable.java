package store;

public interface IPutable<K extends Comparable<K>> {
	void put(K key);
}
