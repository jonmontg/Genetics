import org.jbox2d.common.Vec2;

import java.util.LinkedList;

public class StaircaseWindow implements Window {

    private Box2DProcessing box2d;
    private LinkedList<Boundary> boundaries = new LinkedList<>();
    private Vec2 goal;
    private float width, height;
    private int num;

    public StaircaseWindow(float width, float height, int number, Box2DProcessing b2d) {
        this.box2d = b2d;
        this.width = width;
        this.height = height;
        num = number;
        boundaries.add(new Boundary(0, height/2, 5, height, box2d));
        boundaries.add(new Boundary(width/2, height, width, 5, box2d));
        Staircase stairs = new Staircase(new Vec2(3*width/number, height), width/number, height/number, number-3, box2d);
        boundaries.addAll(stairs.getShape());
        goal = new Vec2(width, 0);
    }

    /**
     * Returns the list of Boundaries that make up this
     * @return - boundaries
     */
    public LinkedList<Boundary> getBoundaries() {
        return boundaries;
    }

    public Vec2 getGoal() {return goal;}

    public Vec2 getStartPosn() {
        return new Vec2(1*width/num, height-20);
    }
}
