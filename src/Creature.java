import processing.core.*;


class Creature {

    // temp vars
    private static final int nInputs = 2;
    private static final int nOutputs = 2; //xa, ya

    private Sketch p;

    private PVector target;

    private PVector acc;
    private PVector vel;
    private PVector pos;

    private PVector color;
    private NeuralNetwork brain;
    private double mutationRate;
    private double fitness;

    Creature(Sketch p, double mr, int[] brainSize, PVector target, PVector col) {
        this.p = p;
        this.mutationRate = mr;
        this.target = target;
        this.acc = new PVector(0, 0);
        this.vel = new PVector(0, 0);
        this.pos = new PVector(this.p.width / 2, this.p.height / 2);
        this.color = col;
        this.brain = new NeuralNetwork(nInputs, brainSize, nOutputs);
    }

    private Creature (Sketch p, double mr, NeuralNetwork brain, PVector target, PVector col) {
        this.p = p;
        this.mutationRate = mr;
        this.target = target;
        this.acc = new PVector(0, 0);
        this.vel = new PVector(0, 0);
        this.pos = new PVector(this.p.width / 2, this.p.height / 2);
        this.color = col;
        this.brain = brain;
    }

    void setTarget(float x, float y) {
        this.target = new PVector(x, y);
    }

    void update() {
            double[] outputs = brain.predict(nnInputs());
            float aScale = 1f;
            this.acc = new PVector((float)outputs[0] * aScale, (float)outputs[1] * aScale);
            this.acc.limit(aScale);

            this.vel.add(this.acc);
            float maxVel = 2;
            this.vel.limit(maxVel);
            this.pos.add(this.vel);

            this.fitness += PVector.dist(this.pos, this.target)/1000;
    }

    void draw() {
        this.p.noStroke();
        this.p.fill(this.color.x, this.color.y, this.color.z);
        this.p.ellipse((int)this.pos.x, (int)this.pos.y, 10, 10);
    }

    // Currently minimizing fitness
    double getFitness() {
        return this.fitness;
    }

    void mutate(double mr) {
        this.brain.mutate(mr);
    }

    private double[] nnInputs() {
        return new double[] {this.pos.x - this.target.x, this.pos.y - this.target.y};
    }

    Creature copy() {
        return new Creature(this.p, this.mutationRate, this.brain.copy(), this.target, this.color);
    }

    void print() {
        System.out.println("F: " + this.fitness);
        this.brain.print();
    }
}