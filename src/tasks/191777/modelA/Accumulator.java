package net.sf.geographiclib;

/**
 * An accumulator for sums with enhanced precision.
 * <p>
 * This class allows adding many floating-point numbers (doubles or floats) together with twice the
 * normal precision. The effective precision of the sum is approximately doubled.
 * <p>
 * This implementation is based on the algorithm described by J. R. Shewchuk:
 * <a href="https://doi.org/10.1007/PL00009321">Adaptive Precision
 * Floating-Point Arithmetic and Fast Robust Geometric Predicates</a>,
 * Discrete &amp; Computational Geometry 18(3) 305&ndash;363 (1997).
 */
public final class Accumulator {

    private final double s;
    private final double t;

    /**
     * Construct an Accumulator with an initial double value.
     *
     * @param initialValue the initial value of the accumulator.
     */
    public Accumulator(double initialValue) {
        this.s = initialValue;
        this.t = 0.0;
    }
    
    /**
     * Construct an Accumulator with an initial float value. The value is converted to a double.
     *
     * @param initialValue the initial float value of the accumulator.
     */
     public Accumulator(float initialValue) {
        this.s = initialValue;
        this.t = 0.0;
    }


    /**
     * Private constructor for internal use.
     *
     * @param s the primary sum component.
     * @param t the secondary sum component.
     */
    private Accumulator(double s, double t) {
        this.s = s;
        this.t = t;
    }

    /**
     * Return a new Accumulator with the current value.
     *
     * @return a copy of the current Accumulator.
     */
    public Accumulator copy() {
        return new Accumulator(s, t);
    }

    /**
     * Return the current sum held in the accumulator.
     *
     * @return the current sum.
     */
    public double sum() {
        return s;
    }


    /**
     * Return a new Accumulator with the result of adding a double value to the current sum.
     *
     * @param y the value to add.
     * @return a new Accumulator with the updated sum.
     */
    public Accumulator sum(double y) {
        Accumulator a = copy();
        a = a.add(y); // Assignment necessary because add is immutable.
        return a;
    }

     /**
     * Return a new Accumulator with the result of adding a float value to the current sum.
     *
     * @param y the value to add.
     * @return a new Accumulator with the updated sum.
     */
    public Accumulator sum(float y) {
        Accumulator a = copy();
        a = a.add(y);
        return a;
    }

    /**
     * Return a new Accumulator with a negated value.
     *
     * @return a new Accumulator with the negated sum.
     */
    public Accumulator negate() {
        return new Accumulator(-s, -t);
    }

    /**
     * Return a new Accumulator with the given double value added to the sum.
     *
     * @param y the value to add
     * @return new Accumulator with added value.
     */
    public Accumulator add(double y) {
        double u;
        Pair r = GeoMath.sum(y, t);
        y = r.first;
        u = r.second;
        r = GeoMath.sum(y, s);
        double newS = r.first;
        double newT = r.second;

        if (newS == 0) {
            newS = u;
        } else {
            newT += u;
        }
        return new Accumulator(newS, newT);
    }

      /**
     * Return a new Accumulator with the given float value added to the sum.
     *
     * @param y the value to add
     * @return new Accumulator with added value.
     */
    public Accumulator add(float y) {
         return add((double) y); // Delegate to the double version.
    }
}