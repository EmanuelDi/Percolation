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

        if (row - 1 < 0) {
            throw new IllegalArgumentException("index out of bounds row chico" + row);
        }
        if (row - 1 > n) {
            throw new IllegalArgumentException("index out of bounds row grande" + row);
        }
        if (col - 1 < 0) {
            throw new IllegalArgumentException("index out of bounds col chico" + col);
        }
        if (col - 1 > n) {
            throw new IllegalArgumentException("index out of bounds col grande" + col);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        validate(row, col);

        if (!isOpen(row, col)) {

            grid[row - 1][col - 1] = true;
            openSites++;
            int siteIndex = (row - 1) * n + (col - 1);

            if (row - 1 == 0) {
                uf.union(siteIndex, n * n);
            }
            if (row - 1 == n - 1) {
                uf.union(siteIndex, n * n + 1);
            }

            if (row - 1 > 0 && isOpen(row, col)) {
                uf.union(siteIndex, ((row - 1) - 1) * n + (col - 1));
            }

            if (row - 1 < n - 1 && isOpen(row + 1, col)) {
                uf.union(siteIndex, ((row - 1) + 1) * n + (col - 1));
            }

            if (col - 1 > 0 && isOpen(row, col - 1)) {
                uf.union(siteIndex, (row - 1) * n + (col - 1) - 1);
            }

            if (col - 1 < n - 1 && isOpen(row, col + 1)) {
                uf.union(siteIndex, (row - 1) * n + (col - 1) + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        row = row - 1;
        col = col - 1;
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        return isOpen(row, col) && uf.find((row - 1) * n + (col - 1)) == uf.find(n * n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(n * n) == uf.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
