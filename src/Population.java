import processing.core.PApplet;

import java.util.*;

/**
 * A Population represents a group of Creatures with unique properties
 * such as mutation rate and brian size
 */
public class Population extends PApplet {

    Sketch p;
    ArrayList<Creature> creatures;
    int popSize;
    double mutationRate; //how large mutations are
    int mutationFrequency; // how often mutations occur

    // size should be even (for now)
    public Population(Sketch p, int size, double mr, int mf, int[] brainSize) {
        this.p = p;
        this.popSize = size;
        this.creatures = new ArrayList<Creature>();
        this.mutationRate = mr;
        this.mutationFrequency = mf;
        for (int i = 0; i < this.popSize; i++) {
            this.creatures.add(new Creature(p, brainSize));
        }
    }

    public void update() {
        for (int i = 0; i < this.creatures.size(); i++) {
            this.creatures.get(i).update();
        }
    }

    public void draw() {
        for (int i = 0; i < this.creatures.size(); i++) {
            this.creatures.get(i).draw();
        }
    }

    private void sortCreatures() {
        Collections.sort(this.creatures, (c1, c2) -> {
            if (c1.fitness() > c2.fitness()) {
                return -1;
            } else if (c1.fitness() == c2.fitness()) {
                return 0;
            }
            return 1;
        });
    }

    public void nextGen() {

        this.sortCreatures();

        for (int i = 0; i < this.creatures.size(); i++) {
            System.out.println(this.creatures.get(i).fitness());
        }
        ArrayList<Creature> newGen = new ArrayList<Creature>();
        for (int i = 0; i < this.creatures.size() / 2; i++) {
            Creature c1 = this.creatures.get(i).copy();
            Creature c2 = this.creatures.get(i).copy();
            c1.mutate(this.mutationRate, this.mutationFrequency);
            c2.mutate(this.mutationRate, this.mutationFrequency);
            newGen.add(c1);
            newGen.add(c2);
        }
        this.creatures = newGen;
    }
}
