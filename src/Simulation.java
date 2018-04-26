import processing.core.*;

public class Simulation {

    private Sketch p;
    private Population[] populations;
    private int simFrames;
    private int currFrame = 0;
    private int speedMult = 2;
    private PVector target;

    public Simulation(Sketch p, int length, Population[] creatures) {
        this.p = p;
        this.simFrames = length;
        this.populations = creatures;
        this.target = new PVector(this.p.width / 2, this.p.height / 4);
    }

    public void update() {
        for (int frame = 0; frame < speedMult; frame++) {
            this.currFrame++;
            p.background(100);
            p.color(255);
            for (Population p: this.populations) {
                p.update();
            }
            p.fill(255, 255, 255);
            p.ellipse(this.target.x, this.target.y, 16, 16);
        }
        if (this.currFrame >= simFrames) {
            this.currFrame = 0;
            for (Population p: this.populations) {
                p.nextGen();
            }
            p.background(100);
        }
    }

    public void setTarget(PVector target) {
            this.target = target;
    }

    public void draw() {
        for (Population p: this.populations) {
            p.draw();
        }
    }

    public void updateFitness(Creature c) {
        if (c.isAlive()) {
            c.setFitness(c.getFitness() + (double)PVector.dist(c.getPos(), this.target) / 1000);
        }
    }
}
