package btree;

import java.util.ArrayList;

public class BNode<E> {

    public int level = 0;

    public ArrayList<E> keys = new ArrayList<E>();

    public boolean leaf = false;
    public ArrayList<BNode<E>> children = new ArrayList<BNode<E>>();
    public ArrayList<String> values = new ArrayList<String>();

    public BNode<E> parent = null;
    public BNode<E> rbrother = null;
}