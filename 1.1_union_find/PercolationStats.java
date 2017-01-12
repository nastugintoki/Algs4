import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] t;
    private int T;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){    
        if (n <= 0 || trials <= 0) 
             throw new java.lang.IllegalArgumentException();
        T = trials;
        t = new double[T];
        double ns = n * n;

        for(int i = 0;i < trials; i++){
            Percolation P = new Percolation(n);
            while(!P.percolates()){                
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if(P.isOpen(row, col)) continue;
                P.open(row, col);               
            }
            int open = P.numberOfOpenSites();

            t[i] = open / ns;

        }      
    }   
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(t);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(t);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
      
        
    public static void main(String[] args) {  // test client (described below)

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        StdOut.printf("mean                    = %f%n", ps.mean());
        StdOut.printf("stddev                  = %f%n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f%n", ps.confidenceLo(), ps.confidenceHi());
    }      

}