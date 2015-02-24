package store;

public interface IPutable<T> {
	void Put(String key, T value);
}
