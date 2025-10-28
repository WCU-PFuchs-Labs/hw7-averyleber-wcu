/**
 * Code Template Author: David G. Cooper
 * Purpose: An operation class representing a constant number
 */
public class Const extends Unop {
    /** the constant value */
    private final double value;
    /**
     * @param d the value to set the constant to.
     */
    public Const(double d) {
        value = d;
    }
    /**
     * @return the value of the constant.
     */
    @Override
    public double eval(double[] values) {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}