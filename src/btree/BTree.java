package btree;

import java.util.LinkedList;
import java.util.Queue;

public class BTree<T> {

    private int _m = 4;

    private int _min = (_m+1)/2;
    private BNode<T> _root = null;

    private BTree(int m) {
        _m = m;
        _min = (_m+1)/2;
    }

    public static <T> BTree<T> newInstance(Integer m) {
    	return new BTree<T>(m);
    }

    // INSERTION
    //////////////////////////////////////////////////////////////////////
    /**
     * insert this pair to b+ tree
     *
     * @param index
     * @param value
     * @return
     */
    public boolean insert(T index, String value) {
        // the tree only has root node
        if (_root==null || _root.children.size()<=0) {
            // this is the first index, build the root node
            if (_root==null) {
                _root = new BNode<T>();
                _root.leaf = true;
                _root.keys.add(index);
                _root.values.add(value);
            }
            insert(_root, index, value);
            return true;

        // root node is full
        } else {
            BNode<T> leaf = search(_root, index);
            insert(leaf, index, value);
        }

        return true;
    }

    /**
     * insert this pair to BNode:current
     *
     * @param current
     * @param index
     * @param value
     * @return
     */
    private boolean insert(BNode<T> current,T index, String value) {
        if (current==null) {
            return false;
        }

        int location = -1;
        for(T indexs: current.keys) {
            int r = compare(index, indexs);

            // the index existed
            if (r==0) {
                return false;

            // the index is larger than the current one, keep going
            } else if (r>0) {
                location += 1;

            // the index is smaller than the current one, this is the right place to insert
            } else if (r<0) {
                break;
            }
        }
        current.values.add(location+1, value);
        current.keys.add(location+1, index);

        division(current);
        return true;
    }

    /**
     * to ensure that the number of indexes is between [m/2, m]
     * check recursively
     *
     * @param current
     */
    private void division(BNode<T> current) {
        // no need to divide
        if (current.keys.size()<=_m) {
            return;
        }

        BNode<T> parent = current.parent;
        BNode<T> right = new BNode<T>();

        // establish the relation between current, parent and right brother
        right.parent = current.parent;
        right.leaf = current.leaf;
        right.rbrother = current.rbrother;
        current.rbrother = right;

        // cut left of the node to its right brother
        int num_keys = current.keys.size();
        for (int i=_min; i<num_keys; i+=1) {
            right.keys.add(current.keys.get(_min));
            current.keys.remove(_min);
            if (current.leaf) {
                right.values.add(current.values.get(_min));
                current.values.remove(_min);
            } else {
                right.children.add(current.children.get(_min));
                current.children.get(_min).parent = right;
                current.children.remove(_min);
            }
        }

        // if this is root, then get a new root
        // and no more division
        if (parent == null) {
            BNode<T> root = new BNode<T>();
            root.leaf = false;
            root.keys.add(current.keys.get(0));
            root.keys.add(right.keys.get(0));
            root.children.add(current);
            root.children.add(right);

            current.parent = root;
            right.parent = root;

            _root = root;

        // add rbrother to parent
        } else {
            int index_of_right = parent.children.indexOf(current) + 1;
            parent.keys.add(index_of_right, right.keys.get(0));
            parent.children.add(index_of_right, right);

            // keep check and division
            division(parent);
        }
    }


    // SEARCH
    //////////////////////////////////////////////////////////////////////
    /**
     * return the value for this index
     *
     * @param index
     * @return
     */
    public String search(T index) {
        BNode<T> result = search(_root, index);
        if (result==null || result.keys.indexOf(index)<0) {
            return null;
        }
        return result.values.get(result.keys.indexOf(index));
    }

