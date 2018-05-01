import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Box {
    Body body;
    Box2DProcessing box2d;
    float width;
    float height;

    public Box(float x, float y, float width, float height, float angle, Box2DProcessing box2d) {
        this.box2d = box2d;
        this.width = width;
        this.height = height;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(box2d.coordPixelsToWorld(x,y));
        bd.angle = angle;
        body = box2d.createBody(bd);
        PolygonShape ps = new PolygonShape();
        float hw = box2d.scalarPixelsToWorld(width/2);
        float hh = box2d.scalarPixelsToWorld(height/2);
        ps.setAsBox(hw, hh);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1f;
        fd.friction = .6f;
        fd.restitution = .5f;
        fd.filter.groupIndex = -1;
        body.createFixture(fd);
    }

    /**
     * Returns the box2d world position of this body
     * @return - Vec2 body position
     */
    public Vec2 getPosn() {
        return body.getPosition();
    }

    public Vec2 getPixelPosn() {
        Vec2 worldPosn = getPosn();
        return new Vec2(box2d.scalarWorldToPixels(worldPosn.x), box2d.scalarWorldToPixels(worldPosn.y));
    }

    /**
     * Returns the Width and Length of this Box in pixel units
     * @return - Vec2 Pixel dimensions
     */
    public Vec2 getDimensions() {
        return new Vec2(width, height);
    }

    /**
     * Returns the angle of the box2d body of this box
     * @return - float body angle
     */
    public float getAngle() {
        return body.getAngle();
    }

    /**
     * Returns the box2d body of this box
     * @return - Body
     */
    public Body getBody() {
        return body;
    }

    /**
     * Returns the pixel width of this box
     * @return - float pixel width
     */
    public float getWidth() {
        return width;
    }

    /**
     * returns the box2DProcessing world that this box exists within
     * @return - Box2dProcessing object
     */
    public Box2DProcessing getBox2d() { return box2d; }
}
