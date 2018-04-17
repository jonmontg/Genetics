import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import shiffman.box2d.Box2DProcessing;


public class Boundary {
    float x, y;
    float width, height;
    Body body;

    Boundary(float x, float y, float w, float h, Box2DProcessing box2d) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        BodyDef bd = new BodyDef();
        bd.position.set(box2d.coordPixelsToWorld(x, y));
        bd.type = BodyType.STATIC;
        body = box2d.createBody(bd);

        float box2dw = box2d.scalarPixelsToWorld(w/2);
        float box2dh = box2d.scalarPixelsToWorld(h/2);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(box2dw, box2dh);

        body.createFixture(ps,1);
    }

    public Vec2 getPosn() {
        return new Vec2(x, y);
    }
    public Vec2 getDimensions() {
        return new Vec2(width, height);
    }
}