    /**
     * search node the index should be
     *
     * @param current
     * @param index
     * @return
     */
    private BNode<T> search(BNode<T> current, T index) {
        // every search will eventually lead to only one leaf, this is the exit for iteration
        if (current.leaf) {
            return current;

        } else {
            int len = current.keys.size();
            for (int i=len-1; i>=0; i-=1) {
                if (compare(index, current.keys.get(i))>=0) {
                    return search(current.children.get(i), index);
                }
            }
        }
        return null;
    }

    /**
     * search the node that contains the value
     * @param value
     * @return
     */
    private BNode<T> search(String value) {
        BNode<T> result = null;
        BNode<T> tmp = getLeftMostLeaf();

        while (tmp!=null) {
            if (tmp.values.contains(value)) {
                result = tmp;
                break;
            }
            tmp = tmp.rbrother;
        }

        return result;
    }

    /**
     *
     * @return
     *     the left most leaf of the b+ tree
     */
    private BNode<T> getLeftMostLeaf() {
        BNode<T> left_most = null;

        BNode<T> tmp = _root;

        while(tmp!=null) {
            if (tmp.leaf) {
                left_most = tmp;
                break;
            }
            tmp = tmp.children.get(0);
        }

        return left_most;
    }



    // DELETE
    //////////////////////////////////////////////////////////////////////
//    public void delete(T index) {
//        BNode<T> current = search(_root, index);
//        if (current==null || current.keys.indexOf(index)<0){
//            return;
//        }
//        int location = current.keys.indexOf(index);
//        delete(current, location);
//    }

    /**
     * delete this value from b+ tree
     * @param value
     */
    public void delete(String value) {
        BNode<T> current = search(value);
        if (current==null) {
            return;
        }
        int location = current.values.indexOf(value);
        delete(current, location);
    }

    /**
     * delete the index at this location
     *
     * @param current
     * @param location
     */
    private void delete(BNode<T> current, int location) {

        current.keys.remove(location);
        if (current.leaf) {
            current.values.remove(location);
        } else {
            current.children.remove(location);
        }

        if (current==_root) {
            return;
        }

        int size = current.keys.size();
        if (size>=_min) {
            if (location==0 && size>0) {
                reindex(current.parent, location, current.keys.get(0));
            }
            return;
        }

        BNode<T> parent = current.parent;
        BNode<T> left = null, right = null, brother = null;
        int size_left = -1, size_right = -1;
        int location_current = parent.children.indexOf(current);

        // find the left brother
        if (location_current>0) {
            left = parent.children.get(location_current-1);
            size_left = left.keys.size();
        }
        // find the right brother
        if (location_current<size-1 && location_current>=0) {
            right = parent.children.get(location_current+1);
            size_right = right.keys.size();
        }

        if (size_left>_min) {
            borrow(current, left, Brother.LEFT);
            parent.keys.set(location_current, left.keys.get(size-1));

        } else if (size_right>_min) {
            borrow(current, right, Brother.RIGHT);
            parent.keys.set(location_current+1, right.keys.get(1));

        } else if (size_left<=_min && size_left>=0 || size_right<=_min && size_right>=0) {
            if (size_left<=_min && size_left>=0) {
                brother = left;
                combine(current, left, Brother.LEFT);
            } else {
                brother = right;
                combine(current, right, Brother.RIGHT);
                parent.keys.set(location_current+1, right.keys.get(0));
            }

            delete(parent, location_current);
            if (parent==_root && _root.children.size()<=2) {
                _root = brother;
            }
        }
    }

    /**
     * if the first index changed, should change its parent's index recursively
     *
     * @param current
     * @param location
     * location of index that should be replaced by the newIndex
     * @param newIndex
     */
    private void reindex(BNode<T> current, int location, T newIndex) {
        T index = current.keys.get(location);
        current.keys.set(location, newIndex);

        if (current.parent!=null) {
            location = current.parent.keys.indexOf(index);
            if (location==0) {
                reindex(current.parent, location, newIndex);
            }
        }
    }

