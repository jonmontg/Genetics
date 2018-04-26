import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import processing.core.PApplet;
import java.util.LinkedList;
import java.util.Random;


public class Master extends PApplet {

    Box2DProcessing box2d;
    private LinkedList<Creature> creatures = new LinkedList<>();
    private LinkedList<Boundary> boundaries = new LinkedList<>();


    public static void main(String[] args) {
        PApplet.main("Master");
    }

    public void settings() {
        size(800,600);
    }

    public void setup() {
        box2d = new Box2DProcessing(this);
        box2d.createWorld();
        for (int i=0; i<100; i++) {
            creatures.add(new Creature(width / 15, height - 30, 30, 10, 3, box2d));
        }
        boundaries.add(new Boundary(400, 600, 800, 10, box2d));
    }

    public void draw() {

        background(255);
        box2d.step();

        for (Boundary bound : boundaries)
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

    public void displayBoundary(Boundary bound) {
        fill(0);
        stroke(0);
        rectMode(CENTER);
        float[] dims = bound.getDims();
        rect(dims[0], dims[1], dims[2], dims[3]);
    }

    //public void addBoundary()
}
