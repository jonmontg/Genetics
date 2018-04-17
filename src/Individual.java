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

    private String crossover(Individual p, Individual q) {
        return null;
    }

}

