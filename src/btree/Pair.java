package btree;

public class Pair<X, Y> {

	public final X x;
	public final Y y;

	private Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	public static <X, Y> Pair<X, Y> newInstance(X x, Y y) {
		return new Pair<X, Y>(x, y);
	}
}
