import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class RevolvingConnection {

    RevoluteJoint joint;

    RevolvingConnection(RevoluteJoint joint, Box2DProcessing box2d) {
        this.joint = joint;
    }

    /**
     * Toggles the motor on and off
     */
    public void toggle() {
        joint.enableMotor(!joint.isMotorEnabled());
    }

    /**
     * Sets the speed to the given value
     * @param speed - float value for new speed
     */
    public void setSpeed(float speed) {
        joint.setMotorSpeed(speed);
    }

    /**
     * Sets the torque to the given value
     * @param torque - float value for new torque
     */
    public void setTorque(float torque) {
        joint.setMaxMotorTorque(torque);
    }

    /**
     * Returns the box2d RevoluteJoint object held by this
     * @return - RevoluteJoint
     */
    public RevoluteJoint getJoint() {return this.joint; }
}
