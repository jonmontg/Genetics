import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import processing.core.PApplet;
import java.util.LinkedList;
import java.util.Random;


public class Master extends PApplet {

    private LinkedList<Box2DProcessing> worlds = new LinkedList<>();
    private LinkedList<Creature> creatures = new LinkedList<>();
    private LinkedList<DisplayBox> boundaries = new LinkedList<>();


    public static void main(String[] args) {
        PApplet.main("Master");
    }

    public void settings() {
        size(800,600);
    }

    public void setup() {
        for (int i=0; i<100; i++) {
            Box2DProcessing nw = new Box2DProcessing(this);
            worlds.add(nw);
            nw.createWorld();
            creatures.add(new Creature(width / 15, height - 30, 30, 10, 3, nw));
            new Boundary(400,600,800,10, nw);
        }
        boundaries.add(new DisplayBox(400, 600, 800, 10));
    }

    public void draw() {

        background(255);
        for (Box2DProcessing world : worlds)
            world.step();

        for (Creature creature: creatures)
            creature.advance();

        for (DisplayBox bound : boundaries)
            displayBoundary(bound);

        displayCreature(0);
    }

    public void displayCircle(Circle circle) {
        fill(175);
        stroke(0);

        Vec2 posn = circle.getPosn();
        ellipse(posn.x, posn.y, circle.getRadius(), circle.getRadius());
    }

    public void displayCreature(int i) {
        for (Box box : creatures.get(i).getBody())
            displayBox(box);
    }

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

    public void displayBoundary(DisplayBox bound) {
        fill(0);
        stroke(0);
        rectMode(CENTER);
        float[] dims = bound.getDim();
        rect(dims[0], dims[1], dims[2], dims[3]);
    }

    //public void addBoundary()
}
