package testsuite;

import hardwarestore.HardwareStore;
import hardwarestore.Item;
import org.junit.*;

import java.io.File;

import static junit.framework.TestCase.*;

/**class HardwareStoreSpecialInputTest has a series of tests that check method behavior when unusual values are input.
 * @Author Megan West
 */
public class HardwareStoreSpecialInputTest {

    public static HardwareStore hardwareStore = null;

    @Before
    public void createEnvironment() {
        try {
            hardwareStore = new HardwareStore();
        }catch(java.io.IOException e){
            System.out.println("IO Exception occurred while reading from file.");
        }
        hardwareStore.addNewItem("00000", "Brass Tacks", "Fasteners", 640, .30f);
        System.out.println("Method environment set up.");
    }

    @After
    public void clearEnvironment() {
        hardwareStore = null;
        File file = new File("database.txt");

        file.delete();
        System.out.println("Method environment cleaned up.\n");
    }

    /**testMessage lets us know that we are running the tests with normal input
     * @Author Megan West
     */
    @Test
    public void testMessage(){
        int x = 5;
        System.out.println("Now running Special Input Hardware Store Tests");
        assertEquals(5, x);
    }

    /** testAddQuantityInvalidIndex tests for a nullpointerexception.
     * @Author Megan West
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void testAddQuantityInvalidIndex(){
        hardwareStore.addQuantity(1, 1);
    }

    /** testAddNegativeQuantity tests addQuantity with a negative quantity.
     * @Author Megan West
     */
    @Test
    public void testAddNegativeQuantity(){
        hardwareStore.addQuantity(0, -1);
        assertEquals("Fail, expected 639",639, hardwareStore.getItem(0).getQuantity());
        System.out.println("Test Passed.");
    }

    /** testAddVeryLargeQuantity tests addQuantity with a very large quantity.
     * @Author Megan West
     */
    @Test
    public void testAddVeryLargeQuantity(){
        hardwareStore.addQuantity(0, 9000000);
        assertEquals("Fail, expected 9000640",9000640, hardwareStore.getItem(0).getQuantity());
        System.out.println("Test Passed.");
    }

    /** testRemoveNegativeQuantity() tests the removeQuantity method with a negative quantity being removed.
     * @Author Megan West
     */
    @Test
    public void testRemoveNegativeQuantity(){
        hardwareStore.removeQuantity(0, -1);
        assertEquals("Fail, expected 641",641, hardwareStore.getItem(0).getQuantity());
        System.out.println("Test Passed.");
    }

    /** testRemoveTooMuch() tests the removeQuantity method with a larger quantity being removed than present.
     * @Author Megan West
     */
    @Test
    public void testRemoveTooMuch(){
        hardwareStore.removeQuantity(0, 641);
        assertEquals("Fail. Expected -1", -1, hardwareStore.getItem(0).getQuantity());
        System.out.println("Test Passed.");
    }

    /** testGetMatchingItemsByNameId tests getMatchingItemsByName if the id is input instead.
     * @Author Megan West
     */
    @Test
    public void testGetMatchingItemsByNameNoMatch(){
        assertEquals("Fail. Expected null", null, hardwareStore.getMatchingItemsByName("00000"));
        System.out.println("Test Passed.");
    }

    /** testGetMatchingItemsWithNothing tests getMatchingItemsByName if enter is pressed but nothing is
     * input. This test fails because the programmer did not consider this input.
     * @Author Megan West
     */
    @Test
    public void testGetMatchingItemsWithNothing(){
        assertEquals("Fail. Expected null",null, hardwareStore.getMatchingItemsByName(""));
        System.out.println("Test Passed.");
    }

    /** testGetMatchingItemsByNegativeQuantity tests the return value of getMatchingItemsByQuantity when given a quantity
     * less than the quantity of the stored Test Item against the value that should be returned.
     * @Author Megan West
     */
    @Test
    public void testGetMatchingItemsByNegativeQuantity() {
        assertEquals("Fail. Expected null",null, hardwareStore.getMatchingItemsByQuantity(-1));
        System.out.println("Test Passed.");
    }

    /** testFindItemWithName tests the index value of findItem when given a name against the correct index value.
     * @author Megan West
     */
    @Test
    public void testFindItemWithName(){
        assertEquals("Fail. Expected -1",-1, hardwareStore.findItem("Tacks"));
        System.out.println("Test Passed.");
    }

    /** testFindItemWithNothing tests the index value of findItem when given an empty string against the correct index
     * value.
     * @author Megan West
     */
    @Test
    public void testFindItemWithNothing(){
        assertEquals("Fail. Expected -1",-1, hardwareStore.findItem(""));
        System.out.println("Test Passed.");
    }

    /**testGetInvalidIndex tests what happens when we try to get an item from the ArrayList at an invalid index.
     * @Author Megan West
     */
    @Test
    public void testGetInvalidIndex(){
        Item item = hardwareStore.getItem(1);
        assertNull("Fail. Expected null", item);
        System.out.println("Test Passed.");
    }

}
