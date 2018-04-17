import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import shiffman.box2d.Box2DProcessing;

public class RevolvingConnection {

    RevoluteJoint joint;

    RevolvingConnection(RevoluteJoint joint, Box2DProcessing box2d) {
        this.joint = joint;
    }

    public void toggle() {
        joint.enableMotor(!joint.isMotorEnabled());
    }
    public void setSpeed(float speed) {
        joint.setMotorSpeed(speed);
    }
    public void setTorque(float torque) {
        joint.setMaxMotorTorque(torque);
    }
}
