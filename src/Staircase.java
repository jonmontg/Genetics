import org.jbox2d.common.Vec2;
import java.util.LinkedList;

public class Staircase {
    private Box2DProcessing box2d;
    private LinkedList<Boundary> shape;
    private float stepwidth;
    private float stepheight;

    public Staircase(Vec2 firstPosn, float stepwidth, float stepheight, float number, Box2DProcessing box2d) {
        this.box2d = box2d;
        shape = new LinkedList<>();
        this.stepwidth = stepwidth;
        this.stepheight = stepheight;
        for (int i=0; i<number; i++) {
            float x = firstPosn.x+(stepwidth*i);
            float y = firstPosn.y-(stepheight*i);
            addStep(x, y);
        }
    }

    /**
     * Adds step to staircase
     * @param x - bottom left x position of stair
     * @param y - bottom left y position of stair
     */
    public void addStep(float x, float y) {
        shape.add(new Boundary(x, y-(stepheight/2), stepwidth/10, stepheight, box2d));
        shape.add(new Boundary(x+(stepwidth/2), y-stepheight, stepwidth, stepheight/10, box2d));
    }

    /**
     * Returns the list of Boundaries that make up this Staircase
     * @return - LinkedList of Boundaries
     */
    public LinkedList<Boundary> getShape() {
        return shape;
    }

    /**
     * Returns the position of the top right position of the last stair in this staircase
     * @return - Vec2 position
     */
    public Vec2 getEndWorldPosn() {
        Vec2 last = shape.getLast().getWorldPosn();
        return new Vec2(last.x+(stepwidth/2), last.y+(stepheight/2));
    }
}
