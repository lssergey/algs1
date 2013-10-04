import java.util.Iterator;

public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        // construct an empty set of points
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return points.isEmpty();
    }

    public int size() {
        // number of points in the set
        return points.size();
    }

    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)
        points.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        return points.contains(p);
    }

    public void draw() {
        // draw all of the points to standard draw
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point)) queue.enqueue(point);
        }
        return queue;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        double min = Double.POSITIVE_INFINITY;
        Point2D minPoint = null;

        for (Point2D point : points) {
            double d = p.distanceTo(point);
            if (d < min) {
                minPoint = point;
                min = d;
            }
        }

        return minPoint;
    }

    public static void main(String[] args) {

    }
}
