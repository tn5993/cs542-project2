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
	
	public void testTransaction() throws Throwable {
		DBHelper<Integer> db1 = DBHelper.getInstance();
		System.out.println("Prepare data");
		for (int i = 0; i < 20; i++) {
			db1.put(Integer.valueOf(i), "GGG" + i);
		}
		
		TestRunnable tr1 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				System.out.println("Transaction 1 get");
				DBHelper<Integer> db = DBHelper.getInstance();
				String value = db.get(10);
				System.out.println(value);
			}
		};
		
		TestRunnable tr2 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				Thread.sleep(50);
				DBHelper<Integer> db = DBHelper.getInstance();
				System.out.println("Transaction 2 remove");
				db.remove("GGG10");
				
			}
		};
		
		TestRunnable tr3 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				System.out.println("Transaction 3 get");
				Thread.sleep(50);
				DBHelper<Integer> db = DBHelper.getInstance();
				Thread.sleep(50);
				String value = db.get(10);
				System.out.println(value);
			}
		};
		
		TestRunnable[] trs = { tr1, tr2, tr3 };
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		mttr.runTestRunnables();
	}
}
