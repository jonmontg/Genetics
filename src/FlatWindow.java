import org.jbox2d.common.Vec2;

import java.util.LinkedList;

public class FlatWindow implements Window {

    private Box2DProcessing box2d;
    private LinkedList<Boundary> boundaries = new LinkedList<>();
    private Vec2 goal;

    public FlatWindow(float width, float height, Box2DProcessing box2d) {
        boundaries.add(new Boundary(0, height/2, 5, height, box2d));
        boundaries.add(new Boundary(width/2, height, width, 5, box2d));
        boundaries.add(new Boundary(width, height/2, 5, height, box2d));
        goal = new Vec2(width, height);
    }
    /**
     * Returns the list of Boundaries that make up this
     * @return - boundaries
     */
    public LinkedList<Boundary> getBoundaries() {
        return boundaries;
    }

    public Vec2 getGoal() {return goal;}

}
