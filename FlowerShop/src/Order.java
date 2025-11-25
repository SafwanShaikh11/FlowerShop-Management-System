import java.util.ArrayList;

public class Order implements Comparable<Order> {
    private ArrayList<Arrangement> arrangements;
    private String customerName;
    private static int orderInstances = 0;
    private int id;
    private boolean rush;

    public Order(ArrayList<Arrangement> arrangements, boolean rush, String customerName) {
        this.arrangements = arrangements;
        this.customerName = customerName;
        id = orderInstances;
        orderInstances++;
        this.rush = rush;
    }

    /**
     * Get whether order is a rush order or not.
     */
    public boolean isRush() {
        return rush;
    }

    /**
     * Get customer name attached to order.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Get price of order, which is sum of prices of arrangements.
     */
    public double getPrice() {
        double price = 0;
        for(Arrangement a: arrangements) {
            price += a.getPrice();
        }
        return price;
    }

    /**
     * Get id of order.
     */
    public int getId() {
        return id;
    }

    /**
     * Get all arrangements attached to order.
     */
    public ArrayList<Arrangement> getArrangements() {
        return arrangements;
    }

    /**
     * Get a list of all unique flower names in the order.
     */
    public ArrayList<String> uniqueFlowers() {
        ArrayList<String> flowers = new ArrayList<>();

        for(Arrangement a : getArrangements()) {
            for(String name : a.getFlowerNames()) {
                if(!flowers.contains(name)) {
                    flowers.add(name);
                }
            }
        }

        return flowers;
    }

    /**
     * Get a list of all flower names in the order.
     */
    public ArrayList<String> getFlowerNames() {
        ArrayList<String> flowerNames = new ArrayList<>();
        for(Arrangement a : getArrangements()) {
            flowerNames.addAll(a.getFlowerNames());
        }
        return flowerNames;
    }

    /*
    Used for priority queue as backlog. Changed the implementation of this.
    @Override
    public int compareTo(Order other) {
        if(isRush() && !other.isRush()) {
            return -1;
        } else if (!isRush() && other.isRush()) {
            return 1;
        } else if (getPrice() > other.getPrice()) {
            return -1;
        } else if (getPrice() < other.getPrice()) {
            return 1;
        } else {
            return 0;
        }
    }*/

    @Override
    public int compareTo(Order other) {
        // Useful for sorting ArrayList by price
        return Double.compare(getPrice(), other.getPrice());
    }
}
