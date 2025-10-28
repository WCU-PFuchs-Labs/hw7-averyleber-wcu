/*
 * Code Author: Avery Leber
 * 10/28/2025 CSC240
 * Purpose: Purpose: Tests the GPTree class as well as new versions using crossover.
 */
import java.util.*;

public class TestGPTree {
    static int numIndepVars = 3;
    static int maxDepth = 5;
    static Random rand = new Random();

    public static void main(String[] args) {
        double[] data = {3.14, 2.78, 1.0};
        Binop[] ops = {
            new Plus(),
            new Minus(),
            new Mult(),
            new Divide()
        };
        NodeFactory n = new NodeFactory(ops, numIndepVars);

        GPTree gpt1 = new GPTree(n, maxDepth, rand);
        System.out.println(gpt1 + " = " + gpt1.eval(data));

        GPTree gpt2 = new GPTree(n, maxDepth, rand);
        System.out.println(gpt2 + " = " + gpt2.eval(data));

        gpt1.crossover(gpt2, rand);

        System.out.println("After crossover");
        System.out.println(gpt1 + " = " + gpt1.eval(data));
        System.out.println(gpt2 + " = " + gpt2.eval(data));
    }

}
