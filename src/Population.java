import processing.core.PApplet;

import java.util.ArrayList;

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
    public void nextGen() {

    }
}
