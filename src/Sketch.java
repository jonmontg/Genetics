
import processing.core.*;
import processing.event.MouseEvent;
import java.util.ArrayList;


public class Sketch extends PApplet {
    private static final int W = 1000;
    private static final int H = 1000;

    public Simulation s;

    public void settings() {
        //size(W, H);
        fullScreen();
    }

    public void setup() {
        noStroke();

        Population[] creatures = new Population[16];
        creatures[0] = new Population(this, 50, .01, new int[] {2}, new PVector(255, 0, 0));
        creatures[1] = new Population(this, 50, .1, new int[] {2}, new PVector(200, 0, 0));
        creatures[2] = new Population(this, 50, 1, new int[] {2}, new PVector(150, 0, 0));
        creatures[3] = new Population(this, 50, 10, new int[] {2}, new PVector(100, 0, 0));
        creatures[4] = new Population(this, 50, .01, new int[] {6}, new PVector(0, 255, 0));
        creatures[5] = new Population(this, 50, .1, new int[] {6}, new PVector(0, 200, 0));
        creatures[6] = new Population(this, 50, 1, new int[] {6}, new PVector(0, 150, 0));
        creatures[7] = new Population(this, 50, 10, new int[] {6}, new PVector(0, 100, 0));
        creatures[8] = new Population(this, 50, .01, new int[] {6, 6}, new PVector(0, 0, 255));
        creatures[9] = new Population(this, 50, .1, new int[] {6, 6}, new PVector(0, 0, 200));
        creatures[10] = new Population(this, 50, 1, new int[] {6, 6}, new PVector(0, 0, 150));
        creatures[11] = new Population(this, 50, 10, new int[] {6, 6}, new PVector(0, 0, 100));
        creatures[12] = new Population(this, 50, .01, new int[] {6, 6, 6}, new PVector(255, 255, 0));
        creatures[13] = new Population(this, 50, .1, new int[] {6, 6, 6}, new PVector(20, 200, 0));
        creatures[14] = new Population(this, 50, 1, new int[] {6, 6, 6}, new PVector(150, 150, 0));
        creatures[15] = new Population(this, 50, 10, new int[] {6, 6, 6}, new PVector(100, 100, 0));

        this.s = new Simulation(this, 500, creatures);
    }

    public void mousePressed() {
        this.s.setTarget(mouseX, mouseY);
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
