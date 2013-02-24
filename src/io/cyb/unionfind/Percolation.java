package io.cyb.unionfind;
/**
 * Monte Carlo percolation simulation.
 *
 * @author Alexandr.Romanyuk@gmail.com
 *
 */
public class Percolation {

    private static final  byte OPEN_SITE = 0;
    private static final byte BLOCKED_SITE = 1;
    
    private WeightedQuickUnionUF wu;
    private int n;
    private int p;
    private int q;

    private byte[][] map;   

    /**
     * Representation of virtual roots.
     *
     * @see Programming Tricks and Common Pitfalls {@link http://goo.gl/VSmev}
     */
    private final int virtualTop;
    private final int virtualBottom;

    /**
     * By convention, the indices i and j are integers between 1 and N,
     * where (1, 1) is the upper-left site Adding extra row/column.
     * Doing this results in cleaner code.
     *
     * @see {@link http://goo.gl/VSmev}
     */
    public Percolation(int n) {
        this.n = n;

        // + 2 for two virtual root's
        wu = new WeightedQuickUnionUF(n * n + 2);
        map = new byte[n + 1][n + 1];

        virtualTop = 0;
        virtualBottom = n * n + 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                map[i][j] = BLOCKED_SITE;
            }
        }
    }

    /**
     * open site (row i, column j) if it is not already.
     *
     * @param i
     * @param j
     */
    public void open(int i, int j) {
        validate(i, j);
        
        map[i][j] = OPEN_SITE;
        p = getComponentId(i, j);

        // up
        if (i > 1 && isOpen(i - 1, j)) {
            q = getComponentId(i - 1, j);
            wu.union(p, q);
        } else if (i == 1) {
        // union with top virtual
            wu.union(virtualTop, p);
        }

        // down
        if (i < n && isOpen(i + 1, j)) {
            q = getComponentId(i + 1, j);
            wu.union(p, q);
        } else if (i == n) {
        // union with bottom virtual
            wu.union(virtualBottom, p);
        }

        // left
        if (j > 1 && isOpen(i, j - 1)) {
            q = getComponentId(i, j - 1);
            wu.union(p, q);
        }

        // right
        if (j < n && isOpen(i, j + 1)) {
            q = getComponentId(i, j + 1);
            wu.union(p, q);
        }
    }

    /**
     * mapping 2d indices to flat array.
     * @see http://en.wikipedia.org/wiki/Row-major_order
     *
     * @param i index
     * @param j index
     * @return offset = row*NUMCOLS + column
     */
    private int getComponentId(int i, int j) {        
        return i * n - n + j;
    }

    /**
     * @param i row
     * @param j column
     * @return is site open?
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return map[i][j] == OPEN_SITE;
    }

    /**
     * @param i row
     * @param j column
     * @return is site full?
     */
    public boolean isFull(int i, int j) {
        validate(i, j);
        
        p = getComponentId(i, j);
        return wu.connected(p, virtualTop);
    }
    
    private void validate(int i, int j) {
        if (i < 1 || i > n  || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * @return does the system percolate?
     */
    public boolean percolates() {
        return wu.connected(virtualTop, virtualBottom);
    }
}