import java.util.Random;

public class NodeFactory implements Cloneable {
    private final Binop[] currentOps;
    private final int numIndepVars;

    public NodeFactory(Binop[] binops, int numVars) {
        this.currentOps = binops;
        this.numIndepVars = numVars;
    }
    public Node getOperator(Random rand) throws CloneNotSupportedException {
        int index = rand.nextInt(currentOps.length);
        Binop op;

        try {
            op = (Binop) currentOps[index].clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return new Node(op);
    }

    // Returns a new Node with a terminal (Const or Variable)
    public Node getTerminal(Random rand) {
        // Pick random number in [0, numIndepVars]:
        int r = rand.nextInt(numIndepVars + 1);

        // Return variable:
        if (r < numIndepVars) {
            return new Node(new Variable(r));
        } else {
            // Return Constwith random value in [0,1]:
            return new Node(new Const(rand.nextDouble()));
        }
    }

    // Returns the number of operators:
    public int getNumOps() {
        return currentOps.length;
    }

    public int getNumIndepVars() {
        return numIndepVars;
    }
}
