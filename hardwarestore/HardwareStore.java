package hardwarestore;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to represent a database interface for a list of
 * <CODE>item</CODE>'s. It using a plain-text file "database.txt" to store and
 * write item objects in readable text form. It contains an
 * <CODE>ArrayList</CODE> called <CODE>itemList</CODE> to store the database in
 * a runtime friendly data structure. The <CODE>itemList</CODE> is written to
 * "database.txt" at the end of the <CODE>hardwarestore.HardwareStore</CODE> object's life by
 * calling <CODE>writeDatabase()</CODE>. This class also provides methods for
 * adding, removing, and searching for items in the list.
 *
 * @author Junye Wen
 */
public class HardwareStore {

    private ArrayList<Item> itemList;
    
    private static final String DATA_FILE_NAME = "database.txt";

    /**
     * This constructor creates an empty ArrayList and then calls the 
     * <CODE>readDatabase()</CODE> method to populate items previously stored. 
     *
     * @throws IOException
     */
    public HardwareStore() throws IOException {
        itemList = new ArrayList<>();
        readDatabase();
    }

    /**
     * Method getAllItemsFormatted returns the current list of items in the Arraylist in
     * no particular order.
     * 
     * @return a formatted String representation of all the items in itemList.
     */
    public String getAllItemsFormatted() {
        return getFormattedItemList(itemList);
    }

    /**
     * Private method getFormattedPackageList used as an auxiliary method to return a given ArrayList
     * of items in a formatted manner.
     *
     * @param items the item list to be displayed.
     * @return a formatted String representation of all the items in the list give as a parameter.
     */
    private String getFormattedItemList(ArrayList<Item> items) {

        String text = " ------------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n", "ID Number", "Name", "Category", "Quantity", "Price") +
                      " ------------------------------------------------------------------------------------\n";

        for (int i = 0; i < items.size(); i++) {
            text += String.format("| %-10s| %-25s| %-20s| %-10s| %-10s|%n",
                    items.get(i).getIdNumber(),
                    items.get(i).getName(),
                    items.get(i).getCategory(),
                    Integer.toString(items.get(i).getQuantity()),
                    String.format("%.2f", items.get(i).getPrice()));
        }
        text += " ------------------------------------------------------------------------------------\n";

