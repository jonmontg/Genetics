import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import processing.core.PApplet;
import java.util.LinkedList;


public class Master extends PApplet {

    Box2DProcessing box2d;
    private Population creatures;
    private LinkedList<Boundary> boundaries = new LinkedList<>();


    Creature best;
    double currSteps = 0;
    int generation = 1;
    boolean simulate = true;


    public static void main(String[] args) {
        PApplet.main("Master");
    }

    public void settings() {
        size(800,600);
    }

    public void setup() {
        box2d = new Box2DProcessing(this);
        box2d.createWorld();

        //Window window = new StaircaseWindow(width, height, 35, box2d);
        Window window = new FlatWindow(width, height, box2d);

        Vec2 start = window.getStartPosn();
        creatures = new Population(
                box2d,
                start.x,
                start.y,
                new float[]{20, 5, 20, 5, 20, 5}, // dims
                new int[]{10}, window.getGoal(), // goalposn
                20,
                .2,
                .2,
                1);

        best = creatures.getBest();
        boundaries.addAll(window.getBoundaries());
    }



    public void draw() {
        background(255);
        box2d.step();
        for (Boundary bound : boundaries)
            displayBoundary(bound);

        textSize(32);
        text("Generation "+generation, (int)(1.2*width/3), 30);
        fill(0, 102, 153);


        if (simulate) {
            if (currSteps > 2000) {
                creatures.reproduce();
                best = creatures.getBest();
                generation++;
                currSteps = 0;
            }
            currSteps++;

            creatures.update();

            displayAllCreatures();
        }
        else {
            best.update();
            displayCreature(best);
        }
    }

    /**
     * Displays the creature at the given index in creatures
     * @param i - index of Creature to display
     */
    public void displayCreature(int i) {
        for (Box box : creatures.getCreatures().get(i).getBody())
            displayBox(box);
    }

    public void displayCreature(Creature c) {
        for (Box box : c.getBody())
            displayBox(box);
    }

    /**
     * Displays all creatures in the list of creatures
     */
    public void displayAllCreatures() {
        for (Creature c : creatures.getCreatures()) {
            for (Box box : c.getBody())
                displayBox(box);
        }
    }

    /**
     * Displays the given Box
     * @param box - Box to be displayed
     */
    public void displayBox(Box box) {
        Body body = box.getBody();
        Vec2 posn = box.getBox2d().getBodyPixelCoord(body);
        Vec2 dim = box.getDimensions();
        float angle = body.getAngle();

        pushMatrix();
        translate(posn.x, posn.y);
        rotate(-angle);

        fill(175);
        stroke(0);
        rectMode(CENTER);
        rect(0,0, dim.x, dim.y);
        popMatrix();
    }

    /**
     * Displays the given boundary
     * @param bound - boundary to be displayed
     */
    public void displayBoundary(Boundary bound) {
        fill(0);
        stroke(0);
        rectMode(CENTER);
        float[] dims = bound.getDims();
        rect(dims[0], dims[1], dims[2], dims[3]);
    }

    //public void addBoundary()
}