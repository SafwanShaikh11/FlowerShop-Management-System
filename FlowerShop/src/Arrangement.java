import Interfaces.Priceable;

import java.util.ArrayList;

public class Arrangement implements Priceable {
    private Flower[] flowers;
    private Vase vase;
    private double price;

    public Arrangement(Flower[] flowers, Vase vase, double price) {
        this.flowers = flowers;
        this.vase = vase;
        this.price = price;
    }

    /**
     * Get price of arrangement.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get all flower names in arrangement.
     */
    public ArrayList<String> getFlowerNames() {
        ArrayList<String> names = new ArrayList<>();
        for(Flower f : flowers) {
            names.add(f.getName());
        }
        return names;
    }

    /**
     * Get name of vase in arrangement.
     */
    public String getVaseName() {
        return vase.getName();
    }
}
