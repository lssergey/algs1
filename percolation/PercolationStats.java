public class PercolationStats {
    
    private double[] fractions;
    private int T;
    
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        
        this.T = T;
        
        int cells = N * N;
        int count, x, y;
        
        fractions = new double[T];
        
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            
            count = 0;
            
            while (!p.percolates()) {
                x = StdRandom.uniform(N) + 1;
                y = StdRandom.uniform(N) + 1;
                
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    count++;
                }
            }
            fractions[i] = (double) count / cells;
        }
    }
    
    public double mean() {
        return StdStats.mean(fractions);
    }
    
    public double stddev() {
        return StdStats.stddev(fractions);        
    }
    
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
    
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats ps = new PercolationStats(N, T);
        
        StdOut.print("mean                    = ");
        StdOut.println(ps.mean());
        
        StdOut.print("stddev                  = ");
        StdOut.println(ps.stddev());
        
        StdOut.print("95% confidence interval = ");
        StdOut.println(ps.confidenceLo() + ", " + ps.confidenceHi());
        
        return;        
    }
}