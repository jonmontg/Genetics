import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class Individual {

    private String chromosome;
    int x;
    int y;

    public Individual(Individual p, Individual q) {
        this.chromosome = crossover(p, q);
    }

    public Individual(String chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * TODO: create a crossing over algorithm that combines parent chromosomes into a child
     */
    public String crossover(Individual p, Individual q) {
        return null;
    }

    /**
     *
     */
    public void mutate() {

    }
}
