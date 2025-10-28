/**
 * Code Template Author: David G. Cooper
 * Purpose: A Binary Tree class for Arithmetic evaluation
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
    
    public double eval(double[] values) {
        if (operation instanceof Unop) {
            Unop unop = (Unop) operation;
            double leftVal = (left != null) ? left.eval(values) : 0.0;
            return unop.eval(leftVal);
        } else if (operation instanceof Binop) {
            Binop binop = (Binop) operation;              // <-- explicit cast
            if (left == null || right == null) {
                // Defensive fallback so evaluation never NPEs.
                System.err.println("Warning: Incomplete Binop node at depth " + depth + " op=" + operation);
                double l = (left != null) ? left.eval(values) : 0.0;
                double r = (right != null) ? right.eval(values) : 0.0;
                return binop.eval(l, r);
            }
            double leftVal = left.eval(values);
            double rightVal = right.eval(values);
            return binop.eval(leftVal, rightVal);
        } else {
            System.err.println("Error: operation is not a Unop or a Binop!");
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
     * Purpose: Collect using preorder traversal.
     */
    public void traverse(Collector c) {
        // Collect this:
        c.collect(this);

        // Traverse left child:
        if (left != null) {
            left.traverse(c);
        }

        // Traverse right child:
        if (right != null) {
            right.traverse(c);
        }
    }

    /*
     * Purpose: Swap this left child node with trunk left child node:
     */
    public void swapLeft(Node trunk) {
        if (this.left == null || trunk.left == null) {
            return;
        }

        Node temp = this.left;
        this.left = trunk.left;
        trunk.left = temp;
    }

    /*
     * Purpose: Swap this right child node with trunk right child node.
     */
    public void swapRight(Node trunk) {
        if (this.right == null || trunk.right == null) {
            return;
        }

        Node temp = this.right;
        this.right = trunk.right;
        trunk.right = temp;

    }

    /*
     * Purpose: Return 'true' if operation is a Unop
     * 
     * Context: Checks whether or not an item is a leaf node (a node with no children).
     */
    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }

    @Override
    public String toString() {
        if (operation instanceof Unop) {
            return operation.toString();
        } else if (operation instanceof Binop) {
            String leftStr = (left != null) ? left.toString() : "null";
            String rightStr = (right != null) ? right.toString() : "null";
            return "(" + leftStr + " " + operation.toString() + " " + rightStr + ")";
        } else {
            return "Error: Operation is not a Unop or a Binop!";
        }
    }
}