        return text;
    }


    /**
     * This method is used to add a item to the itemList ArrayList.
     *
     * @param idNumber a <CODE>String</CODE> representing the ID number of item
     * @param name a <CODE>String</CODE> representing the name of item
     * @param category a <CODE>String</CODE> representing the category of item
     * @param quantity an <CODE>int</CODE> representing the quantiy of item
     * @param price a <CODE>float</CODE> representing the price of item
     */
    public void addNewItem(String idNumber, String name, String category, int quantity, float price) {
        //If passed all the checks, add the item to the list
        itemList.add(new Item(idNumber, name, category, quantity, price));
        System.out.println("hardwarestore.Item has been added.\n");
    }
    
    
    /**
     * Add a certain quantity of the given item index.
     * Preconditions: 1. hardwarestore.Item exists.
     * @param itemIndex the index of the item in the itemList
     * @param quantity  the quantity to remove
     */
    public void addQuantity(int itemIndex, int quantity) {
        Item temp = getItem(itemIndex);
        temp.setQuantity(temp.getQuantity() + quantity);
        System.out.println("Quantity updated.\n");
    }

    
    /**
     * Removes a certain quantity of the given item index. 
     * Preconditions: 1. hardwarestore.Item exists. 2. Quantity to remove smaller than current quantity.
     * @param itemIndex the index of the item in the itemList
     * @param quantity  the quantity to remove
     */
    public void removeQuantity(int itemIndex, int quantity) {
        Item temp = getItem(itemIndex);
        temp.setQuantity(temp.getQuantity() - quantity);
        System.out.println("Quantity updated.\n");
    }

    /**
     * Returns all the items that (partially) match the given name.
     * @param name the name to match.
     * @return a string containing a table of the matching items.
     */
    public String getMatchingItemsByName(String name) {
        ArrayList<Item> temp = new ArrayList<Item>();
        for (Item tempItem : itemList) {
            if (tempItem.getName().toLowerCase().contains(name.toLowerCase())) {
                temp.add(tempItem);
            }
        }
        
        if (temp.size() == 0) {
            return null;
        } else {
            return getFormattedItemList(temp);
        }
    }

    /**
     * Returns all the items with current quantity lower than (or equal) the
     * given threshold.
     * @param quantity the quantity threshold.
     * @return a string containing a table of the matching items.
     */
    public String getMatchingItemsByQuantity(int quantity) {
        ArrayList<Item> temp = new ArrayList<Item>();
        for (Item tempItem : itemList) {
            if (tempItem.getQuantity() <= quantity) {
                temp.add(tempItem);
            }
        }
        
        if (temp.isEmpty()) {
            return null;
        } else {
            return getFormattedItemList(temp);
        }
    }

    /**
     * This method can be used to find a item in the Arraylist of items.
     *
     * @param idNumber a <CODE>String</CODE> that represents the ID number of
     * the item that to be searched for.
     * @return the <CODE>int</CODE> index of the items in the Arraylist of
     * items, or -1 if the search failed.
     */
    public int findItem(String idNumber) {

        int index = -1;

        for (int i = 0; i < itemList.size(); i++) {
            String temp = itemList.get(i).getIdNumber();

            if (idNumber.equalsIgnoreCase(temp)) {
                index = i;
                break;
            }

        }

        return index;
    }

    /**
     * This method is used to retrieve the hardwarestore.Item object from the
     * <CODE>itemList</CODE> at a given index.
     *
     * @param i the index of the desired <CODE>hardwarestore.Item</CODE> object.
     * @return the <CODE>hardwarestore.Item</CODE> object at the index or null if the index is
     * invalid.
     */
    public Item getItem(int i) {
        if (i < itemList.size() && i >= 0) {
            return itemList.get(i);
        } else {
            System.out.println("Invalid Index.\n");
            return null;
        }
    }

    /**
     * This method opens the database file and overwrites it with a
     * text representation of all the items in the <CODE>itemList</CODE>. This
     * should be the last method to be called before exiting the program.
     *
     * @throws IOException
     */
    public void writeDatabase() throws IOException {
        PrintWriter pw = new PrintWriter(DATA_FILE_NAME);

        for (Item c : itemList) {
            pw.print(c.toString());
        }

        pw.close();
    }

    /**
     * The method opens the database file and initializes the <CODE>itemList</CODE> 
     * with its contents. If no such file exists, then one is created. 
     * The contents of the file are "loaded" into the itemList ArrayList in no 
     * particular order. The file is then closed during the duration of the 
     * program until <CODE>writeDatabase()</CODE> is called.
     *
     * @throws IOException
     */
    public void readDatabase() throws IOException {

        File dataFile = new File(DATA_FILE_NAME);

        // If data file does not exist, create it.
        if (!dataFile.exists()) {
            System.out.println("database.txt does not exist, creating one now . . .");
            //if the file doesn't exists, create it
            PrintWriter pw = new PrintWriter(DATA_FILE_NAME);
            //close newly created file so we can reopen it
            pw.close();

            return; // No need to try to read anything from an empty file, so return.
        }

        Scanner itemScanner = new Scanner(new FileReader(dataFile));

        //Initialize the Array List with items from database.txt
        while (itemScanner.hasNextLine()) {

            // split values using the space character as separator
            String[] temp = itemScanner.nextLine().split("~");

            itemList.add(new Item(temp[0], temp[1], temp[2],
                    Integer.parseInt(temp[3]), Float.parseFloat(temp[4])));
        }

        //item list is now in the ArrayList completely so we can close the file
        itemScanner.close();
    }

}
