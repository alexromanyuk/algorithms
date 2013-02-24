package io.cyb.unionfind;
/**
 * Percolation Statistics.
 *
 * @author Alexandr.Romanyuk@gmail.com
 */
public class PercolationStats {

    private static final double STDDEV_CONST = 1.96;

    private Percolation pc;
    private int[] openSites;

    /**
     * perform T independent computational experiments on an N-by-N grid.
     *
     * @param n # of elements
     * @param times # of experiments
     */
    public PercolationStats(final int n, final int times) {
        if (n <= 0 || times <= 0) {
            throw new IllegalArgumentException(
                    "N,T should be greater than zero");
        }

        openSites = new int[times];

        for (int experiment = 0; experiment < times; experiment++) {
            openSites[experiment] = 0;
            pc = new Percolation(n);
            do {
                openSites[experiment]++;
                pc.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            } while (!pc.percolates());
            openSites[experiment] = openSites[experiment];
        }
    }

    /**
     * @return sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(openSites);
    }

    /**
     * @return sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(openSites);
    }

    /**
     * @param t # of experiments
     * @return 95% confidence interval.
     */
    private double[] confInterval(final int t) {
        double[] ci = new double[2];
        double mean = mean();
        double stddev = stddev();
        double st = Math.sqrt(t);

        ci[0] = mean - (STDDEV_CONST * stddev) / st;
        ci[1] = mean + (STDDEV_CONST * stddev) / st;
        return ci;
    }

    /**
     * test client, described below.
     *
     * @param args 1)# N of elements, 2) # T of experiments
     */
    public static void main(final String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        double[] ci = stats.confInterval(t);

        StdOut.println(String.format("mean                    = %f",
                stats.mean()));
        StdOut.println(String.format("stddev                  = %f",
                stats.stddev()));
        StdOut.println(String
                .format("95%% conf interval = %f, %f", ci[0], ci[1]));
    }
}
