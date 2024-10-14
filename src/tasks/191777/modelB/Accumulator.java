package net.sf.geographiclib;

/**
 * An accumulator for sums with improved precision.
 * <p>
 * This class allows adding many numbers together with twice the
 * normal precision, resulting in an effective precision of approximately 32 decimal places.
 * <p>
 * The implementation is based on J. R. Shewchuk's algorithm:
 * <a href="https://doi.org/10.1007/PL00009321">Adaptive Precision
 * Floating-Point Arithmetic and Fast Robust Geometric Predicates</a>,
 * Discrete &amp; Computational Geometry 18(3) 305&ndash;363 (1997).
 */
public final class Accumulator<T extends Number> {

    private final double sum;
    private final double correction;

    /**
     * Constructs an Accumulator with an initial value.
     *
     * @param initialValue the initial value of the accumulator.
     */
    public Accumulator(final T initialValue) {
        sum = initialValue.doubleValue();
        correction = 0.0;
    }

    /**
     * Constructs a copy of another Accumulator.
     *
     * @param other the Accumulator to copy.
     */
    public Accumulator(final Accumulator<T> other) {
        this(other.sum, other.correction);
    }

    private Accumulator(final double sum, final double correction) {
        this.sum = sum;
        this.correction = correction;
    }

    /**
     * Returns the current sum.
     *
     * @return the accumulated sum.
     */
    public double sum() {
        return sum;
    }

    /**
     * Adds a value to the accumulator.
     *
     * @param value the value to add.
     * @return a new Accumulator with the added value.
     */
    public Accumulator<T> add(final T value) {
        double y = value.doubleValue();
        double u;

        Pair r = GeoMath.sum(y, correction);
        y = r.first;
        u = r.second;

        r = GeoMath.sum(y, sum);
        double newSum = r.first;
        double newCorrection = r.second;

        if (newSum == 0) {
            newSum = u;
            newCorrection = 0;
        } else {
            newCorrection += u;
        }
        return new Accumulator<>(newSum, newCorrection);
    }

    /**
     * Negates the accumulator.
     *
     * @return a new Accumulator with the negated value.
     */
    public Accumulator<T> negate() {
        return new Accumulator<>(-sum, -correction);
    }
}