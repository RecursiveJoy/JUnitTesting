package testsuite;

import hardwarestore.HardwareStore;
import hardwarestore.Item;
import org.junit.*;
import java.io.*;

import static junit.framework.TestCase.*;

/** Class HardwareStoreTest contains test cases for normal use of every method in the HardwareStore class.
 * @author Megan West
 */
public class HardwareStoreTest {

    public static HardwareStore hardwareStore = null;

    /**createMethodEnvironment() creates a fresh HardwareStore and adds a mock item to element 0 of its array.
     * the constructor will attempt to read from database.txt into this array so it is surrounded by a try-catch block.
     * @author Megan West
     */
    @Before
    public void createMethodEnvironment() {
        try {
            hardwareStore = new HardwareStore();
        }catch(java.io.IOException e){
            System.out.println("IO Exception occurred while reading from file.");
        }
        hardwareStore.addNewItem("00000", "Brass Tacks", "Fasteners", 640, .30f);
        System.out.println("Method environment set up.");
    }

    /**nullifyHardwareStore() sets the address of hardwareStore to null without writing anything to file. So we can
     * create a new one for the next method without worrying if something was left in its array to mess up our tests.
     * @author Megan West
     */
    @After
    public void nullifyHardwareStore() {
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
        System.out.println("Now running Normal Hardware Store Tests");
        assertEquals(5, x);
    }

    /**testGetItem() tests the getItem method by comparing the item that was added to the store with the item that
     * should be in the store.
     * @author Megan West
     */
    @Test
    public void testGetItem(){
        Item item = new Item("00000", "Brass Tacks", "Fasteners", 640, .30f);
        assertTrue(item.equals(hardwareStore.getItem(0)));
        System.out.println("Test Passed.");
    }

    /**testGetAllItemsFormatted tests getAllItemsFormatted() to ensure that the displayed output matches the string that
     * should be displayed. We did this by manually building the outputted table and storing it to a String.
     * @author Megan West
     */
    @Test
    public void testGetAllItemsFormatted(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.out.print(hardwareStore.getAllItemsFormatted());

        String output = outContent.toString();
        System.out.println(output);

        String text = " ------------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n", "ID Number", "Name", "Category", "Quantity", "Price") +
                " ------------------------------------------------------------------------------------\n";

