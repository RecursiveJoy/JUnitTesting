package hardwarestore;

import java.io.IOException;
import java.util.Scanner;

/**
 * This is the main class of the Hardware Store database manager. It provides a
 * console for a user to use the 5 main commands.
 *
 * @author Junye Wen
 */
public class MainApp {

    // This object will allow us to interact with the methods of the class hardwarestore.HardwareStore
    private final HardwareStore hardwareStore;
    private static final Scanner CONSOLE_INPUT = new Scanner(System.in); // Used to read from System's standard input

    /**
     * Default constructor. Initializes a new object of type hardwarestore.HardwareStore
     *
     * @throws IOException
     */
    public MainApp() throws IOException {
        hardwareStore = new HardwareStore();
    }

    /**
     * Shows all items in the inventory.
     */
    public void showAllItems() {
        System.out.print(hardwareStore.getAllItemsFormatted());
    }

    /**
     * This method will add items quantity with given number. If the item does
     * not exist, it will call another method to add it.
     *
     */
    public void addItemQuantity() {

        System.out.println("Please input the ID of item");
        String idNumber = CONSOLE_INPUT.nextLine();

        if (!idNumber.matches("[A-Za-z0-9]{5}")) {
            System.out.println("Invalid ID Number: not proper format. "
                    + "ID Number must be 5 alphanumeric characters.\n");
            return;
        }

        int itemIndex = hardwareStore.findItem(idNumber);
        if (itemIndex != -1) { // If item exists in the database

            System.out.println("hardwarestore.Item found in database. Please enter quantity to add.");
            int quantity = CONSOLE_INPUT.nextInt();
            if (quantity <= 0) {
                System.out.println("Invalid quantity. "
                        + "The addition amount must be larger than 0.\n");
                return;
            }
            hardwareStore.addQuantity(itemIndex, quantity);
        } else {
            //If it reaches here, the item does not exist. We need to add new one.
            System.out.println("hardwarestore.Item with given number does not exist.\n");

            // Enter name
            System.out.println("\nPlease type the name of item.");
            String name = CONSOLE_INPUT.nextLine();

            // Entery category
            String category = null;
            System.out.println("\nPlease select the category of item.");
            System.out.println("1: Door&Window\n2: Cabinet&Furniture\n3: Fasteners\n4: Structural\n5: Other");
            int selection = CONSOLE_INPUT.nextInt();
            switch (selection) {
                case 1:
                    category = "Door&Window";
                    break;
                case 2:
                    category = "Cabinet&Furniture";
                    break;
                case 3:
                    category = "Fasteners";
                    break;
                case 4:
                    category = "Structural";
                    break;
                case 5:
                    category = "Other";
                    break;
                default:
                    System.out.println("Invalid category number.");
                    return;
            }

            // Entery quantity
            System.out.println("\nPlease type the quantity of the item.");
            int quantity = CONSOLE_INPUT.nextInt();
            if (quantity < 0) {
                System.out.println("Invalid price. "
                        + "The quantity cannot be smaller than 0.");
                return;
            }

            // Enter price
            System.out.println("\nPlease type the price of the item.");
            float price = CONSOLE_INPUT.nextFloat();
            if (price < 0) {
                System.out.println("Invalid price. "
                        + "The price cannot be smaller than 0.");
                return;
            }

            hardwareStore.addNewItem(idNumber, name, category, quantity, price);
        }

    }

    /**
     * This method will remove the given quantity of an item with given number.
     * If the item does not exist, it will show an appropriate message.
     */
    public void removeItemQuantity() {

        System.out.println("Please input the ID of item");
        String idNumber = CONSOLE_INPUT.nextLine();
        if (!idNumber.matches("[A-Za-z0-9]{5}")) {
            System.out.println("Invalid ID Number: not proper format. "
                    + "ID Number must be at least 5 alphanumeric characters.");
            return;
        }

        int itemIndex = hardwareStore.findItem(idNumber);
        int currentQuantity;
        if (itemIndex == -1) {
            System.out.println("hardwarestore.Item does not exist.\n");
            return;
        } else {
            currentQuantity = hardwareStore.getItem(itemIndex).getQuantity();
            System.out.println("Current quantity: " + currentQuantity + "\n");
        }

        System.out.println("Please input the quantity to remove.");
        int quantity = CONSOLE_INPUT.nextInt();
        if (quantity > currentQuantity) {
            System.out.println("Invalid quantity. "
                    + "The removal amount must be smaller than current quantity.\n");
        } else {
            hardwareStore.removeQuantity(itemIndex, quantity);
        }
    }

    /**
     * This method can search item by a given name (part of name.
     * Case-insensitive.) Will display all items with the given name.
     */
    public void searchItemByName() {

        System.out.println("Please input the name of item.\n");
        String name = CONSOLE_INPUT.nextLine();

        String output = hardwareStore.getMatchingItemsByName(name);
        if (output == null) {
            System.out.println("hardwarestore.Item not found.");
        } else {
            System.out.println(output);
        }
    }

    /**
     * This method can search item below a certain quantity. Will display all
     * items fits such condition.
     */
    public void searchItemByQuantity() {

        System.out.println("Please enter the quantity:\n");
        int quantity = CONSOLE_INPUT.nextInt();

        if (quantity < 0) {
            System.out.println("Quantity should be at least 0.\n");
        }

        String output = hardwareStore.getMatchingItemsByQuantity(quantity);
        if (output == null) {
            System.out.println("No items found below given quantity.");
        } else {
            System.out.println(output);
        }
    }

    public void saveDatabase() throws IOException {
        hardwareStore.writeDatabase();
    }

    /**
     * This method will begin the user interface console. Main uses a loop to
     * continue executing commands until the user types '6'.
     *
     * @param args this program expects no command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        MainApp app = new MainApp();

        String welcomeMessage = "\nWelcome to the Hardware Store database. Choose one of the following functions:\n\n"
                + "\t1. Show all existing items in stock and their quantities.\n"
                + "\t2. Add a new quantity of a specific item to the stock.\n"
                + "\t3. Remove a certain quantity of a specific item type.\n"
                + "\t4. Search for an item (given its name or part of its name).\n"
                + "\t5. Show a list of all items below a certain quantity.\n"
                + "\t6. Exit program.\n";

        System.out.println(welcomeMessage);

        int selection = CONSOLE_INPUT.next().charAt(0);
        CONSOLE_INPUT.nextLine();

        while (selection != '6') {

            switch (selection) {
                case '1':
                    app.showAllItems();
                    break;
                case '2':
                    app.addItemQuantity();
                    break;
                case '3':
                    app.removeItemQuantity();
                    break;
                case '4':
                    app.searchItemByName();
                    break;
                case '5':
                    app.searchItemByQuantity();
                    break;
                case 'h':
                    System.out.println(welcomeMessage);
                    break;
                default:
                    System.out.println("That is not a recognized command. Please enter another command or 'h' to list the commands.");
                    break;

            }

            System.out.println("Please enter another command or 'h' to list the commands.\n");
            selection = CONSOLE_INPUT.next().charAt(0);

            CONSOLE_INPUT.nextLine();
        }

        CONSOLE_INPUT.close();
        
        
        System.out.print("Saving database...");
        app.saveDatabase();

        System.out.println("Done!");

    }
}
