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

    public Population(Box2DProcessing w, float startx, float starty, float[] dims, int[] brainsize, Vec2 goal, int size, double crossoverProb, double mutationRate, int mutationFreq) {
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

    public void update() {
        for (Creature c : creatures) {
            c.update();
        }
    }

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

    public int choosePartner() {
        return size - 1 - poppool[(int)(Math.random()*poppool.length)];

    }

    public Creature getBest() {
        return bestEver;
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
