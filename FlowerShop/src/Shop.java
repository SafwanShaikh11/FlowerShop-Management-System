import BasicIO.ASCIIDisplayer;
import Exceptions.NullOrderException;

import java.util.*;

public class Shop {
    private final ArrayList<Order> backlog;
    private final ArrayList<Order> rushBacklog;
    private final Catalog catalog;
    private final ASCIIDisplayer displayer;

    public Shop() {
        backlog = new ArrayList<>();
        rushBacklog = new ArrayList<>();
        this.catalog = new Catalog();
        displayer = new ASCIIDisplayer();
    }

    /**
     * Removes an order by id from the backlogs. Returns null if no order
     * is found with the id in the backlogs.
     *
     * @param id identifier of the order to be removed
     * @return order corresponding to id
     */
    public Order cancelOrder(int id) {
        for(int i=0; i < backlog.size(); i++) {
            if(backlog.get(i).getId() == id) {
                Order o = backlog.remove(i);
                displayer.writeString("Cancelled order ID " + o.getId() + "\n\n");
                return o;
            }
        }
/*
        for(int i=0; i < rushBacklog.size(); i++) {
            if(rushBacklog.get(i).getId() == id) {
                Order o = rushBacklog.remove(i);
                displayer.writeString("Cancelled order ID" + o.getId() + "\n\n");
                return o;
            }
        }*/

        displayer.writeString("Could not find order with ID " + id + "\n\n");
        return null;
    }

    /**
     * Makes an order and adds it to either rushBacklog or backlog. Sorts afterwards to maintain priority.
     * @param arrangements arrangements in order
     * @param rush if order is rush or not
     * @param customerName name of customer making order
     */
    public void makeOrder(ArrayList<Arrangement> arrangements, boolean rush, String customerName) {
        if(rush) {
            rushBacklog.add(new Order(arrangements, rush, customerName));
            Collections.sort(rushBacklog);
            Collections.reverse(rushBacklog);
        } else {
            backlog.add(new Order(arrangements, rush, customerName));
            Collections.sort(backlog);
            Collections.reverse(backlog);
        }
    }

    /**
     * Adds an arrangement to the catalog.
     * @param a arrangement to be added
     */
    public void addArrangement(Arrangement a) {
        catalog.addArrangement(a);
    }

    /**
     * Prints out all the arrangements in the catalog to displayer.
     */
    public void browseCatalog() {
        int i=0;
        for(Arrangement a : catalog.getArrangements()) {
            displayer.writeString("Arrangement " + i++ + "\n");
            printArrangement(a);
        }
    }

    /**
     * Prints an arrangement out to the displayer.
     * @param a arrangement to be printed
     */
    private void printArrangement(Arrangement a) {
        ArrayList<String> flowerNames = a.getFlowerNames();
        for (String name : flowerNames) {
            displayer.writeString(name + "\n");
        }

        displayer.writeString(a.getVaseName() + "\n");
        displayer.writeString("Price: $" + String.format("%.2f", a.getPrice()) + "\n");
        displayer.newLine();
    }

    /**
     * Displays the current order to the displayer. If there are no orders, then prints out
     * "There are no orders."
     */
    public void currentOrder() {
        displayer.writeString("Current Order:\n");
        if(rushBacklog.size() > 0) {
            displayOrder(rushBacklog.get(0));
        } else if (backlog.size() > 0) {
            displayOrder(backlog.get(0));
        } else {
            displayer.writeString("There are no orders.");
            displayer.newLine();
        }
    }

    /**
     * Displays an order to the displayer.
     *
     * @param o order to be displayed
     * @throws NullOrderException null order given as argument
     */
    public void displayOrder(Order o) throws NullOrderException {
        if (o != null) {
            displayer.writeString("Order Information\n");
            displayer.writeString("Order ID: " + o.getId() + "\n");
            displayer.writeString("Customer Name: " + o.getCustomerName() + "\n");
            displayer.writeString("Is Rush: " + o.isRush() + "\n");
            displayer.writeString("Order Price: " + o.getPrice() + "\n");
            displayer.newLine();
            displayer.writeString("Order Arrangements\n");
            HashMap<String, Integer> flowerCounts = new HashMap<>();
            addFlowerCounts(o, flowerCounts);

            for(String key : flowerCounts.keySet()) {
                displayer.writeString(key + ": " + flowerCounts.get(key) + "\n");
            }
        } else {
            throw new NullOrderException();
        }

        displayer.newLine();
    }

    /**
     * Lists a count of all outstanding flowers to displayer, along with the number of orders
     * that need each outstanding flower.
     *
     * (hint: need to use addFlowerCounts() and addUniqueFlowerCount())
     */
    public void listOutstandingFlowers() {
        displayer.writeString("Outstanding Flowers\n");
        HashMap<String, Integer> flowerCounts = new HashMap<>();
        for(Order o : backlog) {
            addFlowerCounts(o, flowerCounts);
        }

        for(Order o : rushBacklog) {
            addFlowerCounts(o, flowerCounts);
        }

        HashMap<String, Integer> orderFlowerCount = new HashMap<>();
        for(Order o : backlog) {
            addUniqueFlowerCounts(o, orderFlowerCount);
        }

        for(Order o : rushBacklog) {
            addUniqueFlowerCounts(o, orderFlowerCount);
        }

        for(String key: flowerCounts.keySet()) {
            displayer.writeString(key + ": " + flowerCounts.get(key) + "\n");
        }

        displayer.writeString("Orders that contain each flower:");
        displayer.newLine();
        for(String key: orderFlowerCount.keySet()) {
            displayer.writeString(key + ": " + orderFlowerCount.get(key) + "\n");
        }
        displayer.newLine();
    }

