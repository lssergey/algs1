import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        for (int a = 0; a < N - 3; a++)
            for (int b = a + 1; b < N - 2; b++)
                for (int c = b + 1; c < N - 1; c++)
                    for (int d = c + 1; d < N; d++) {
                        Point[] pts = {points[a], points[b], points[c], points[d]};
                        double pqSlope = pts[0].slopeTo(pts[1]);
                        double qrSlope = pts[1].slopeTo(pts[2]);
                        double rsSlope = pts[2].slopeTo(pts[3]);

                        if ((pqSlope == qrSlope) && (qrSlope == rsSlope)) {
                            Arrays.sort(pts);
                            pts[0].drawTo(pts[3]);
                            StdOut.println(
                                pts[0]
                                + " -> "
                                + pts[1]
                                + " -> "
                                + pts[2]
                                + " -> "
                                + pts[3]
                            );
                        }
                    }

        StdDraw.show(0);
    }
}
