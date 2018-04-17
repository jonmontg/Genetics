import java.util.LinkedList;

/**
 *
 */
public class Environment {

    LinkedList<Point> food;

    private class Point {

        int x, y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Environment() {
        this.food = new LinkedList<>();
    }
}
