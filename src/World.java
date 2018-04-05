/**
 * Author: Jonathan Montgomery
 * Date: April 5, 2018
 *
 * World holds a population and an environment. The population competes and
 * reproduces within the world depending on the pressures of the environment.
 */
public class World {
    private Population population;
    private Environment environment;

    public World(Population p, Environment e) {
        this.population = p;
        this.environment = e;
    }

    /**
     * TODO: Allows the population and environment to progress.
     *
     */
    public void advance() {

    }

    /**
     * TODO: Calculate the fitness of an organism.
     * Returns the fitness of an organism based on how well it performs
     * the desired task.
     */
    public int fitness(Individual i) {
        return 0;
    }
}
