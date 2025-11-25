import Interfaces.Item;

public class Vase implements Item {

    private String name;

    public Vase(String name) {
        this.name = name;
    }

    /**
     * Get name of vase.
     */
    public String getName() {
        return name;
    }
}
