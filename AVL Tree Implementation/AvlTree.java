package oop.ex4.data_structures;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is the complete and tested implementation of an AVL-tree, with duplicates keys are not allowed.
 */
public class AvlTree implements Iterable<Integer>, BinaryTree {

    /**
     * The number of elements in the tree
     */
    private int elementsNumber = 0;
    private final static int INITIAL_HEIGHT = 0;
    private final static int NOT_CONTAIN = -1;
    private final static int NEED_BALANCE = 1;
    private final static int NO_LEFT_CHILD = 0;

    /**
     * Values to find the case of balancing
     */
    private final static int LL_CASE1 = 0;
    private final static int LL_CASE2 = 1;
    private final static int RR_CASE1 = 0;
    private final static int RR_CASE2 = -1;
    private final static int LR_CASE = -1;
    private final static int RL_CASE = 1;

    /**
     * The root of the tree
     */
    private Node root;

    /*------Constructors-----*/

    /**
     * The default constructor
     */
    public AvlTree() {
        root = null;
    }

    /**
     * A constructor that builds a new AVL tree containing all uniques values
     * in the input array
     *
     * @param data the values to add to the tree
     */
    public AvlTree(int[] data) {
        if (data != null) {
            for (int item : data) {
                add(item);
            }
        }
    }

    /**
     * A copy constructor that creates a deep copy of the given AVL Tree. The new tree
     * contains all the values of the given tree, but not necessarily in the same structure.
     *
     * @param tree an AVL Tree
     */
    public AvlTree(AvlTree tree) {
        if (tree != null) {
            Iterator<Integer> myIter = tree.iterator();
            while (myIter.hasNext()) {
                add(myIter.next());
            }
        }
    }


    /**
     * Check whether the tree contains the given input value
     *
     * @param searchVal the value to search for
     * @return the depth of the node ( 0 for the root) with the given value if it was found
     * in the tree , otherwise -1
     */
    public int contains(int searchVal) {
        Node currentNode = root;
        int depthCount = NOT_CONTAIN;
        while (currentNode != null) {
            depthCount++;
            if (searchVal < currentNode.value) {
                currentNode = currentNode.left;
            } else if (searchVal > currentNode.value) {
                currentNode = currentNode.right;
            } else {
                return depthCount;
            }
        }
        return NOT_CONTAIN;
    }