    /**
     * if the number of indexes is below _min, should borrow an index from its brothers
     *
     * @param current
     * @param brother
     * @param type
     */
    private void borrow(BNode<T> current, BNode<T> brother, Brother type) {

        int size_brother = brother.keys.size();
        if (size_brother<0) {
            return;
        }

        // borrow from left brother
        if (type==Brother.LEFT) {
            current.keys.add(0, brother.keys.get(size_brother-1));
            brother.keys.remove(size_brother-1);
            if (current.leaf) {
                current.values.add(0, brother.values.get(size_brother-1));
                brother.values.remove(size_brother-1);
            } else {
                current.children.add(0, brother.children.get(size_brother-1));
                brother.children.remove(size_brother-1);
            }

        // borrow from right brother
        } else if (type==Brother.RIGHT) {
            current.keys.add(brother.keys.get(0));
            brother.keys.remove(0);
            if (current.leaf) {
                current.values.add(brother.values.get(0));
                brother.values.remove(0);
            } else {
                current.children.add(brother.children.get(0));
                brother.children.remove(0);
            }
        }
    }

    /**
     * if the sum of indexes and its brother indexes is below _min, should combine these two
     *
     * @param current
     * @param brother
     * @param type
     */
    private void combine(BNode<T> current, BNode<T> brother, Brother type) {
        int size = current.keys.size();

        // combine with left brother
        if (type==Brother.LEFT) {
            for (int i=0; i<size; i+=1) {
                brother.keys.add(current.keys.get(i));
            }
            if (current.leaf) {
                for (int i=0; i<size; i+=1) {
                    brother.values.add(current.values.get(i));
                }
            } else {
                for (int i=0; i<size; i+=1) {
                    brother.children.add(current.children.get(i));
                }
            }

        // combine with right brother
        } else if (type==Brother.RIGHT) {
            for (int i=size-1; i>=0; i-=1) {
                brother.keys.add(0, current.keys.get(i));
            }
            if (current.leaf) {
                for (int i=size-1; i>=0; i-=1) {
                    brother.values.add(0, current.values.get(i));
                }
            } else {
                for (int i=size-1; i>=0; i-=1) {
                    brother.children.add(0, current.children.get(i));
                }
            }
        }
    }


    // TOOL
    //////////////////////////////////////////////////////////////////////
    public void print() {
        Queue<BNode<T>> queue = new LinkedList<BNode<T>>();
        queue.add(_root);
        _root.level = 0;
        System.out.println();
        while (!queue.isEmpty()) {
            BNode<T> current = queue.poll();
            System.out.print("level: "+current.level+" "+current.leaf+" ");
            for (T index: current.keys) {
                System.out.print(index+", ");
            }
            for (BNode<T> child: current.children) {
                child.level = current.level + 1;
                queue.add(child);
            }
            System.out.print("\t\tparent: ");
            if (current.parent!=null) {
                for (T index: current.parent.keys) {
                    System.out.print(index+",");
                }
            }
            System.out.println();
        }
    }
    
    /* validate if all node in the tree satisfy min <= number of keys <= max */
    public boolean isValid() {
    	boolean result = true;
    	Queue<BNode<T>> queue = new LinkedList<BNode<T>>();
    	queue.add(_root);
    	while (!queue.isEmpty()) {
    		BNode<T> node = queue.poll();
    		boolean isValidNode = node.keys.size() <= _m && node.keys.size() >= _min;
    		if (!isValidNode) {
    			result = false;
    		}
    		for (BNode<T> child : node.children) {
    			queue.add(child);
    		}
    	}
    	
		return result;
    }

    public int getM() {
        return _m;
    }

    public int getMin() {
        return _min;
    }

    private enum Brother {
        LEFT, RIGHT;
    }

    private int compare(T e1,T e2) {
        if (e1 instanceof Integer && e2 instanceof Integer) {
            return ((Integer) e1).compareTo((Integer) e2);
        }
        return ((String) e1).compareTo((String) e2);
    }
}