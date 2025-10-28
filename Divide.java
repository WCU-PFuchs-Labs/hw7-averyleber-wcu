/*
 * Code Author: Avery Leber
 * Date: 10/08/2025
 * Purpose: Binary Tree Division
 */
public class Divide extends Binop {
    /**
      * @param left the left value
      * @param right the right value
      * @return the result of dividing
      * left to right 
      */
    @Override
    public double eval(double left, double right) {
        if (Math.abs(right) < 0.0001) {
            return 1.0; // Avoids infinity
        }
        
        return left / right;
    }

    @Override
    public String toString() {
        return "/";
    }
}
