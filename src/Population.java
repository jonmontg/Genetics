
import processing.core.PVector;

import java.util.*;

/**
 * A Population represents a group of Creatures with unique properties
 * such as mutation rate and brian size
 */
public class Population {

    private Sketch p;
    private ArrayList<Creature> creatures;
    private double mutationRate; //how large mutations are
    private int mutationFrequency; // how often mutations occur
    private double percent_kill;

    // size should be even (for now)
    public Population(Sketch p, int size, double mr, int mf, double pk, int[] brainSize) {
        this.p = p;
        this.creatures = new ArrayList<Creature>();
        this.mutationRate = mr;
        this.mutationFrequency = mf;
        this.percent_kill = pk;
        for (int i = 0; i < size; i++) {
            this.creatures.add(new Creature(p, brainSize));
        }
    }

    public void update() {
        for (Creature c: this.creatures) {
            c.update();
        }
    }

    public void draw() {
        for (Creature c: this.creatures) {
            c.draw();
        }
    }

    private void sortCreatures() {

        this.creatures.sort((c1, c2) -> {
            if (Math.abs(c1.fitness() - c2.fitness()) < .00001)
                return 0;
            if (c1.fitness() < c2.fitness())
                return -1;
            if (c1.fitness() == c2.fitness())
                return 0;
            if (c1.fitness() > c2.fitness())
                return 1;
            return 0;
        });
    }

    private double[] fitnessProbs() {
        double[] probs = new double[this.creatures.size()];
        double totFitness = 0;
        for (Creature c: this.creatures) {
            totFitness += c.fitness();
        }
        for (int i = 0; i < probs.length; i++) {
            probs[i] = 1 - this.creatures.get(i).fitness() / totFitness;
        }
        return probs;
    }

    private int rouletteSelect(double[] probs) {
        double pSum = 0;
        for (double prob: probs) {
            pSum += prob;
        }
        double val = new Random().nextDouble();
        for (int i = 0; i < probs.length; i++) {
            val -= probs[i];
            if (val < 0) {
                return i;
            }
        }
        return 0;
    }

    public void nextGen() {
        this.sortCreatures();
        ArrayList<Creature> newGen = new ArrayList<>();
        double[] fitProbs = this.fitnessProbs();
        for (int i = 0; i < this.creatures.size(); i++) {
            newGen.add(this.creatures.get(rouletteSelect(fitProbs)).copy());
        }
        for (Creature c: newGen) {
            c.mutate(this.mutationRate, this.mutationFrequency);
        }
        this.creatures = newGen;
    }

    public void setTarget(PVector target) {
        for (Creature c: this.creatures) {
            c.setTarget(target);
        }
    }
}
