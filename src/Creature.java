import processing.core.*;

public class Creature {

    // temp vars
    private static final int nInputs = 6;
    private static final int nOutputs = 2; //xa, ya
    private PVector target;

    private Sketch p;

    private PVector pos;
    private PVector vel;
    private PVector acc;
    private double maxV = 4;

    private PVector color;
    private NeuralNetwork brain;
    private double fitness;
    private boolean alive;

    /**
     * Creates a new Creature
     * @param brainSize - sizes for Creature's brain's hidden layers
     */
    public Creature(Sketch p, int[] brainSize, PVector col) {
        this.p = p;
        this.pos = new PVector(p.width / 2, 3 * p.height / 4);
        this.vel = new PVector(0, 0);
        this.acc = new PVector(0, 0);
        this.target = new PVector(p.width / 2, p.height / 4);
        this.color = col;
        this.brain = new NeuralNetwork(nInputs, brainSize, nOutputs);
        this.alive = true;
    }

    /**
     * Creates a new Creature given a brain
     * @param brain - Neural Network of Creature brain
     */
    public Creature (Sketch p, NeuralNetwork brain, PVector target, PVector col) {
        this.p = p;
        this.pos = new PVector(p.width / 2, 3 * p.height / 4);
        this.vel = new PVector(0, 0);
        this.acc = new PVector(0, 0);
        this.target = target;
        this.color = col;
        this.brain = brain;
        this.alive = true;
    }

    public void update() {
        if (this.alive) {
            // outputs represent x and y acceleration
            double[] outputs = brain.predict(nnInputs());
            //System.out.println(outputs[0]);
            this.acc = new PVector((float) outputs[0], (float) outputs[1]);
            this.vel.add(this.acc);
            this.vel.limit((float) this.maxV);
            this.pos.add(this.vel);

            if (this.pos.x < 0 || this.pos.x > this.p.width || this.pos.y < 0 || this.pos.y > this.p.height) {
                //this.alive = false;
                //this.fitness = 9999; // arbitrary large number
            }
        }
    }

    public void draw() {
        this.p.fill(color.x, color.y, color.z);
        this.p.ellipse((int)this.pos.x, (int)this.pos.y, 10, 10);
    }

    // Currently minimizing getFitness function
    public double getFitness() {
        //System.out.println(this.fit);
        return this.fitness;
    }

    public void setFitness(double f) {
        this.fitness = f;
    }

    public PVector getPos() {
        return this.pos;
    }

    public boolean isAlive() {
        return alive;
    }

    public double speed() {
        return (double)this.vel.mag();
    }

    public void mutate(double mr, int mf) {
        this.brain.mutate(mr, mf);
    }

    private double[] nnInputs() {
        return new double[] {this.pos.x, this.pos.y, this.vel.x, this.vel.y, this.target.x, this.target.y};
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
        return new Creature(this.p, this.brain.copy(), this.target, this.color.copy());
    }
}