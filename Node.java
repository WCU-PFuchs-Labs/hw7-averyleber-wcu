/**
 * Code Author: Avery Leber
 * CSC240 10/28/2025
 * Purpose: Randomly generated expression trees of various sizes and shapes.
 */
import java.util.Random;

public class Node implements Cloneable {
    private Node left;
    private Node right;
    private  Op operation;
    protected int depth;

    public Node(Binop operation,Node left, Node right) {
        depth = 0;
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public Node(Unop operation) {
        depth = 0;
        this.operation = operation;
    }

    public Node(Binop operation) {
        depth = 0;
        this.operation = operation;
    }
    
    public double eval(double[] values) { //Changed method signature to double eval(double[] values)
        if (operation instanceof Unop unop) {
              return unop.eval(values);
        } else if (operation instanceof Binop binop) {
              return binop.eval(left.eval(values), right.eval(values));
        } else {
              System.err.println("Error operation is not a Unop or a Binop!");
              return 0.0;
        }
    }

    // Creates an exact copy of an object
    // Throws an exception of the object's class does not implement the Cloneable interface
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Op can't clone.");
            throw e;
        }
        
        Node b = (Node) o;
        if (left != null) {
            b.left = (Node) left.clone();
        }

        if (right != null) {
            b.right = (Node) right.clone();
        }

        if (operation != null) {
            b.operation = (Op) operation.clone();
        }

        return o;
    }

    public void addRandomKids(NodeFactory nf, int maxDepth, Random rand) throws CloneNotSupportedException {
        // Base Case: stops growth and attaches terminals:
        if (this.depth == maxDepth) {
            this.left = nf.getTerminal(rand);
            this.left.depth = this.depth + 1;

            this.right = nf.getTerminal(rand);
            this.right.depth = this.depth + 1;
            return;
        }
        
        // Otherwise, randomly choose operator or terminal for LEFT:
        int leftChoice = rand.nextInt(nf.getNumOps() + nf.getNumIndepVars() + 1);
        if (leftChoice < nf.getNumOps()) {
            this.left = nf.getOperator(rand);
            this.left.depth = this.depth + 1;
            this.left.addRandomKids(nf, maxDepth, rand);
        } else {
            this.left = nf.getTerminal(rand);
            this.left.depth = this.depth + 1;
        }

        // Otherwise, randomly choose operator or terminal for RIGHT:
        int rightChoice = rand.nextInt(nf.getNumOps() + nf.getNumIndepVars() + 1);
        if (rightChoice < nf.getNumOps()) {
            this.right = nf.getOperator(rand);
            this.right.depth = this.depth + 1;
            this.right.addRandomKids(nf, maxDepth, rand);
        } else {
            this.right = nf.getTerminal(rand);
            this.right.depth = this.depth + 1;
        }
    }

    /*
     * Purpose: Recursively gives nodes in the binary tree to the Collector interface to "collect" them.
     * This allows the creation of potential binary nodes that will be held be GPTree.java to be randomly selected at "crossover" points.
     */
    public void traverse(Collector c) {
        c.collect(this);

        if (this.left != null) {
            left.traverse(c);
        }

        if (this.right != null) {
            right.traverse(c);
        }
    }

    /*
     * Purpose: Determine if a node has no children.
     */
    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }

    
    public void swapLeft(Node trunk) {
        Node temp = this.left;
        this.left = trunk.left;
        trunk.left = temp;
    }

    public void swapRight(Node trunk) {
        Node temp = this.right;
        this.right = trunk.right;
        trunk.right = temp;
    }


    @Override
    public String toString() {
        if (operation instanceof Unop) {
            return operation.toString();
        } else if (operation instanceof Binop) {
            return "(" + left.toString() + " " + operation.toString() + " " + right.toString() + ")";
        } else {
            return "Error: Operation is not a Unop or a Binop!";
        }
    }
}
