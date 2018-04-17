import java.util.PriorityQueue;
import java.util.Comparator;

public class Population extends PriorityQueue<Individual>{

    public Population(Comparator<Individual> comp) {
        super(comp);
    }

    /**
     * TODO:
     * Returns the diversity factor of an individual
     */
    public double diversity(Individual i) {
        return 0;
    }

    public double standardDev(double mean) {

        return 0;
    }

}
