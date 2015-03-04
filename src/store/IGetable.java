package store;


public interface IGetable<K extends Comparable<K>> {
	String get(K key);
}