package store;
import junit.framework.TestCase;
import store.DBHelper;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;


public class DBHelperTest extends TestCase{
	public static void main(String[] args) {
		String[] name = { DBHelperTest.class.getName() };
		junit.textui.TestRunner.main(name);
	}
	
	/* Transaction 1 get, Transaction 2 remove, Transaction 3 get */
	public void testTransaction() throws Throwable {
		DBHelper db1 = DBHelper.getInstance();
		System.out.println("Prepare data");
		for (int i = 0; i < 10001; i++) {
			db1.put("Number" + i,Integer.valueOf(i));
		}
		
		TestRunnable tr1 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				System.out.println("Transaction 1 get");
				DBHelper db = DBHelper.getInstance();
				String value = db.get(10000);
				System.out.println(value);
			}
		};
		
		TestRunnable tr2 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				DBHelper db = DBHelper.getInstance();
				System.out.println("Transaction 2 remove");
				db.remove("Number10000");
			}
		};
		
		TestRunnable tr3 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				System.out.println("Transaction 3 get");
				DBHelper db = DBHelper.getInstance();
				String value = db.get(10000);
				System.out.println(value);
			}
		};
		
		TestRunnable[] trs = { tr1, tr2, tr3 };
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		mttr.runTestRunnables();
	}
	
	/* Transaction 1 get, Transaction 2 remove, Transaction 3 get */
	public void testTransactionStringAndNumberTree() throws Throwable {
		DBHelper db1 = DBHelper.getInstance();
		System.out.println("Prepare data");
		for (int i = 0; i < 10001; i++) {
			db1.put("Number" + i,Integer.valueOf(i));
		}
		
		for (int i = 0; i < 10001; i++) {
			db1.put("String" +i, "Wow" +i);
		}
		
		TestRunnable tr1 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				System.out.println("Transaction 1 get");
				DBHelper db = DBHelper.getInstance();
				String value = db.get("Wow10000");
				System.out.println(value);
			}
		};
		
		TestRunnable tr2 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				DBHelper db = DBHelper.getInstance();
				System.out.println("Transaction 2 remove");
				db.remove("String10000");
			}
		};
		
		TestRunnable tr3 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				System.out.println("Transaction 3 get");
				DBHelper db = DBHelper.getInstance();
				String value = db.get("Wow10000");
				System.out.println(value);
			}
		};
		
		TestRunnable[] trs = { tr1, tr2, tr3 };
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		mttr.runTestRunnables();
	}
}
