/*
 * Code Author: Avery Leber
 * Date: 10/08/2025
 * Purpose: Allows implementations of variables into algebra evaluations.
 */
public class Variable extends Unop {
    private final int index;

    public Variable(int index) {
        this.index = index;
    }

    @Override
    public double eval(double[] values) {
        return values[index];
    }

    @Override
    public String toString() {
        return "X" + index;
    }
}
