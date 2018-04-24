
import processing.core.PVector;

import java.rmi.activation.ActivationGroup_Stub;
import java.util.*;

/**
 * A Population represents a group of Creatures with unique properties
 * such as mutation rate and brian size
 */
public class Population {

    private Sketch p;
    private ArrayList<Creature> creatures; // maps creatures to getFitness
    private double mutationRate; //how large mutations are
    private int mutationFrequency; // how often mutations occur
    private double fitness;

    // size should be even (for now)
    public Population(Sketch p, int size, double mr, int mf, int[] brainSize, PVector col) {
        this.p = p;
        this.creatures = new ArrayList<Creature>();
        this.mutationRate = mr;
        this.mutationFrequency = mf;
        this.fitness = 0;
        for (int i = 0; i < size; i++) {
            this.creatures.add(new Creature(p, brainSize, col));
        }
    }

    public void update() {
        for (Creature c: this.creatures) {
            c.update();
            this.p.s.updateFitness(c);
        }
    }

    public void draw() {
        for (Creature c: this.creatures) {
            c.draw();
        }
    }

    private void sortCreatures() { // sorts in descending order
        this.creatures.sort((c1, c2) -> {
            if (Math.abs(c1.getFitness() - c2.getFitness()) < .00001)
                return 0;
            if (c1.getFitness() < c2.getFitness())
                return -1;
            if (c1.getFitness() == c2.getFitness())
                return 0;
            if (c1.getFitness() > c2.getFitness())
                return 1;
            return 0;
        });
    }

    private Creature rouletteSelect(double[] probs) {
        double probSum = 0;
        for (double prob: probs) {
            probSum += prob;
        }
        double r = new Random().nextDouble() * probSum;
        for (int i = 0; i < probs.length; i++) {
            r -= probs[i];
            if (r < 0) {
                //System.out.println("I: " + i);
                return this.creatures.get(i).copy();
            }
        }
        return this.creatures.get(0).copy();
    }

    private Creature tournamentSelect(int size) {
        if (size > this.creatures.size()) {
            return this.creatures.get(0).copy();
        }
        int best = -1;
        for (int i = 0; i < size; i++) {
            int rInd = new Random().nextInt(this.creatures.size());
            if (best == -1 || this.creatures.get(rInd).getFitness() < this.creatures.get(best).getFitness()) {
                best = rInd;
            }
        }
        //System.out.println("best " + best);
        return this.creatures.get(best).copy();
    }

    public void nextGen() {
        this.sortCreatures();
        this.fitness = this.creatures.get(0).getFitness(); // temporarily using best getFitness

        ArrayList<Creature> newGen = new ArrayList<>();
        for (int c = 0; c < this.creatures.size(); c++) {
            newGen.add(tournamentSelect(30));
        }
        for (Creature c: newGen) {
            c.mutate(this.mutationRate, this.mutationFrequency);
        }
        this.creatures = newGen;
    }
}
