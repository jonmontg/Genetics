import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

import java.util.LinkedList;
import java.util.Random;


public class MainApp extends PApplet {

    private Box2DProcessing box2d;
    private LinkedList<Circle> circles = new LinkedList<>();
    private LinkedList<Box> boxes = new LinkedList<>();
    private LinkedList<Boundary> boundaries = new LinkedList<>();
    private Creature creature;

    boolean simulate = true;

    public static void main(String[] args) {
        PApplet.main("MainApp", args);
    }

    public void settings() {
        size(800,600);
    }

    public void setup() {
        box2d = new Box2DProcessing(this);
        box2d.createWorld();
        boundaries.add(new Boundary(400,600,800,10,box2d));
        creature = new Creature(400, 500,100, 15, box2d);
        creature.addBox(100, 15);

        LinkedList<Float> speeds = new LinkedList<>();
        LinkedList<Float> torques = new LinkedList<>();
        speeds.add(PI*2);
        speeds.add(PI*2);
        torques.add(10000f);
        torques.add(10000f);
        creature.setJointSpeeds(speeds);
        creature.setJointTorques(torques);
    }

    public void draw() {
        background(255);
        box2d.step();

        if (mousePressed) {
            LinkedList<Float> newSpeeds = new LinkedList<>();
            Random rand = new Random();
            for (int i=0; i<creature.getJoints().size(); i++)
                newSpeeds.add(2*PI-rand.nextFloat()*4*PI);
            creature.setJointSpeeds(newSpeeds);
        }

        displayCreature(creature);
    }

    public void displayCircle(Circle circle) {
        fill(175);
        stroke(0);

        Vec2 posn = circle.getPosn();
        ellipse(posn.x, posn.y, circle.getRadius(), circle.getRadius());
    }

    public void displayCreature(Creature c) {
        for (Box box : c.getBody())
            displayBox(box);
    }

    public void displayBox(Box box) {
        Body body = box.getBody();
        Vec2 posn = box2d.getBodyPixelCoord(body);
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
        Vec2 posn = bound.getPosn();
        Vec2 dim = bound.getDimensions();
        rect(posn.x, posn.y, dim.x, dim.y);
    }

    public void addDynamicCircle(float x, float y, float r) {
        circles.add(new Circle(x, y, r, box2d));
    }

    public void addDynamicBox(float x, float y, float w, float h, float angle) {
        boxes.add(new Box(x, y, w, h, angle, box2d));
    }

    public void addBoundary(float x, float y, float w, float h) {
        boundaries.add(new Boundary(x,y, w, h, box2d));
    }

    public void createNewBox() {
        float startx = mouseX;
        float starty = mouseY;
        System.out.println("start");
        while (mousePressed) {
            fill(175);
            stroke(0);
            rect(startx, starty, mouseX, mouseY);
        }
        System.out.println("created");
        startx = box2d.scalarPixelsToWorld(mouseX);
        starty = box2d.scalarPixelsToWorld(mouseY);
        float endx = box2d.scalarPixelsToWorld(mouseX);
        float endy = box2d.scalarPixelsToWorld(mouseY);
        float[] horizontal = orient(startx, endx);
        float[] vertical = orient(starty, endy);
        addDynamicBox(horizontal[0], vertical[0], horizontal[1], vertical[1],0);
    }

    private float[] orient(float start, float end) {
        float len;
        float posn;
        if (start > end) {
            len = start - end;
            posn = end + (len/2);
        }
        else {
            len = end - start;
            posn = start + (len/2);
        }
        return new float[] {posn, len};
    }
}