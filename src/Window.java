import org.jbox2d.common.Vec2;
import java.util.LinkedList;

public interface Window {
    LinkedList<Boundary> getBoundaries();
    Vec2 getGoal();
    Vec2 getStartPosn();
}
