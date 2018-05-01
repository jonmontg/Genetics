import processing.core.PVector;
import java.util.*;


public class Population {

    private Sketch p;
    private ArrayList<Creature> creatures;
    private double mutationRate;
    private int tournSize = 10;

    // size should be even (for now)
    public Population(Sketch p, int size, double mr, int[] brainSize, PVector col) {
        this.p = p;
        this.creatures = new ArrayList<Creature>();
        this.mutationRate = mr;
        for (int i = 0; i < size; i++) {
            this.creatures.add(new Creature(this.p, this.mutationRate, brainSize, new PVector(this.p.width / 2, 50), col));
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

    public ArrayList<Creature> getCreatures() {
        return creatures;
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
        return this.creatures.get(best).copy();
    }

    public void nextGen() {
        this.sortCreatures();

        ArrayList<Creature> newGen = new ArrayList<>();
        for (int c = 0; c < this.creatures.size(); c++) {
            newGen.add(tournamentSelect(this.tournSize));
        }
        for (Creature c: newGen) {
            c.mutate(this.mutationRate);
        }
        this.creatures = newGen;
    }
}
