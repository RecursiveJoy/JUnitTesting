package testsuite;

import junit.framework.JUnit4TestAdapter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ HardwareStoreSpecialInputTest.class, HardwareStoreTest.class })

/**TestRunner is a class containing a main method which runs all of the tests in HardwareStoreSpecialInputTest
 * and HardwareStoreTest classes.
 */
public class TestRunner {

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TestRunner.class);
    }

    public static void main (String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
