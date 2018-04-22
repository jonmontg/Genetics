import processing.core.*;

public class Simulation {

    Sketch p;
    Population[] populations;
    int simFrames;
    int currFrame = 0;
    int speedMult = 1;

    public Simulation(Sketch p, int length, Population[] creatures) {
        this.p = p;
        this.simFrames = length;
        this.populations = creatures;
    }

    public void update() {
        System.out.println("F: " + this.currFrame);
        for (int n = 0; n < speedMult; n++) {
            this.currFrame++;
            for (int i = 0; i < this.populations.length; i++) {
                this.populations[i].update();
            }
        }
        if (this.currFrame >= simFrames) {
            this.currFrame = 0;
            for (int i = 0; i < this.populations.length; i++) {
                this.populations[i].nextGen();
            }
            p.background(100);
        }
    }

    public void draw() {
        for (int i = 0; i < this.populations.length; i++) {
            this.populations[i].draw();
        }
    }
}
