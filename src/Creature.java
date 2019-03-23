import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import java.util.LinkedList;

public class Creature {
    LinkedList<Box> boxes;
    LinkedList<RevolvingConnection> joints;
    Box2DProcessing box2d;
    NeuralNetwork brain;
    float goalx, goaly;
    float closestDistance;

    public Creature(float x, float y, float width, float height, int numberOfSegments, int[] brainsize, Box2DProcessing box2d) {
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
        brain = new NeuralNetwork(2*joints.size(), brainsize, joints.size());
    }

    public Creature(float x, float y, float[] dims, int[] brainsize, Box2DProcessing box2d) {
        this.box2d = box2d;
        float x1 = x - dims[0]/2+2;
        float x2 = x + dims[2]/2-2;
        Box box1 = new Box(x1, y, dims[0], dims[1], 0, box2d);
        Box box2 = new Box(x2, y, dims[2], dims[3], 0, box2d);
        Vec2 posn = new Vec2(box1.getBody().getWorldCenter().x+box2d.scalarPixelsToWorld(dims[0]/2), box1.getBody().getWorldCenter().y);
        RevoluteJointDef jd = new RevoluteJointDef();
        jd.initialize(box1.getBody(), box2.getBody(), posn);
        jd.maxMotorTorque = 1000;
        jd.enableMotor = true;
        boxes = new LinkedList<>();
        joints = new LinkedList<>();
        joints.add(new RevolvingConnection((RevoluteJoint)box2d.world.createJoint(jd), box2d));
        boxes.add(box1);
        boxes.add(box2);
        for (int i=4; i<dims.length; i+=2)
            addBox(dims[i], dims[i+1]);
        brain = new NeuralNetwork(2*joints.size(), brainsize, joints.size());
    }

    /**
     * Sets the creature's goal for fitness evaluation
     * @param x
     * @param y
     */
    public void setGoal(float x, float y) {
        goalx = x;
        goaly = y;
        Vec2 currPosn = boxes.getFirst().getPixelPosn();
        closestDistance = (float)Math.sqrt((currPosn.x)*(currPosn.x)+(currPosn.y)*(currPosn.y));
    }

    /**
     * Updates the creature's position and sets new segment speeds according to brain state
     */
    public void update() {
        Vec2 currPosn = boxes.getFirst().getPixelPosn();
        closestDistance = Math.min((float)Math.sqrt((currPosn.x)*(currPosn.x)+(currPosn.y)*(currPosn.y)), closestDistance);
        double[] outputs = brain.predict(nnInputs());
        LinkedList<Float> newSpeeds = new LinkedList<>();
        for (int i=0; i<joints.size(); i++)
            newSpeeds.add((float)(outputs[i]*(Math.PI)));
        setJointSpeeds(newSpeeds);
    }

    /**
     * Creates a new segment and attaches it to the last segment added with a revolute joint
     * @param width: width of new segment
     * @param height: height of new segment
     */
    private void addBox(float width, float height) {
        Box lastBox = boxes.getLast();
        Vec2 lastPosn = box2d.getBodyPixelCoord(lastBox.getBody());
        Box newBox = new Box(lastPosn.x+(lastBox.getWidth()/2)-5+(width/2), lastPosn.y, width, height, 0, box2d);
        RevoluteJointDef jd = new RevoluteJointDef();

        Vec2 posn = new Vec2(lastBox.getBody().getWorldCenter().x+box2d.scalarPixelsToWorld(lastBox.getWidth()/2),
                lastBox.getBody().getWorldCenter().y);
        jd.initialize(lastBox.getBody(), newBox.getBody(), posn);
        jd.maxMotorTorque = 700;
        jd.enableMotor=true;
        joints.add(new RevolvingConnection((RevoluteJoint)box2d.world.createJoint(jd), box2d));
        boxes.add(newBox);
    }

    /**
     * Sets joint speeds to values given by argument
     * @param speeds: LinkedList of speeds
     */
    public void setJointSpeeds(LinkedList<Float> speeds) {
        for (int i=0; i<speeds.size(); i++)
            joints.get(i).setSpeed(speeds.get(i));
    }

    /**
     * Sets joint torques to values given by argument
     * @param torques: LinkedList of torques
     */
    public void setJointTorques(LinkedList<Float> torques) {
        for (int i=0; i<torques.size(); i++)
            joints.get(i).setTorque(torques.get(i));
    }

    /**
     * Returns the list of Boxes that make up the creature's segments
     * @return - boxes
     */
    public LinkedList<Box> getBody() {
        return boxes;
    }

    /**
     * Returns the list of RevolvingConnection joints that make up the creature's joints
     * @return - joints
     */
    public LinkedList<RevolvingConnection> getJoints() {
        return joints;
    }

    /**
     * Returns a list of Floats representing the speeds of the joints
     * @return - speeds
     */
    public LinkedList<Float> getSpeeds() {
        LinkedList<Float> speeds = new LinkedList<>();
        for (RevolvingConnection j : joints)
            speeds.add(j.getJoint().getJointSpeed());
        return speeds;
    }

    /**
     * Returns the list of Floats representing the relative angles of joints
     * @return - angles
     */
    public LinkedList<Float> getAngles() {
        LinkedList<Float> angles = new LinkedList<>();
        for (RevolvingConnection j : joints)
            angles.add(j.getJoint().getReferenceAngle());
        return angles;
    }

    /**
     * Returns an array of inputs for the neural network
     * @return - double[] brain inputs
     */
    public double[] nnInputs() {
        double[] input = new double[2*joints.size()];
        for (int i=0; i<joints.size(); i++)
            input[i] = joints.get(i).joint.getJointSpeed();
        for (int i=0; i<joints.size(); i++)
            input[i+joints.size()] = joints.get(i).joint.getJointAngle();
        return input;
    }

    public NeuralNetwork getBrain() {
        return brain;
    }
}
