public class Brute {
	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] points = new Point[N];

		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		for (int a = 0; a < N - 3; a++)
			for (int b = a + 1; b < N - 2; b++)
				for (int c = b + 1; c < N - 1; c++)
					for (int d = c + 1; d < N; d++) {
						Point p = points[a];
						Point q = points[b];
						Point r = points[c];
						Point s = points[d];

						Double pqSlope = p.slopeTo(q);
						Double qrSlope = q.slopeTo(r);
						Double rsSlope = r.slopeTo(s);

						if (pqSlope.equals(qrSlope) && qrSlope.equals(rsSlope)) {
							StdOut.print(p);
							StdOut.print(" -> ");
							StdOut.print(q);
							StdOut.print(" -> ");
							StdOut.print(r);
							StdOut.print(" -> ");
							StdOut.println(s);
						}
					}
	}
}