    /**
     * Add a new node with the given value to the tree
     *
     * @param newValue the value of the new node to add
     * @return true if the value to add is not already in the tree and was successfully added,
     * false otherwise
     */
    public boolean add(int newValue) {
        if (contains(newValue) == NOT_CONTAIN) {
            Node currentNode = root;
            Node parentNode = null;

            // add node
            while (currentNode != null) {
                if (newValue < currentNode.value) {
                    parentNode = currentNode;
                    currentNode = currentNode.left;

                } else {
                    parentNode = currentNode;
                    currentNode = currentNode.right;
                }
            }

            Node newNode = new Node(newValue);
            newNode.parent = parentNode;
            elementsNumber++;
            if (root == null) {
                root = newNode;
            }
            // add parent
            if (parentNode != null) {
                if (newValue > parentNode.value) {
                    parentNode.right = newNode;
                } else {
                    parentNode.left = newNode;
                }
            }

            // actualize the height and balancing
            if (newNode != root) {
                currentNode = newNode.parent;
                if (currentNode.right == null || currentNode.left == null) {
                    updateHeightStart(currentNode);
                }
                checkBalanced(newNode.parent);

            }

            return true;
        }

        return false;
    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     *
     * @param toDelete the value to remove from the tree
     * @return true if the given value was found and deleted, false otherwise
     */
    public boolean delete(int toDelete) {
        Node nodeToDelete = findNode(toDelete);
        Node balanceNode = nodeToDelete;

        if (nodeToDelete == null) { // if not contain
            return false;
        }

        //If the node has no sons
        if (nodeToDelete.right == null && nodeToDelete.left == null) {// if it's leaf
            if (nodeToDelete == root) {
                root = null;
            } else if (nodeToDelete.value < nodeToDelete.parent.value) {
                nodeToDelete.parent.left = null;

            } else {
                nodeToDelete.parent.right = null;
            }
        }

        //If the node has only one son
        else if (nodeToDelete.right == null) {

            nodeToDelete.value = nodeToDelete.left.value;
            nodeToDelete.left = null;

        } else if (nodeToDelete.left == null) {

            nodeToDelete.value = nodeToDelete.right.value;
            nodeToDelete.right = null;

        }
        //If the node has two sons
        else {

            Node successor = successor(nodeToDelete);
            balanceNode = successor.parent;
            nodeToDelete.value = successor.value;

            if (successor.parent.value > successor.value) {
                successor.parent.left = successor.right;

            } else {
                successor.parent.right = successor.right;
            }
            if (successor.right != null) {
                successor.right.parent = successor.parent;
            }

        }
        elementsNumber--;
        updateHeightStart(balanceNode);
        checkBalanced(balanceNode);
        return true;
    }

    /**
     * This method is searching for the successor node
     *
     * @param node - the node we search for his successor
     * @return the successor node
     */
    private Node successor(Node node) {
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        return successor;
    }

    /**
     * This method updates the height of the nodes
     *
     * @param node - the starter node, to update the height
     */
    private void updateHeightStart(Node node) {
        while (node!= null){
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            node = node.parent;
        }
    }

    /**
     * This method is giving back the heigt of a node
     *
     * @param node - the node we are searching for the height
     * @return -1 iff the node has no sons, otherwise his height
     */
    private int getHeight(Node node) {
        if (node != null) {
            return node.height;
        } else {
            return NOT_CONTAIN;
        }
    }

    /**
     * This method verifies if the value is on the subtree debuting on the given node
     *
     * @param searchVal - the value of the searching node
     * @return the searching Node, otherwise null
     */
    private Node findNode(int searchVal) {
        Node currentNode = root;
        while (currentNode != null) {
            if (searchVal < currentNode.value) {
                currentNode = currentNode.left;
            } else if (searchVal > currentNode.value) {
                currentNode = currentNode.right;
            } else {
                return currentNode;
            }
        }
        return null;
    }

    /**
     * This method checks if the balance is needed by calculating the difference of height
     * between two different nodes
     *
     * @param node - the node where to check if a balance is needed
     */
    private void checkBalanced(Node node) {
        while (node != null) {
            int difference = getDifference(node);
            if (Math.abs(difference) > NEED_BALANCE) {
                balanced(node, node.parent, difference);
            }
            node = node.parent;
        }
    }


    /**
     * This method balance the Tree in order to have a real AVL Tree
     *
     * @param node       - problematic node (where the balance is needed)
     * @param parentNode - parent node of the problematic node
     * @param difference - the value of the adding node
     */
    private void balanced(Node node, Node parentNode, int difference) {

        Node oldNode = node;
        if (difference > NEED_BALANCE) {
            int differenceChildLeft = getDifference(node.left);
            // Left Left Case
            if (differenceChildLeft == LL_CASE1 || differenceChildLeft == LL_CASE2) {
                node = rightRotate(node);
                updateParent(node, parentNode, oldNode);

            // Left Right Case
            } else if (differenceChildLeft == LR_CASE) {
                node.left = leftRotate(node.left);
                node = rightRotate(node);
                updateParent(node, parentNode, oldNode);
            }
        } else if (difference < NEED_BALANCE) {
            int differenceChildRight = getDifference(node.right);
            // Right Right Case
            if (differenceChildRight == RR_CASE1 || differenceChildRight == RR_CASE2) {
                node = leftRotate(node);
                updateParent(node, parentNode, oldNode);

            // Right Left Case
            } else if (differenceChildRight == RL_CASE) {
                node.right = rightRotate(node.right);
                node = leftRotate(node);
                updateParent(node, parentNode, oldNode);
            }
        }

        if (parentNode == null) { //Update the root in need
            root = node;
        }

        while (node.parent != null) { //Update the height
            updateHeightStart(node.parent);
            node = node.parent;

        }

    }

    /**
     * This method is searching for the difference of heights between a node child
     * @param node - node we check the heights
     * @return the difference of heights
     */
    private int getDifference(Node node) {
        return getHeight(node.left) - getHeight(node.right);
    }


    /**
     * This method updates the parent and the children of the problematic node and the
     * node that replaces it
     *
     * @param node       - the replacing node
     * @param parentNode - the parent of the old node (pb one)
     * @param oldNode    - the problematic node
     */
    private void updateParent(Node node, Node parentNode, Node oldNode) {

        if (parentNode != null && parentNode.left == oldNode) {
            parentNode.left = node;
        } else if (parentNode != null && parentNode.right == oldNode) {
            parentNode.right = node;
        }
        if (node.left != null) {
            node.left.parent = node;
        }
        if (node.right != null) {
            node.right.parent = node;
        }
        node.parent = parentNode;
    }

    /**
     * This method does a right rotation on the AVL Tree.
     *
     * @param node - the problematic node ( in need for a rotation )
     * @return the new Node that we need to change place with the problematic node
     */
    private Node rightRotate(Node node) {
        Node nodeLeft = node.left;
        Node nodeLR = nodeLeft.right;

        // Perform rotation
        nodeLeft.right = node;
        node.parent = nodeLeft;

        node.left = nodeLR;
        if (nodeLR != null) {
            nodeLR.parent = node;
        }

        // Update heights
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        nodeLeft.height = Math.max(getHeight(nodeLeft.left), getHeight(nodeLeft.right)) + 1;

        // Return new root
        return nodeLeft;
    }

    /**
     * This method does a left rotation on the AVL Tree.
     *
     * @param node - the problematic node ( in need for a rotation )
     * @return the new Node that we need to change place with the problematic node
     */
    private Node leftRotate(Node node) {
        Node nodeRight = node.right;
        Node nodeRL = nodeRight.left;

        // Perform rotation
        nodeRight.left = node;
        node.parent=nodeRight;

        node.right = nodeRL;
        if (nodeRL != null) {
            nodeRL.parent=node;
        }

        // Update heights
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        nodeRight.height = Math.max(getHeight(nodeRight.left), getHeight(nodeRight.right)) + 1;

        // Return new root
        return nodeRight;
    }

    /**
     * @return the number of nodes in the tree
     */
    public int size() {
        return elementsNumber;
    }

    /**
     * Calculates the minimum number of nodes in an AVL Tree of height h.
     *
     * @param h the height of the tree ( a non negative number )
     * @return the minimum number of nodes  in an AVL Tree of the given height
     */
    public static int findMinNodes(int h) {
        return (int) (Math.round(((Math.sqrt(5) + 2) / Math.sqrt(5)) * Math.pow((1 + Math.sqrt(5)) / 2, h) - 1));
    }

    /**
     * Calculates the maximum number of nodes in an AVL Tree of height h.
     *
     * @param h the height of the tree ( a non negative number )
     * @return the maximum number of nodes in an AVL Tree of the given height
     */
    public static int findMaxNodes(int h) {
        return (int) Math.pow(2, h + 1) - 1;
    }


    /**
     * @return an iterator for the AVL Tree. The return iterator iterates over the tree nodes
     * in an ascending order, and does NOT implement the remove() method.
     */
    public Iterator<Integer> iterator() {
        return new AVLItr();
    }


    /**
     * This class creates an AVL Tree iterator.
     */
    private class AVLItr implements Iterator<Integer> {

            /** The Array List of nodes we use to keep the nodes we need to use */
            private ArrayList<Node> nodesArray;

            /** The next node in an ascending order */
            private Node nextNode;


            /**
             * The constructor of the AVL Tree Iterator.
             */
            private AVLItr() {
                nodesArray = new ArrayList<>();
                Node currentNode = root;

                while (currentNode != null) { //We keep al the nodes on the left in order to
                    nodesArray.add(currentNode); // sort them in an ascending order
                    currentNode = currentNode.left;
                }
                int index = nodesArray.size();

                if (index != NO_LEFT_CHILD){
                nextNode = nodesArray.get(index - 1);}//The last node is the smallest one
                else{ root = null;
                nextNode = null;
                }
            }

            /**
             * This method checks if there is still a node in the tree.
             * @return false iff no node left in the tree
             */
            public boolean hasNext() {
                return (nextNode != null);
            }


            /**
             * This method is searching for the next node in an ascending order
             * @return the next node value in an ascending order
             */
            public Integer next() {

                if (nextNode == null) { // No node left in the tree
                    throw new NoSuchElementException();
                }

                Node nodeWork = nextNode;
                int valToReturn = nodeWork.value;
                nodesArray.remove(nodeWork); // We remove it because it is the smallest

                if (nodeWork.right != null) {
                    Node node = nodeWork.right;
                    while (node != null) { // Searching new nodes on the left because they are
                        nodesArray.add(node); // smaller than the rights nodes
                        node = node.left;
                    }
                }

                int newLenArray = nodesArray.size();
                if (newLenArray != NO_LEFT_CHILD){
                nextNode = nodesArray.get(newLenArray - 1);}
                else {
                    nextNode = null;
                }

                return valToReturn;
            }

            /**
             * Unsupported operation
             */
            public void remove(){
                throw new UnsupportedOperationException();
            }
    }

    /**
     * This class implements an AVL Tree Node.
     */
    private class Node {

        /**
         * The value of the node, The height of his position in the tree
         */
        private int value, height;

        /**
         * The children and parent of the node
         */
        Node left, right, parent;

        /**
         * The constructor of a node taking in cunt the value it has to take in
         *
         * @param val - the value the Node has to take in
         */
        Node(int val) {
            value = val;
            height = INITIAL_HEIGHT;
            parent = null;

        }
    }


}