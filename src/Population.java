import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

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
    private float bestEver;

    public Population(Box2DProcessing w, float startx, float starty, float[] dims, int[] brainsize, Vec2 goal, int size,
                      double crossoverProb, double mutationRate, int mutationFreq) {
        this.box2d = w;
        this.size = size;
        Vec2 st = w.coordPixelsToWorld(startx, starty);
        this.startx = st.x;
        this.starty = st.y;
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
        bestEver = creatures.get(0).closestDistance;

        poppool = new int[(int)(.5*size*(size+1))];
        int processed = 0;
        for (int i=0; i<size; i++) {
            for (int j=0; j<=i; j++)
                poppool[processed+j] = i;
            processed+=i+1;
        }
    }


    public Vec2 getStart() {
        return new Vec2(this.startx, this.starty);
    }

    public float getBestEver() {
        return bestEver;
    }

    public void update() {
        for (Creature c : creatures) {
            c.update();
        }
    }

    private Creature tournamentSelect(int size) {
        if (size > this.creatures.size()) {
            return this.creatures.get(0).copy();
        }
        int best = -1;
        for (int i = 0; i < size; i++) {
            int rInd = new Random().nextInt(this.creatures.size());
            if (best == -1 || this.creatures.get(rInd).closestDistance < this.creatures.get(best).closestDistance) {
                best = rInd;
            }
        }
        //System.out.println("best " + best);
        return this.creatures.get(best).copy();
    }

    public void nextGen() {
        creatures.sort((x, y) -> (x.closestDistance > y.closestDistance) ? 1 : -1);
        Creature best = creatures.get(0);

        if (best.closestDistance < bestEver) // update bestEver if applicable
            bestEver = best.closestDistance;

        ArrayList<Creature> newGen = new ArrayList<>();
        for (int c = 0; c < this.creatures.size(); c++) {
            newGen.add(tournamentSelect(30));
        }
        for (Creature c: newGen) {
            c.brain.mutate(this.mutationRate, 1);
        }

        for (Creature c: this.creatures) {
            c.kill();
        }
        this.creatures = newGen;
        for (Creature c : creatures)
            c.setGoal(goal.x, goal.y);
    }

    public float reproduce() {
        creatures.sort((x, y) -> (x.closestDistance > y.closestDistance) ? 1 : -1);
        Creature best = creatures.get(0);

        if (best.closestDistance < bestEver) // update bestEver if applicable
            bestEver = best.closestDistance;

        ArrayList<Creature> newGen = new ArrayList<>();
        for (int i=0; i<size; i++) {
            Creature father = creatures.get(choosePartner());
            Creature mother = creatures.get(choosePartner());
            Creature child = new Creature(startx+50, starty, dims, brainsize, box2d);
            child.brain = NeuralNetwork.mate(father.getBrain(), mother.getBrain(), crossoverProb, mutationRate, mutationFreq);
            newGen.add(child);
        }
        for (Creature c : creatures)
            c.kill();

        creatures = newGen;
        return bestEver;
    }


    public int choosePartner() {
        return size - 1 - poppool[(int)(Math.random()*poppool.length)];

    }

    public LinkedList<Creature> getCreatures() {
        return new LinkedList<>(creatures);
    }

    public static void main(String[] args) {
        int[] freqs = new int[10];
        int[] poppool = new int[(int)(.5*10*(10+1))];
        int processed = 0;
        for (int i=0; i<10; i++) {
            for (int j=0; j<=i; j++)
                poppool[processed+j] = i;
            processed+=i+1;
        }

        for (int i=0; i<100; i++) {
            freqs[9-poppool[(int)(Math.random()*poppool.length)]]++;
        }

        for (int i=0; i<freqs.length; i++)
            System.out.println(((float)freqs[i]/100));
        double sum = 0;
        for (int i=0; i<10; i++) {
            sum += (double)freqs[i]/100;
        }
        System.out.println(sum);
    }
}
