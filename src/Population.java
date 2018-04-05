import java.util.PriorityQueue;
import java.util.Comparator;

public class Population extends PriorityQueue<Individual>{

    public Population(Comparator<Individual> comp) {
        super(comp);
    }

}
