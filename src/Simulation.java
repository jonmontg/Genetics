import processing.core.*;

import java.util.Random;

public class Simulation {

    private Sketch p;
    private Population[] populations;
    private int simFrames;
    private int currFrame = 0;
    public int speedMult = 1;
    private PVector target;

    public Simulation(Sketch p, int length, Population[] creatures) {
        this.p = p;
        this.simFrames = length;
        this.populations = creatures;
        this.target = new PVector(new Random().nextInt(this.p.width), new Random().nextInt(this.p.height));
    }

    public void update() {
        for (int frame = 0; frame < speedMult; frame++) {
            this.currFrame++;
            p.background(100);
            p.fill(255, 255, 255);
            p.ellipse(this.target.x, this.target.y, 16, 16);

            for (Population p: this.populations) {
                p.update();
            }
        }
        if (this.currFrame >= simFrames) {
            this.currFrame = 0;
            for (Population p: this.populations) {
                p.nextGen();
                this.setTarget(new PVector(new Random().nextInt(this.p.width), new Random().nextInt(this.p.height)));
            }
        }
    }

    public void setTarget(PVector target) {
        this.target = target;
        for (Population p: this.populations) {
            p.setTarget(this.target);
        }
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
