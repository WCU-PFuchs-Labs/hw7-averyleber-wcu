/**
 * Code Author: Avery Leber
 * CSC 240 10/18/2024
 * Purpose: Tha base class for operations
 */
public class Op implements Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Op can't clone.");
        }
        return o;
    }
}