import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.LinkedList;

public class Population {
    private Box2DProcessing box2d;
    private ArrayList<Creature> creatures;
    private double mutationRate;
    private double mutationFreq;
    private int size;
    private float startx, starty;
    private float[] dims;
    private int[] brainsize;
    private Vec2 goal;

    public Population(Box2DProcessing w, float startx, float starty, float[] dims, int[] brainsize, Vec2 goal, int size, double mutationRate, double mutationFreq) {
        this.box2d = w;
        this.size = size;
        this.startx = startx;
        this.starty = starty;
        this.dims = dims;
        this.brainsize = brainsize;
        this.goal = goal;
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
    }

    public void update() {
        for (Creature c : creatures) {
            c.update();
        }
    }

    public Creature reproduce() {
        creatures.sort((x, y) -> (x.closestDistance > y.closestDistance) ? 1 : -1);
        Creature best = creatures.get(0);
        for (int i=0; i<size-1; i+=2) {
            Creature child1 = new Creature(startx, starty, dims, brainsize, box2d);
            Creature child2 = new Creature(startx, starty, dims, brainsize, box2d);
            child1.brain = NeuralNetwork.mate(creatures.get(i).getBrain(), creatures.get(i+1).getBrain());
            child2.brain = NeuralNetwork.mate(creatures.get(i+1).getBrain(), creatures.get(i).getBrain());
            creatures.set(i, child1);
            creatures.set(i+1, child2);
        }
        return best;
    }

    public void choosePartner() {

    }

    public LinkedList<Creature> getCreatures() {
        return new LinkedList<>(creatures);
    }
}
