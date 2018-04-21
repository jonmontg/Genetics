import processing.core.*;

public class Simulation extends PApplet {

    Population[] populations;
    int simFrames;
    int speedMult = 1;

    public Simulation(int length, Population[] creatures) {
        this.simFrames = length;
        this.populations = creatures;
    }

    public void update() {
        for (int n = 0; n < speedMult; n++) {
            for (int i = 0; i < this.populations.length; i++) {
                this.populations[i].update();
            }
        }
    }

    public void draw() {
        for (int i = 0; i < this.populations.length; i++) {
            this.populations[i].draw();
        }
    }
}
