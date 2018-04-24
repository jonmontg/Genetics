import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Circle {
    Body body;
    float r;

    public Circle(float x, float y, float r, Box2DProcessing box2d) {
        BodyDef bd = new BodyDef();
        Vec2 posn = new Vec2(x, y);
        bd.position.set(posn);
        bd.type = BodyType.DYNAMIC;
        Body body = box2d.createBody(bd);
        FixtureDef fd = new FixtureDef();
        fd.shape = new CircleShape();
        fd.shape.setRadius(box2d.scalarPixelsToWorld(r));
        fd.friction = .3f;
        fd.restitution = .5f;
        fd.density = 1f;
        body.createFixture(fd);
        this.body = body;
        this.r = r;
    }

    public Vec2 getPosn() {
        return body.getPosition();
    }

    public float getRadius() {
        return r;
    }
}