        text += String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n",
                    "00000",
                    "Brass Tacks",
                    "Fasteners",
                    Integer.toString(640),
                    String.format("%.2f", .30f));
        text += " ------------------------------------------------------------------------------------\n";

        assertEquals("Fail. Should be true.", text, output);
        System.out.println("Test Passed.");
    }


    /** testAddQuantity tests the addQuantity method with a positive number and checks that the quantity of the stored
     * item matches the expected quantity.
     * @Author Megan West
     */
    @Test
    public void testAddQuantity(){

        hardwareStore.addQuantity(0, 1);
        assertEquals("Fail, should be 641",641, hardwareStore.getItem(0).getQuantity());
        System.out.println("Test Passed.");
    }

    /** testAddQuantityWithNegative() tests the addQuantity method with a zero value and checks that the quantity
     *  of the stored item matches the expected quantity.
     * @Author Megan West
     */
    @Test
    public void testAddQuantityZero(){
        hardwareStore.addQuantity(0, 0);
        assertEquals("Fail, should be 640",640, hardwareStore.getItem(0).getQuantity());
        System.out.println("Test Passed.");
    }

    /** testRemoveQuantity tests removeQuantity with a positive quantity being removed.
     * @Author Megan West
      */
    @Test
    public void testRemoveQuantity(){
        hardwareStore.removeQuantity(0, 1);
        assertEquals("Fail, should be 639",639, hardwareStore.getItem(0).getQuantity());
        System.out.println("Test Passed.");
    }

    /** testRemoveQuantityWithZero() tests the removeQuantity method with a zero quantity being removed.
     * @Author Megan West
     */
    @Test
    public void testRemoveQuantityWithZero(){
        hardwareStore.removeQuantity(0, 0);
        assertEquals("Fail, should be 640",640, hardwareStore.getItem(0).getQuantity());
        System.out.println("Test Passed.");
    }

    /**testGetMatchingItemsByName tests getMatchingItemsByName with a string that matches part of the stored item's name.
     * @Author Megan West
     */
    @Test
    public void testGetMatchingItemsByName(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.out.print(hardwareStore.getMatchingItemsByName("Tacks"));

        String output = outContent.toString();

        String text = " ------------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n", "ID Number", "Name", "Category", "Quantity", "Price") +
                " ------------------------------------------------------------------------------------\n";

        text += String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n",
                "00000",
                "Brass Tacks",
                "Fasteners",
                Integer.toString(640),
                String.format("%.2f", .30f));
        text += " ------------------------------------------------------------------------------------\n";

        assertEquals("Fail, should be equal",text, output);
        System.out.println("Test Passed.");
    }

    /** testGetMatchingItemsByNameNoMatch tests getMatchingItemsByName if there is no match for the provided String
     * in the database.
     * @Author Megan West
     */
    @Test
    public void testGetMatchingItemsByNameNoMatch(){
        assertEquals("Fail, should be null",null, hardwareStore.getMatchingItemsByName("Cabinet"));
        System.out.println("Test Passed.");
    }

    /** testGetMatchingItemsByQuantity tests the output result of getMatchingItemsByQuantity when given a quantity
     * greater than the quantity of the stored Test Item against the value of the String table that should appear.
     * @Author Megan West
     */
    @Test
    public void testGetMatchingItemsByQuantity(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.out.print(hardwareStore.getMatchingItemsByQuantity(750));

        String output = outContent.toString();

        String text = " ------------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n", "ID Number", "Name", "Category", "Quantity", "Price") +
                " ------------------------------------------------------------------------------------\n";

        text += String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n",
                "00000",
                "Brass Tacks",
                "Fasteners",
                Integer.toString(640),
                String.format("%.2f", .30f));
        text += " ------------------------------------------------------------------------------------\n";

        assertEquals("fail: should be equal", text, output);
        System.out.println("Test Passed.");
    }

    /** testGetMatchingItemsByQuantity tests the output result of getMatchingItemsByQuantity when given a quantity
     * equal to the quantity of the stored Test Item against the value of the String table that should appear.
     * @Author Megan West
     */
    @Test
    public void testGetMatchingItemsByQuantityEquals(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.out.print(hardwareStore.getMatchingItemsByQuantity(640));

        String output = outContent.toString();

        String text = " ------------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n", "ID Number", "Name", "Category", "Quantity", "Price") +
                " ------------------------------------------------------------------------------------\n";

        text += String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n",
                "00000",
                "Brass Tacks",
                "Fasteners",
                Integer.toString(640),
                String.format("%.2f", .30f));
        text += " ------------------------------------------------------------------------------------\n";

        assertEquals("Fail: should be equal", text, output);
        System.out.println("Test Passed.");
    }

    /** testGetMatchingItemsByQuantity tests the return value of getMatchingItemsByQuantity when given a quantity
     * less than the quantity of the stored Test Item against the value that should be returned.
     * @Author Megan West
     */
    @Test
    public void testGetMatchingItemsByQuantityLessThan() {
        assertEquals("Fail, should be null",null, hardwareStore.getMatchingItemsByQuantity(1));
        System.out.println("Test Passed.");
    }

    /** testFindItem tests the index value of findItem when given a valid idNumber against the correct index value.
     * @author Megan West
     */
    @Test
    public void testFindItem(){

        assertEquals("fail, should be 0",0, hardwareStore.findItem("00000"));
        System.out.println("Test Passed.");
    }

    /** testFindItem tests the index value of findItem when given a nonexistent idNumber against the correct index value.
     * @author Megan West
     */
    @Test
    public void testFindItemNotFound(){
        assertEquals(-1, hardwareStore.findItem("12345"));
        System.out.println("Test Passed.");
    }

    /** testReadAndWrite() tests that the new item written to the database file is correctly written and read when
     * a new HardwareStore object is created.
     * @Author Megan West
     */
    @Test
    public void testReadAndWrite(){
        hardwareStore.addNewItem("12345", "2\" nails", "Fasteners", 75, .25f);
        try{
            hardwareStore.writeDatabase();
        }catch(IOException e){
            System.out.println("fail: There was an IOException while writing to file.");
        }

        try{
            HardwareStore tester = new HardwareStore();

            assertTrue("fail: should be True", hardwareStore.getItem(1).equals(tester.getItem(1)));
            System.out.println("Test Passed.");
        }catch(IOException e){
            System.out.println("fail: There was an IOException while reading from file.");
        }
    }
}
