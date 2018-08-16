
import processing.core.*;

import java.util.ArrayList;

public class Sketch extends PApplet {
    //private static final int W = 1000;
    //private static final int H = 1000;

    private Simulation s;

    public void settings() {
        //size(W, H);
        fullScreen();
    }

    public void setup() {
        noStroke();

        ArrayList<Population> populations = new ArrayList<>();
        populations.add(new Population(this, 50, .1, new int[] {2}, new PVector(255, 0, 0)));
        populations.add(new Population(this, 50, .1, new int[] {3}, new PVector(255, 255, 0)));
        populations.add(new Population(this, 50, .1, new int[] {4}, new PVector(0, 255, 0)));
        populations.add(new Population(this, 50, .1, new int[] {5}, new PVector(0, 255, 255)));
        populations.add(new Population(this, 50, .1, new int[] {6}, new PVector(0, 0, 255)));

        Population[] creatures = new Population[populations.size()];
        creatures = populations.toArray(creatures);

        this.s = new Simulation(this, 500, creatures);
    }

    public void keyPressed() {
        this.s.keyPressed(key);
    }

    public void draw() {
        this.s.update();
        this.s.draw();
    }

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }
}
