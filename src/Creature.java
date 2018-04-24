import processing.core.*;

public class Creature {

    // temp vars
    private static final int nInputs = 4;
    private static final int nOutputs = 2; //xa, ya

    private PVector pos;
    private PVector vel;
    private PVector acc;
    private  double fit;
    private double maxV = 6;
    private Sketch p;
    private NeuralNetwork brain;
    private PVector target;

    /**
     * Creates a new Creature
     * @param brainSize - sizes for Creature's brain's hidden layers
     */
    public Creature(Sketch p, int[] brainSize) {
        this.p = p;
        this.fit = 0;
        this.pos = new PVector(p.width / 2, 3 * p.height / 4);
        this.vel = new PVector(0, 0);
        this.acc = new PVector(0, 0);
        this.target = new PVector(p.width / 2, p.height / 4);
        this.brain = new NeuralNetwork(nInputs, brainSize, nOutputs);
    }

    /**
     * Creates a new Creature given a brain
     * @param brain - Neural Network of Creature brain
     */
    public Creature (Sketch p, NeuralNetwork brain, PVector target) {
        this.p = p;
        this.fit = 0;
        this.pos = new PVector(p.width / 2, 3 * p.height / 4);
        this.vel = new PVector(0, 0);
        this.acc = new PVector(0, 0);
        this.target = target;
        this.brain = brain;
    }

    public void update() {
        // outputs represent x and y acceleration
        double[] outputs = brain.predict(nnInputs());

        //double mag = Math.abs(outputs[0]) * .2;
        //double sin_ang = outputs[1];
        //double cos_ang = outputs[2];
        //this.acc = PVector.fromAngle((float)Math.atan2(sin_ang, cos_ang));

        this.acc = new PVector((float)outputs[0], (float)outputs[1]);

        this.vel.add(this.acc);
        this.vel.limit((float)this.maxV);
        this.pos.add(this.vel);
        double dist = (double)PVector.dist(this.pos, this.target);
        this.fit += dist / 1000;
    }

    public void draw() {
        this.p.ellipse((int)this.pos.x, (int)this.pos.y, 10, 10);
    }

    // Currently minimizing fitness function
    public double fitness() {
        //System.out.println(this.fit);
        return (double)PVector.dist(this.pos, this.target);
    }


    public void mutate(double mr, int mf) {
        this.brain.mutate(mr, mf);
    }

    private double[] nnInputs() {
        return new double[] {this.pos.x, this.pos.y, this.target.x, this.target.y};
    }

    public void setPos(int x, int y) {
        this.pos.set(x, y);
    }

    public void print() {
        System.out.println(this.pos.x + " " + this.pos.y);
    }

    public void setTarget(PVector target) {
        this.target = target;
    }

    public Creature copy() {
        return new Creature(this.p, this.brain.copy(), this.target);
    }
}