import processing.core.*;

public class Creature extends PApplet implements Comparable<Creature> {

    // temp vars
    private static final int nInputs = 2; // x, y
    private static final int nOutputs = 2; //xa, ya

    double x;
    double y;
    double dx;
    double dy;
    double maxV = 10;
    Sketch p;

    NeuralNetwork brain;

    /**
     * Creates a new Creature
     * @param brainSize - sizes for Creature's brain's hidden layers
     */
    public Creature(Sketch p, int[] brainSize) {
        this.p = p;
        this.x = Sketch.W / 2;
        this.y = Sketch.H / 2;
        this.dx = 0;
        this.dy = 0;
        this.brain = new NeuralNetwork(nInputs, brainSize, nOutputs);
    }

    /**
     * Creates a new Creature given a brain
     * @param brain - Neural Network of Creature brain
     */
    public Creature (NeuralNetwork brain) {
        this.x = Sketch.W / 2;
        this.y = Sketch.H / 2;
        this.dx = 0;
        this.dy = 0;
        this.brain = brain;
    }

    public void update() {
        // outputs represent x and y acceleration
        double[] outputs = brain.predict(nnInputs());
        this.dx += outputs[0];
        this.dx = Math.max(-maxV, Math.min(this.dx, maxV));
        this.dy += outputs[1];
        this.dy = Math.max(-maxV, Math.min(this.dy, maxV));
        this.x += this.dx;
        this.y += this.dy;
    }

    public void draw() {
        p.ellipse((int)this.x, (int)this.y, 10, 10);
    }

    // Temp fitness function go right
    public double fitness() {
        return x;
    }

    public void mutate(double mr, int mf) {
        this.brain.mutate(mr, mf);
    }

    private double[] nnInputs() {
        return new double[] {this.x, this.y};
    }

    public void print() {
        System.out.println(x + " " + y);
    }

    // creatures with greater fitness will be ordered earlier
    public int compareTo (Creature o) {
        return -Double.compare(this.fitness(), o.fitness());
    }

}