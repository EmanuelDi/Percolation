
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0 ) {
            throw new IllegalArgumentException("N and trial must be greater than 0");
        }

        thresholds = new double[trials];

        for (int i = 0; i < thresholds.length; i++) {
            Percolation percolation = new Percolation(n);

            while ( !percolation.percolates() ) {
                int row = StdRandom.uniformInt(0, n );
                int col = StdRandom.uniformInt(0, n );
                percolation.open(row, col);
            }
            thresholds[i] = (double) percolation.numberOfOpenSites() / n * n;
        }
    }

    // sample mean of percolation threshold
    public double mean() { return StdStats.mean(thresholds); }

    // sample standard deviation of percolation threshold
    public double stddev() { return StdStats.stddev(thresholds); }

    // low endpoint of 95% confidence interval
    public double confidenceLo() { return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length); }

    // high endpoint of 95% confidence interval
    public double confidenceHi() { return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length); }

    // test client (see below)
    public static void main(String[] args) {

        int n = StdIn.readInt();
        int trials = StdIn.readInt();

        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }

}
