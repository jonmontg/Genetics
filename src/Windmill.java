import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import shiffman.box2d.Box2DProcessing;

public class Windmill {
    RevoluteJoint j;
    Box box1;
    Box box2;

    Windmill(float x, float y, Box2DProcessing box2d) {
        box1 = new Box(x, y, 120, 10, 0, box2d);
        box2 = new Box(x, y, 10, 40, (float)3.14/2, box2d);

        RevoluteJointDef jd = new RevoluteJointDef();
        jd.initialize(box1.getBody(), box2.getBody(), box1.getBody().getWorldCenter());

        jd.motorSpeed = (float)6.28;
        jd.maxMotorTorque = 1000000f;
        jd.enableMotor=true;

        j = (RevoluteJoint)box2d.world.createJoint(jd);
    }

    public Box getBox1() {
        return box1;
    }

    public Box getBox2() {
        return box2;
    }

    public void toggle() {
        j.enableMotor(!j.isMotorEnabled());
    }
}
