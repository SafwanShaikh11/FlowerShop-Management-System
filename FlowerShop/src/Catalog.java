import java.util.ArrayList;

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
