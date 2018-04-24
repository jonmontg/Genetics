import org.jbox2d.common.Vec2;

import java.util.LinkedList;

public class StaircaseWindow {

    private Box2DProcessing box2d;
    private LinkedList<Boundary> boundaries;

    public StaircaseWindow(float width, float height, Box2DProcessing b2d) {
        this.box2d = b2d;
        boundaries.add(new Boundary(0, height/2, 5, height, box2d));
        Staircase stairs = new Staircase(new Vec2(2*width/15, height), width/15, height/15, 13, box2d);
    }
}
