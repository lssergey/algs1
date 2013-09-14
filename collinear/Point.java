/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
        	return Double.NEGATIVE_INFINITY;
        }
        if (this.y == that.y) {
        	return +0;
        } else if (this.x == that.x) {
        	return Double.POSITIVE_INFINITY;
        } else {
            Double yd = ((double)that.y) - this.y;
            Double xd = ((double)that.x) - this.x;
        	return yd / xd;
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y > that.y) {
        	return 1;
        } else if (this.y < that.y) {
    		return -1;
        } else {
        	if (this.x > that.x) {
        		return 1;
        	} else if (this.x < that.x) {
        		return -1;
        	} else {
        		return 0;
        	}
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            Double slope1 = slopeTo(p1);
            Double slope2 = slopeTo(p2);

            Double diff = slope1 - slope2;

            if (diff < 0) return -1;
            else if (diff > 0) return 1;
            else return 0;
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}