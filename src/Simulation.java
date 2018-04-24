import processing.core.*;

public class Simulation {

    private Sketch p;
    private Population[] populations;
    private int simFrames;
    private int currFrame = 0;
    private int speedMult = 1;
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
            p.color(0, 255, 255);
            p.ellipse(this.target.x, this.target.y, 10, 10);
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
        for (Population p : this.populations) {
            this.target = target;
            p.setTarget(target);
        }
    }

    public void draw() {
        for (Population p: this.populations) {
            p.draw();
        }
    }
}
