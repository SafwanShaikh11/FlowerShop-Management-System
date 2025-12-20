import java.util.ArrayList;
/**This class is a representation of what a catalog in my Flower Shop would look like
 * it's using the Arraylist for better traversal if I use any Iterator
 * @Arrangement: this is the class that holds all the arrangements in the catalog
 * **/

public class Catalog {
    private ArrayList<Arrangement> arrangements;

    public Catalog() {
        arrangements = new ArrayList<>();
    }

    public void addArrangement(Arrangement a) {
        arrangements.add(a);
    }

    public ArrayList<Arrangement> getArrangements() {
        return arrangements;
    }
}
