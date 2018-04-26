
import processing.core.*;

import java.util.Random;

public class Sketch extends PApplet {
    private static final int W = 1600;
    private static final int H = 900;

    public void settings() {
        size(W, H);
    }

    public Simulation s;

    public void setup() {
        noStroke();
        // init simulation
        Population[] creatures = new Population[6];
        creatures[0] = new Population(this, 200, .6, 1, new int[] {10}, new PVector(255, 0, 0));
        creatures[1] = new Population(this, 200, .7, 1, new int[] {10}, new PVector(150, 0, 0));
        creatures[2] = new Population(this, 200, .8, 1, new int[] {10}, new PVector(255, 255, 0));
        creatures[3] = new Population(this, 200, .9, 1, new int[] {10}, new PVector(150, 150, 0));
        creatures[4] = new Population(this, 200, 1, 1, new int[] {10}, new PVector(0, 255, 0));
        creatures[5] = new Population(this, 200, 1.1, 1, new int[] {10}, new PVector(0, 150, 0));
        //creatures[6] = new Population(this, 50, .001, 1, new int[] {10, 10}, new PVector(0, 255, 255));
        //creatures[7] = new Population(this, 50, .001, 1, new int[] {10, 10}, new PVector(0, 150, 150));
        //creatures[8] = new Population(this, 50, .001, 1, new int[] {10, 10}, new PVector(0, 0, 255));
        //creatures[9] = new Population(this, 50, .01, 1, new int[] {10, 10}, new PVector(0, 0, 150));
        //creatures[10] = new Population(this, 50, .01, 1, new int[] {12, 10}, new PVector(255, 0, 255));
        //creatures[11] = new Population(this, 50, .1, 1, new int[] {12, 10}, new PVector(150, 0, 150));
        //creatures[12] = new Population(this, 50, .1, 1, new int[] {12, 10}, new PVector(255, 255, 255));
        //creatures[13] = new Population(this, 50, .1, 1, new int[] {12, 10}, new PVector(150, 150, 150));
        //creatures[14] = new Population(this, 50, 1, 1, new int[] {12, 10}, new PVector(80, 80, 80));
        //creatures[15] = new Population(this, 50, 1, 1, new int[] {12, 10}, new PVector(0, 0, 0));
        //creatures[16] = new Population(this, 50, 1, 1, new int[] {12, 10}, new PVector(0, 100, 200));
        //creatures[17] = new Population(this, 50, 10, 1, new int[] {12, 10}, new PVector(200, 100, 0));
        //creatures[18] = new Population(this, 50, 10, 1, new int[] {12, 10}, new PVector(100, 200, 0));
        //creatures[19] = new Population(this, 50, 10, 1, new int[] {12, 10}, new PVector(0, 200, 100));



        this.s = new Simulation(this, 500, creatures);
        background(100);
    }

    public void mousePressed() {
        PVector target = new PVector(mouseX, mouseY);
        this.s.setTarget(target);
    }

    public void keyPressed() {
        if (key == 'a') {
            if (this.s.speedMult > 1) {
                this.s.speedMult /= 2;
            }
        } else if (key == 'd') {
            System.out.println("D");
            if (this.s.speedMult < 1000) {
                this.s.speedMult *= 2;
            }
        }
    }

    public void draw() {
        this.s.update();
        this.s.draw();
    }

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }
}
