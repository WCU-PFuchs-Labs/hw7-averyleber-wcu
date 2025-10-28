/*
 * Code Author: Avery Leber
 * 10/28/2025 CSC240
 * Purpose: Encapsulates the Algebra Tree (Node) and implements a crossover() method.
 */
import java.util.ArrayList;
import java.util.Random;

public class GPTree implements Collector {
    private Node root;
    private ArrayList<Node> crossNodes;

    /*
     * Purpose: Stores nodes that have children.
     */
    @Override
    public void collect(Node node) {
        if (!node.isLeaf()) {
            crossNodes.add(node);
        }

    }

       // DO NOT EDIT code below for Homework 8. 
    // If you are doing the challenge mentioned in 
    // the comments above the crossover method
    // then you should create a second crossover
    // method above this comment with a slightly 
    // different name that handles all types
    // of crossover.
    
    
    /**
     * This initializes the crossNodes field and
     * calls the root Node's traverse method on this
     * so that this can collect the Binop Nodes.
     */
    public void traverse() {
        crossNodes = new ArrayList<>();
        root.traverse(this);
    }
    
    /**
     * This returns a String with all of the binop Strings
     * separated by semicolons
     */
    public String getCrossNodes() {
        StringBuilder string = new StringBuilder();
        int lastIndex = crossNodes.size() - 1;
        for(int i = 0; i < lastIndex; ++i) {
            Node node = crossNodes.get(i);
            string.append(node.toString());
            string.append(";");
        }
        string.append(crossNodes.get(lastIndex));
        return string.toString();
    }
   
    
    /**
     * this implements left child to left child
     * and right child to right child crossover.
     * Challenge: additionally implement left to 
     * right child and right to left child crossover.
     */
    public void crossover(GPTree tree, Random rand) {
        // find the points for crossover
        this.traverse();
        tree.traverse();
        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();
        // get the connection points
        Node thisTrunk = crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        
        if(left) {
            thisTrunk.swapLeft(treeTrunk);
            
        } else {
            thisTrunk.swapRight(treeTrunk);
        }
        
    }

    GPTree() { 
        root = null; 
    }    
    
    public GPTree(NodeFactory n, int maxDepth, Random rand)
    {
        try {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
            root = null;
        }
    }
    
    @Override
    public String toString() { 
        return root.toString(); 
    }
    
    public double eval(double[] data) { 
        return root.eval(data); 
    }

}
