import Interfaces.Item;
/**
 * this class represents the actual Flower 
 * we'll be using 
 */
public class Flower implements Item {
    private String name;

    public Flower(String name) {
        this.name = name;
    }

    /**
     * Get name of flower.
     */
    public String getName() {
        return name;
    }
}
