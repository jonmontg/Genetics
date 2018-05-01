
import processing.core.*;
import processing.event.MouseEvent;
import java.util.ArrayList;


public class Sketch extends PApplet {
    private static final int W = 1000;
    private static final int H = 1000;

    public Simulation s;

    public void settings() {
        size(W, H);
    }

    public void setup() {

        Population[] creatures = new Population[8];
        creatures[0] = new Population(this, 50, .1, new int[] {2}, new PVector(255, 0, 0));
        creatures[1] = new Population(this, 50, .2, new int[] {2}, new PVector(200, 0, 0));
        creatures[2] = new Population(this, 50, .3, new int[] {2}, new PVector(150, 0, 0));
        creatures[3] = new Population(this, 50, .4, new int[] {2}, new PVector(100, 0, 0));
        creatures[4] = new Population(this, 50, .1, new int[] {6, 6}, new PVector(0, 255, 0));
        creatures[5] = new Population(this, 50, .2, new int[] {6, 6}, new PVector(0, 200, 0));
        creatures[6] = new Population(this, 50, .3, new int[] {6, 6}, new PVector(0, 150, 0));
        creatures[7] = new Population(this, 50, .4, new int[] {6, 6}, new PVector(0, 100, 0));





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
