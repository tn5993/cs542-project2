package store;

import btree.Node;
import btree.Pair;

public interface IGetable<K extends Comparable<K>> {
	Pair<Node<K>, Integer> get(K value);
}
