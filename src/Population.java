import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.LinkedList;

public class Population {
    private Box2DProcessing box2d;
    private ArrayList<Creature> creatures;
    private double mutationRate;
    private int mutationFreq;
    private int size;
    private float startx, starty;
    private float[] dims;
    private int[] brainsize;
    private Vec2 goal;
    private int[] poppool;
    private double crossoverProb;
    private Creature bestEver;

    /**
     * Initializes a population of creatures for the genetic algorithm
     * @param w The Box2D world the creatures exist in
     * @param startx Creature starting x position
     * @param starty Creature starting y position
     * @param dims Array of creature dimensions
     * @param brainsize Array of neural network dimension sizes
     * @param goal Creature goal location. Used for fitness evaluation
     * @param size Number of creatures in the population
     * @param crossoverProb Probability of genetic crossover in reproduction
     * @param mutationRate Coefficient of the gaussian mutation
     * @param mutationFreq Number of mutations on the child after reproduction
     */
    public Population(Box2DProcessing w, float startx, float starty, float[] dims,
                      int[] brainsize, Vec2 goal, int size, double crossoverProb, double mutationRate, int mutationFreq) {
        this.box2d = w;
        this.size = size;
        this.startx = startx;
        this.starty = starty;
        this.dims = dims;
        this.brainsize = brainsize;
        this.goal = goal;
        this.crossoverProb = crossoverProb;
        this.mutationFreq = mutationFreq;
        this.mutationRate = mutationRate;
        creatures = new ArrayList<>(size);
        for (int i=0; i<size; i++)
            creatures.add(i, new Creature(startx, starty, dims, brainsize, box2d));
        for (Creature creature : creatures) {
            creature.setGoal(goal.x, goal.y);
            LinkedList<Float> speeds = new LinkedList<>();
            for (int i=0; i<creature.joints.size(); i++)
                speeds.add((float)(java.util.concurrent.ThreadLocalRandom.current().nextFloat()*Math.PI-(2*Math.PI)));
            creature.setJointSpeeds(speeds);
        }
        bestEver = creatures.get(0);

        poppool = new int[(int)(.5*size*(size+1))];
        int processed = 0;
        for (int i=0; i<size; i++) {
            for (int j=0; j<=i; j++)
                poppool[processed+j] = i;
            processed+=i+1;
        }
    }

    /**
     * Updates all creatures in the population
     */
    public void update() {
        for (Creature c : creatures) {
            c.update();
        }
    }

    /**
     * Replaces previous generation with new generation. Each creature is assigned a reproduction probability
     * based on its fitness evaluation.
     * @return Creature - The creature with the highest fitness
     */
    public Creature reproduce() {
        creatures.sort((x, y) -> (x.closestDistance > y.closestDistance) ? 1 : -1);
        Creature best = creatures.get(0);

        if (best.closestDistance < bestEver.closestDistance) // update bestEver if applicable
            bestEver = best;

        for (int i=0; i<size; i++) {
            Creature father = creatures.get(i);
            Creature mother = creatures.get(choosePartner());
            Creature child = new Creature(startx, starty, dims, brainsize, box2d);
            child.brain = NeuralNetwork.mate(father.getBrain(), mother.getBrain(), crossoverProb, mutationRate, mutationFreq);
            creatures.set(i, child);
        }
        return best;
    }

    /**
     * Chooses a member of the population for reproduction
     * @return int - the population index of the creature
     */
    public int choosePartner() {
        return size - 1 - poppool[(int)(Math.random()*poppool.length)];

    }

    /**
     * Returns the Creature with the highest historical fitness
     * @return Creature
     */
    public Creature getBest() {
        return bestEver;
    }

    /**
     * Returns the population as a LinkedList of creatures
     * @return LinkedList
     */
    public LinkedList<Creature> getCreatures() {
        return new LinkedList<>(creatures);
    }
}
