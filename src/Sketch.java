
import processing.core.*;

import java.util.*;

public class Sketch extends PApplet {
    public static final int W = 1000;
    public static final int H = 1000;

    public void settings() {
        size(W, H);
    }

    Simulation s;

    ArrayList<Creature> creatures;
    public void setup() {
        // init simulation

        Population[] creatures = new Population[2];
        creatures[0] = new Population(this, 100, 3, 100, new int[] {3, 3, 3});
        creatures[1] = new Population(this, 100, .1, 100, new int[] {4, 4, 4});

        s = new Simulation(this, 100, creatures);
        background(100);
    }

    public void draw() {
        s.update();
        s.draw();
    }

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

}
