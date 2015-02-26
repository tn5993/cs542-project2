package store;

public interface IPutable<K,V> {
	void Put(K key, V value);
}
