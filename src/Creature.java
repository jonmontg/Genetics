import org.jbox2d.dynamics.World;
import processing.core.*;

import java.util.ArrayList;

public class Creature {

    // temp vars
    private static final int nInputs = 8;
    private static final int nOutputs = 2; //xa, ya

    private Sketch p;

    private PVector target;

    private PVector acc;
    private PVector vel;
    private PVector pos;
    private float maxVel = 4;

    private PVector color;
    private NeuralNetwork brain;
    private double mutationRate;
    private double fitness;

    public Creature(Sketch p, double mr, int[] brainSize, PVector target, PVector col) {
        this.p = p;
        this.mutationRate = mr;
        this.target = target;
        this.acc = new PVector(0, 0);
        this.vel = new PVector(0, 0);
        this.pos = new PVector(this.p.width / 2, this.p.height / 2);
        this.color = col;
        this.brain = new NeuralNetwork(nInputs, brainSize, nOutputs);
    }

    public Creature (Sketch p, double mr, NeuralNetwork brain, PVector target, PVector col) {
        this.p = p;
        this.mutationRate = mr;
        this.target = target;
        this.acc = new PVector(0, 0);
        this.vel = new PVector(0, 0);
        this.pos = new PVector(this.p.width / 2, this.p.height / 2);
        this.color = col;
        this.brain = brain;
    }

    public void setTarget(float x, float y) {
        this.target = new PVector(x, y);
    }

    public void update() {
            double[] outputs = brain.predict(nnInputs());
            float aScale = 1;
            this.acc = new PVector((float)outputs[0] * aScale, (float)outputs[1] * aScale);

            this.vel.add(this.acc);
            this.vel.limit(this.maxVel);
            this.pos.add(this.vel);

            this.fitness += PVector.dist(this.pos, this.target);
    }

    public void draw() {
        this.p.fill(this.color.x, this.color.y, this.color.z);
        this.p.ellipse((int)this.pos.x, (int)this.pos.y, 10, 10);
    }

    // Currently minimizing getFitness function
    public double getFitness() {
        //System.out.println(this.fit);
        return this.fitness;
    }

    public void mutate(double mr) {
        this.brain.mutate(mr);
    }

    private double[] nnInputs() {
        return new double[] {this.acc.x, this.acc.y, this.vel.x, this.vel.y,
                            this.pos.x, this.pos.y, target.x, target.y};
    }

    public Creature copy() {
        return new Creature(this.p, this.mutationRate, this.brain.copy(), this.target, this.color);
    }
}