import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import java.util.LinkedList;

public class Creature {
    LinkedList<Box> boxes;
    LinkedList<RevolvingConnection> joints;
    Box2DProcessing box2d;



    public Creature(float x, float y, float width, float height, int numberOfSegments, Box2DProcessing box2d) {
        this.box2d = box2d;
        Box box1 = new Box(x-(width/2)-5, y, width, height, 0, box2d);
        Box box2 = new Box(x+(width/2)-5, y, width, height, 0, box2d);
        RevoluteJointDef jd = new RevoluteJointDef();
        Vec2 posn = new Vec2(box1.getBody().getWorldCenter().x+box2d.scalarPixelsToWorld((width/2)),
                box1.getBody().getWorldCenter().y);
        jd.initialize(box1.getBody(), box2.getBody(), posn);
        jd.maxMotorTorque = 500;
        jd.enableMotor=true;

        boxes = new LinkedList<>();
        joints = new LinkedList<>();
        joints.add(new RevolvingConnection((RevoluteJoint)box2d.world.createJoint(jd), box2d));
        boxes.add(box1);
        boxes.add(box2);
        for (int i=2; i<numberOfSegments; i++)
            addBox(width, height);
    }

    private void addBox(float width, float height) {
        Box lastBox = boxes.getLast();
        Vec2 lastPosn = box2d.getBodyPixelCoord(lastBox.getBody());
        Box newBox = new Box(lastPosn.x+(lastBox.getWidth()/2)-5+(width/2), lastPosn.y, width, height, 0, box2d);
        RevoluteJointDef jd = new RevoluteJointDef();

        Vec2 posn = new Vec2(lastBox.getBody().getWorldCenter().x+box2d.scalarPixelsToWorld(lastBox.getWidth()/2),
                lastBox.getBody().getWorldCenter().y);
        jd.initialize(lastBox.getBody(), newBox.getBody(), posn);
        jd.maxMotorTorque = 500;
        jd.enableMotor=true;
        joints.add(new RevolvingConnection((RevoluteJoint)box2d.world.createJoint(jd), box2d));
        boxes.add(newBox);
    }

    public void setJointSpeeds(LinkedList<Float> speeds) {
        for (int i=0; i<speeds.size(); i++)
            joints.get(i).setSpeed(speeds.get(i));
    }

    public void setJointTorques(LinkedList<Float> torques) {
        for (int i=0; i<torques.size(); i++)
            joints.get(i).setTorque(torques.get(i));
    }

    public LinkedList<Box> getBody() {
        return boxes;
    }

    public LinkedList<RevolvingConnection> getJoints() {
        return joints;
    }
}
