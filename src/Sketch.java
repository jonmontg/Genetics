
import processing.core.*;

import java.util.Random;

public class Sketch extends PApplet {
    private static final int W = 1600;
    private static final int H = 900;

    public void settings() {
        size(W, H);
    }

    private Simulation s;

    public void setup() {
        // init simulation
        Population[] creatures = new Population[2];
        creatures[0] = new Population(this, 300, .00001, 1, .35, new int[] {8});
        creatures[1] = new Population(this, 300, .00001, 1, .35, new int[] {10});

        this.s = new Simulation(this, 150, creatures);
        background(100);
    }

    public void mousePressed() {
        PVector target = new PVector(mouseX, mouseY);
        this.s.setTarget(target);
    }

    public void draw() {
        this.s.update();
        this.s.draw();
    }

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }
}
