import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private int openSites;
    private final int n;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.n = n;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        openSites = 0;

    }

    private void validate(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new IllegalArgumentException("The number must be between 0 and " + (n - 1));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        validate(row, col);

        if (!isOpen(row, col)) {

            grid[row][col] = true;
            openSites++;
            int siteIndex = row * n + col;

            if (row == 0){
                uf.union(siteIndex, n * n);
            }
            if (row == n - 1) {
                uf.union(siteIndex, n * n + 1);
                //System.out.println("aja");
            }

            if ( row > 0 && isOpen(row - 1, col)) {
                uf.union(siteIndex, (row -1) * n + col);
            }

            if ( row < n - 1 && isOpen(row + 1, col) ) {
                uf.union(siteIndex, (row + 1) * n + col);
            }

            if ( col > 0 && isOpen(row, col -1) ) {
                uf.union(siteIndex, row * n + col - 1);
            }

            if (col < n - 1 && isOpen(row, col + 1 )) {
                uf.union(siteIndex, row * n + col + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && uf.find(row * n + col) == uf.find(n * n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return openSites; }

    // does the system percolate?
    public boolean percolates() { return uf.find(n * n) == uf.find(n * n + 1); }

    // test client (optional)
    public static void main(String[] args) {


    }
}
