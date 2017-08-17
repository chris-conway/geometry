package model;

import model.MyPoint2D;

        import java.util.Arrays;
import java.util.Comparator;

/**
 *  The {@code model.ClosestPair} data type computes a closest pair of points
 *  in a set of <em>n</em> points in the plane and provides accessor methods
 *  for getting the closest pair of points and the distance between them.
 *  The distance between two points is their Euclidean distance.
 *  <p>
 *  This implementation uses a divide-and-conquer algorithm.
 *  It runs in O(<em>n</em> log <em>n</em>) time in the worst case and uses
 *  O(<em>n</em>) extra space.
 *  <p>
 *  See also {}.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/99hull">Section 9.9</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class ClosestPair {

    // closest pair of points and their Euclidean distance
    private MyPoint2D best1, best2;
    private double bestDistance = Double.POSITIVE_INFINITY;

    /**
     * Computes the closest pair of points in the specified array of points.
     *
     * @param points the array of points
     * @throws NullPointerException if {@code points} is {@code null} or if any
     *                              entry in {@code points[]} is {@code null}
     */
    public ClosestPair(MyPoint2D[] points) {
        int n = points.length;
        if (n <= 1) return;

        // sort by x-coordinate (breaking ties by y-coordinate)
        MyPoint2D[] pointsByX = new MyPoint2D[n];
        for (int i = 0; i < n; i++)
            pointsByX[i] = points[i];

        Arrays.sort(pointsByX, MyPoint2D.X_ORDER);

        // check for coincident points
        for (int i = 0; i < n - 1; i++) {
            if (pointsByX[i].equals(pointsByX[i + 1])) {
                bestDistance = 0.0;
                best1 = pointsByX[i];
                best2 = pointsByX[i + 1];
                return;
            }
        }

        // sort by y-coordinate (but not yet sorted)
        MyPoint2D[] pointsByY = new MyPoint2D[n];
        for (int i = 0; i < n; i++)
            pointsByY[i] = pointsByX[i];

        // auxiliary array
        MyPoint2D[] aux = new MyPoint2D[n];

        closest(pointsByX, pointsByY, aux, 0, n - 1);
    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<MyPoint2D> {
        public int compare(MyPoint2D p, MyPoint2D q) {
            if (p.x < q.x) return -1;
            if (p.x > q.x) return +1;
            return 0;
        }
    }

    // find closest pair of points in pointsByX[lo..hi]
    // precondition:  pointsByX[lo..hi] and pointsByY[lo..hi] are the same sequence of points
    // precondition:  pointsByX[lo..hi] sorted by x-coordinate
    // postcondition: pointsByY[lo..hi] sorted by y-coordinate
    private double closest(MyPoint2D[] pointsByX, MyPoint2D[] pointsByY, MyPoint2D[] aux, int lo, int hi) {
        if (hi <= lo) return Double.POSITIVE_INFINITY;

        int mid = lo + (hi - lo) / 2;
        MyPoint2D median = pointsByX[mid];

        // compute closest pair with both endpoints in left subarray or both in right subarray
        double delta1 = closest(pointsByX, pointsByY, aux, lo, mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid + 1, hi);
        double delta = Math.min(delta1, delta2);

        // merge back so that pointsByY[lo..hi] are sorted by y-coordinate
        merge(pointsByY, aux, lo, mid, hi);

        // aux[0..m-1] = sequence of points closer than delta, sorted by y-coordinate
        int m = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pointsByY[i].x() - median.x()) < delta)
                aux[m++] = pointsByY[i];
        }

        // compare each point to its neighbors with y-coordinate closer than delta
        for (int i = 0; i < m; i++) {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i + 1; (j < m) && (aux[j].y() - aux[i].y() < delta); j++) {
                double distance = aux[i].distanceTo(aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < bestDistance) {
                        bestDistance = delta;
                        best1 = aux[i];
                        best2 = aux[j];
                        // StdOut.println("better distance = " + delta + " from " + best1 + " to " + best2);
                    }
                }
            }
        }
        return delta;
    }

    /**
     * Returns one of the points in the closest pair of points.
     *
     * @return one of the two points in the closest pair of points;
     * {@code null} if no such point (because there are fewer than 2 points)
     */
    public MyPoint2D either() {
        return best1;
    }

    /**
     * Returns the other point in the closest pair of points.
     *
     * @return the other point in the closest pair of points
     * {@code null} if no such point (because there are fewer than 2 points)
     */
    public MyPoint2D other() {
        return best2;
    }

    /**
     * Returns the Eucliden distance between the closest pair of points.
     *
     * @return the Euclidean distance between the closest pair of points
     * {@code Double.POSITIVE_INFINITY} if no such pair of points
     * exist (because there are fewer than 2 points)
     */
    public double distance() {
        return bestDistance;
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

}
