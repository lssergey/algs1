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

        public Node(Point2D p, RectHV rect, boolean orientation) {
            this.p = p;
            this.rect = rect;
            this.orientation = orientation;
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
        root = insert(root, p, new RectHV(0, 0, 1, 1), VERTICAL);
        N++;
    }

    private Node insert(Node h, Point2D p, RectHV rect, boolean orientation) {
        if (h == null) return new Node(p, rect, orientation);
        if (h.p.equals(p)) return h;

        double cmp = compare(h, p);
        if (cmp < 0) h.lb = insert(h.lb, p, getRectPart(rect, h.p, h.orientation, cmp), !orientation);
        else if (cmp > 0) h.rt = insert(h.rt, p, getRectPart(rect, h.p, h.orientation, cmp), !orientation);

        return h;
    }

    private double compare(Node h, Point2D that) {
        if (isHorizontal(h)) return that.y() - h.p.y();
        else return that.x() - h.p.x();
    }

    private RectHV getRectPart(RectHV rect, Point2D p, boolean orientation, double cmp) {
        double xmin, ymin, xmax, ymax;

        if (orientation == HORIZONTAL) {
            xmin = rect.xmin();
            xmax = rect.xmax();
            if (cmp < 0) {
                ymin = rect.ymin();
                ymax = p.y();
            } else {
                ymin = p.y();
                ymax = rect.ymax();
            }
        } else {
            ymin = rect.ymin();
            ymax = rect.ymax();
            if (cmp < 0) {
                xmin = rect.xmin();
                xmax = p.x();
            } else {
                xmin = p.x();
                xmax = rect.xmax();
            }
        }
        return new RectHV(xmin, ymin, xmax, ymax);
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        Node node = root;
        while (node != null) {
            double cmp = compare(node, p);
            if (cmp < 0) node = node.lb;
            else if (cmp > 0) node = node.rt;
            else return true;
        }
        return false;
    }

    private void draw(Node x) {
        if (x == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        x.p.draw();

        if (x.orientation == VERTICAL) {
            double xcoord = x.p.x();
            StdDraw.setPenColor(StdDraw.RED);
            new Point2D(xcoord, x.rect.ymin()).drawTo(new Point2D(xcoord, x.rect.ymax()));
        } else {
            double ycoord = x.p.y();
            StdDraw.setPenColor(StdDraw.BLUE);
            new Point2D(x.rect.xmin(), ycoord).drawTo(new Point2D(x.rect.xmax(), ycoord));
        }

        draw(x.lb);
        draw(x.rt);
    }

    public void draw() {
        // draw all of the points to standard draw
        draw(root);
    }

    private void range(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null || !x.rect.intersects(rect)) return;

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
//
//    public Point2D nearest(Point2D p) {
//        // a nearest neighbor in the set to p; null if set is empty
//    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        StdOut.println(tree.isEmpty());
        StdOut.println(tree.size());

        Point2D p = new Point2D(.4, .7);
        tree.insert(p);
        StdOut.println(tree.isEmpty());
        StdOut.println(tree.size());
        StdOut.println(tree.contains(p));

        tree.insert(new Point2D(.2, .3));
        tree.insert(new Point2D(.8, .1));
        tree.insert(new Point2D(.5, .9));
        tree.insert(new Point2D(.6, .0));
        tree.insert(new Point2D(.1, .5));
        tree.insert(new Point2D(.0, .6));
        tree.insert(new Point2D(.9, .7));

        for (Point2D point : tree.range(new RectHV(.2, .3, .8, .8))) {
            StdOut.println(point);
        }
    }
}