    /**
     * Adding unique flower counts to flowerCount. Hint: Useful for counting how many orders
     * have each type of flower.
     */
    private void addUniqueFlowerCounts(Order o, HashMap<String, Integer> flowerCount) {
        /*
            NOTE: HASHMAPS MAY NOT BE A TOPIC COVERED IN COSC 1P03 AND
            THUS MAY NOT BE REQUIRED TO KNOW FOR EXAM/ASSIGNMENT. ADDED
            HERE BECAUSE MAKES IMPLEMENTATION MUCH EASIER.

            If you have questions about hashmaps let me know.
         */
        for(String flowerName : o.uniqueFlowers()) {
            if(flowerCount.containsKey(flowerName)) {
                flowerCount.put(flowerName, flowerCount.get(flowerName) + 1);
            } else {
                flowerCount.put(flowerName, 1);
            }
        }
    }

    /**
     * Adding all flower counts to flowerCount. Hint: Useful for counting the number of outstanding flowers
     * for each type of flower.
     */
    private void addFlowerCounts(Order o, HashMap<String, Integer> flowerCount) {
        /*
            NOTE: HASHMAPS MAY NOT BE A TOPIC COVERED IN COSC 1P03 AND
            THUS MAY NOT BE REQUIRED TO KNOW FOR EXAM/ASSIGNMENT. ADDED
            HERE BECAUSE MAKES IMPLEMENTATION MUCH EASIER.

            If you have questions about hashmaps let me know.
         */

        for(String flowerName: o.getFlowerNames()) {
            if(flowerCount.containsKey(flowerName)) {
                flowerCount.put(flowerName, flowerCount.get(flowerName) + 1);
            } else {
                flowerCount.put(flowerName, 1);
            }
        }
    }

    /**
     * Removes the order with the highest priority, or null if there are no orders.
     *
     * @return current order
     */
    public Order dispenseOrder() {
        Order o = null;
        if(rushBacklog.size() > 0) {
            o = rushBacklog.remove(0);
        } else if (backlog.size() > 0) {
            o = backlog.remove(0);
        }

        if(o != null) {
            displayer.writeString("Order ID " + o.getId() + " has been dispensed.\n");
        } else {
            displayer.writeString("Backlog is empty, cannot dispense an order.\n");
        }
        displayer.newLine();
        return o;
    }

    /**
     * Closes the displayer once we're done with the program.
     */
    public void close() {
        displayer.close();
    }

    public static void main(String[] args) {
        // Testing implementation
        Shop s = new Shop();

        Flower rose = new Flower("Rose");
        Flower daisy = new Flower("Daisy");
        Flower sunflower = new Flower("Sunflower");

        // Making arrangements
        Flower[] flowers = {rose, rose, daisy, rose, rose, rose, rose, rose};
        Flower[] flowers2 = {daisy, daisy, sunflower, sunflower, daisy, daisy, rose, rose};
        Vase vase = new Vase("basic");
        Arrangement a = new Arrangement(flowers, vase, 20.99);
        Arrangement a2 = new Arrangement(flowers2, vase, 25.99);

        // Add arrangements to catalog
        s.addArrangement(a);
        s.addArrangement(a2);

        // Browse catalog
        s.browseCatalog();

        ArrayList<Arrangement> orderA = new ArrayList<>();
        orderA.add(a);

        ArrayList<Arrangement> orderB = new ArrayList<>();
        orderB.add(a);
        orderB.add(a2);

        // Make 3 orders
        s.makeOrder(orderA, true, "Nick");
        s.makeOrder(orderB, false, "Wow");
        s.makeOrder(orderB, true, "Okay");

        // List outstanding flowers
        s.listOutstandingFlowers();

        // Cancel order with id 1
        s.cancelOrder(1);

        // Cancel order with id 50000 (shouldn't exist)
        Order o = s.cancelOrder(50000);
        if(o != null) {
            s.displayer.writeString("Error: Tried to cancel order with ID 50000 and didn't receive null!");
        }

        // Show current order
        s.currentOrder();

        // Remove 2 orders by priority from backlog, and display them
        s.displayOrder(s.dispenseOrder());
        s.displayOrder(s.dispenseOrder());

        // try to display a null order, and should get NullOrderException
        try {
            s.displayOrder(s.dispenseOrder());
            s.displayer.writeString("Didn't throw a NullOrderException for displaying null order");
        } catch(NullOrderException e) {
            s.displayer.writeString("Successfully caught the NullOrderException!");
        }

        s.displayer.writeString("Testing of program ended.");
        s.close();
    }
}
