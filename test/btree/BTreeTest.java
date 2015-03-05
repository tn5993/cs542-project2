package btree;

import junit.framework.TestCase;

public class BTreeTest extends TestCase {
	private BTree<Integer> numberTree;
	private BTree<String> stringTree;
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
		this.numberTree = new BTree<Integer>(this.m);
		this.stringTree = new BTree<String>(this.m);
	}
	/* Insert key and value and get value by key*/
	public void testInsertAndGet() {
		for (int i = 0; i < 1000; i++)
			numberTree.insert(i, "GGG" + i);
		
		for (int i = 0; i < 1000; i++) {
			String value = numberTree.search(i);
			assertEquals("GGG" +i , value);
		}
	}
	
	/* Insert key and value and get value by key*/
	public void testInsertAndGetStringTree() {
		for (int i = 0; i < 1000; i++)
			stringTree.insert("Wow" + i, "GGG" + i);
		
		for (int i = 0; i < 1000; i++) {
			String value = stringTree.search("Wow" + i);
			assertEquals("GGG" +i , value);
		}
	}
	
	/* Insert by <key, value> pair, delete by value and get by key*/
	public void testDelete() {
		for (int i = 0; i < 1000; i++) {
			numberTree.insert(i, "Value" +i);
		}
		assertEquals("Value10", numberTree.search(10));
		numberTree.delete("Value10");
		assertNull(numberTree.search(10)); //value 10 is deleted
	}
	
	/* Insert by <key, value> pair, delete by value and get by key*/
	public void testDeleteStringTree() {
		for (int i = 0; i < 1000; i++) {
			stringTree.insert("Wow"+i, "Value" +i);
		}
		assertEquals("Value10", stringTree.search("Wow10"));
		stringTree.delete("Value10");
		assertNull(stringTree.search("Wow10")); //value 10 is deleted
	}
	
	/* validate that each node has maximum m node and minimum (m+1)/2 node*/
	public void testIsValidBTree() { 
		for (int i = 0; i < 1000; i++) {
			numberTree.insert(i, "Value" +i);
		}
		assertTrue(numberTree.isValid());
	}
	
	/* validate that each node has maximum m node and minimum (m+1)/2 node*/
	public void testIsValidBTreeString() { 
		for (int i = 0; i < 1000; i++) {
			stringTree.insert("Wow"+i, "Value" +i);
		}
		assertTrue(stringTree.isValid());
	}
	
	/* validate that the tree do not allow duplicate key*/
	public void testDuplicateKey() {
		numberTree.insert(10, "Value10");
		numberTree.insert(10, "Value100");
		assertEquals("Value10", numberTree.search(10));
		numberTree.delete("Value10");
		assertNull(numberTree.search(10));
	}
	
	/* validate that the tree do not allow duplicate key*/
	public void testDuplicateKeyStringTree() {
		stringTree.insert("Wow10", "Value10");
		stringTree.insert("Wow10", "Value100");
		assertEquals("Value10", stringTree.search("Wow10"));
		stringTree.delete("Value10");
		assertNull(stringTree.search("Wow10"));
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public BTree<Integer> getTree() {
		return numberTree;
	}

}
