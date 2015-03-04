package btree;

import junit.framework.TestCase;

public class BTreeTest extends TestCase {
	private BTree<Integer> tree;
	private final Integer m = 5;
	
	public BTreeTest() {
		super();
		
	}
	
	protected static void setUpBeforeClass() throws Exception {
	}

	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
		this.tree = BTree.newInstance(this.m);
	}
	/* Insert key and value and get value by key*/
	public void testInsertAndGet() {
		for (int i = 0; i < 1000; i++)
			tree.insert(i, "GGG" + i);
		
		for (int i = 0; i < 1000; i++) {
			String value = tree.search(i);
			assertEquals("GGG" +i , value);
		}
	}
	/* Insert by <key, value> pair, delete by value and get by key*/
	public void testDelete() {
		for (int i = 0; i < 1000; i++) {
			tree.insert(i, "Value" +i);
		}
		assertEquals("Value10", tree.search(10));
		tree.delete("Value10");
		assertNull(tree.search(10)); //value 10 is deleted
	}
	
	/* validate that each node has maximum m node and minimum (m+1)/2 node*/
	public void testIsValidBTree() { 
		for (int i = 0; i < 1000; i++) {
			tree.insert(i, "Value" +i);
		}
		assertTrue(tree.isValid());
	}
	
	/* validate that the tree do not allow duplicate key*/
	public void testDuplicateKey() {
		tree.insert(10, "Value10");
		tree.insert(10, "Value100");
		assertEquals("Value10", tree.search(10));
		tree.delete("Value10");
		assertNull(tree.search(10));
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public BTree<Integer> getTree() {
		return tree;
	}

}
