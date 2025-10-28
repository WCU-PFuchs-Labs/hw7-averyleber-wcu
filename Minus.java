/*
 * Code Author: Avery Leber
 * Date: 10/08/2025
 * Purpose: Binary Tree Subtraction
 */
public class Minus extends Binop {
    /**
      * @param left the left value
      * @param right the right value
      * @return the result of adding
      * left to right 
      */

    @Override
    public double eval(double left, double right) {
        return left - right;
    }

    @Override
    public String toString() {
        return "-";
    }
}