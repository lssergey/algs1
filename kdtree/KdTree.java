public class KdTree {

    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    private Node root;
    private int N;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb, rt;
        private boolean orientation;
        private Node parent;

        public Node(Point2D p, boolean orientation, Node parent) {
            this.p = p;
            this.orientation = orientation;
            this.parent = parent;
        }

        public double compare(Node h, Point2D that) {
            if (h.orientation == HORIZONTAL) return that.y() - h.p.y();
            else return that.x() - h.p.x();
        }

        public RectHV getRect() {
            if (rect != null) return rect;
            if (parent == null) {
                rect = new RectHV(0, 0, 1, 1);
                return rect;
            }

            double xmin, ymin, xmax, ymax;

            double cmp = compare(parent, p);

            if (parent.orientation == HORIZONTAL) {
                xmin = parent.getRect().xmin();
                xmax = parent.getRect().xmax();
                if (cmp < 0) {
                    ymin = parent.getRect().ymin();
                    ymax = parent.p.y();
                } else {
                    ymin = parent.p.y();
                    ymax = parent.getRect().ymax();
                }
            } else {
                ymin = parent.getRect().ymin();
                ymax = parent.getRect().ymax();
                if (cmp < 0) {
                    xmin = parent.getRect().xmin();
                    xmax = parent.p.x();
                } else {
                    xmin = parent.p.x();
                    xmax = parent.getRect().xmax();
                }
            }
            rect = new RectHV(xmin, ymin, xmax, ymax);
            return rect;
        }
    }

    private boolean isHorizontal(Node x) {
        if (x == null) return false;
        return (x.orientation == HORIZONTAL);
    }

    public boolean isEmpty() {
        // is the set empty?
        return root == null;
    }

    public int size() {
        // number of points in the set
        return N;
    }

    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)
        root = insert(root, p, VERTICAL, null);
    }

    private Node insert(Node h, Point2D p, boolean orientation, Node parent) {
        if (h == null) {
            N++;
            return new Node(p, orientation, parent);
        }
        if (h.p.equals(p)) return h;

        double cmp = compare(h, p);
        if (cmp < 0) {
            h.lb = insert(
                h.lb,
                p,
                !orientation,
                h
            );
        } else if (cmp >= 0) {
            h.rt = insert(
                h.rt,
                p,
                !orientation,
                h
            );
        }

        return h;
    }

    private double compare(Node h, Point2D that) {
        if (isHorizontal(h)) return that.y() - h.p.y();
        else return that.x() - h.p.x();
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        Node node = root;
        while (node != null) {
            double cmpX = p.x() - node.p.x();
            double cmpY = p.y() - node.p.y();

            if (cmpX == 0 && cmpY == 0) return true;

            double cmp = (node.orientation == HORIZONTAL) ? cmpY : cmpX;
            if (cmp < 0) node = node.lb;
            else if (cmp >= 0) node = node.rt;
        }
        return false;
    }

    private void draw(Node h) {
        if (h == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        h.p.draw();

        if (h.orientation == VERTICAL) {
            double xcoord = h.p.x();
            StdDraw.setPenRadius(.001);
            StdDraw.setPenColor(StdDraw.RED);
            new Point2D(xcoord, h.getRect().ymin()).drawTo(
                    new Point2D(xcoord, h.getRect().ymax()));
        } else {
            double ycoord = h.p.y();
            StdDraw.setPenRadius(.001);
            StdDraw.setPenColor(StdDraw.BLUE);
            new Point2D(h.getRect().xmin(), ycoord).drawTo(
                    new Point2D(h.getRect().xmax(), ycoord));
        }

        draw(h.lb);
        draw(h.rt);
    }

    public void draw() {
        // draw all of the points to standard draw
        draw(root);
    }

    private void range(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null || !x.getRect().intersects(rect)) return;

        if (rect.contains(x.p)) queue.enqueue(x.p);

        range(x.lb, rect, queue);
        range(x.rt, rect, queue);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        Queue<Point2D> queue = new Queue<Point2D>();

        range(root, rect, queue);

        return queue;
    }

    private Point2D nearest(Node h, Point2D closest, Point2D p) {
        if (h == null || h.getRect().distanceSquaredTo(p) > closest.distanceSquaredTo(p))
            return closest;

        double cmp = compare(h, p);

        if (cmp < 0) {
            closest = nearest(h.lb, closest, p);
            closest = nearest(h.rt, closest, p);
        } else {
            closest = nearest(h.rt, closest, p);
            closest = nearest(h.lb, closest, p);
        }

        if (h.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
            closest = h.p;

        return closest;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        if (isEmpty()) return null;
        return nearest(root, root.p, p);
    }

//    public static void main(String[] args) {
//        KdTree tree = new KdTree();
//        StdOut.println(tree.isEmpty());
//        StdOut.println(tree.size());
//
//        Point2D p = new Point2D(.4, .7);
//        tree.insert(p);
//        StdOut.println(tree.isEmpty());
//        StdOut.println(tree.size());
//        StdOut.println(tree.contains(p));
//
//        tree.insert(new Point2D(.2, .3));
//        tree.insert(new Point2D(.8, .1));
//        tree.insert(new Point2D(.5, .9));
//        tree.insert(new Point2D(.6, .0));
//        tree.insert(new Point2D(.1, .5));
//        tree.insert(new Point2D(.0, .6));
//        tree.insert(new Point2D(.9, .7));
//
//        StdOut.println(tree.contains(new Point2D(.6, 0)));
//        StdOut.println(tree.contains(new Point2D(.3, 0)));
//    }
}
