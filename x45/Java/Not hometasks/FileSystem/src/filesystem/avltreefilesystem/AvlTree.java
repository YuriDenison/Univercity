package filesystem.avltreefilesystem;


import java.io.*;

/**
 * @author Yuri Denison
 */
public class AvlTree implements Externalizable {
    private Node root;                // root of the tree
    private AvlTreeSerializer serializer;

    public void setSerializer(AvlTreeSerializer serializer) {
        this.serializer = serializer;
    }

    /**
     * Default constructor to prevent errors in reconstructing tree
     */
    public AvlTree() {
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(root);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.root = (Node) in.readObject();
    }

    public AvlTree(AvlTreeSerializer serializer) {
        this.serializer = serializer;
    }

    /**
     * Find the key in tree
     *
     * @param key search key
     * @return Node with equals key or null if tree does not contains this key
     */
    public Node find(int key) {
        // Walking down tree until end or until key was found.
        Node c = root;
        while (c != null) {
            // Value match -> End of search.
            if (c.getKey() == key) {
                return c;

                // Value smaller than current element -> Go left.
            } else if (key < c.getKey()) {
                c = c.getLeft();
                // Value bigger than current element -> Go right.
            } else {
                c = c.getRight();
            }
        }
        return null;
    }

    /**
     * Size of the tree
     *
     * @return size
     */
    public int size() {
        return (root == null) ? 0 : size(root, 0);
    }

    private int size(Node node, int curSize) {
        curSize++;
        if (node.getLeft() != null) {
            curSize += size(node.getLeft(), 0);
        }
        if (node.getRight() != null) {
            curSize += size(node.getRight(), 0);
        }
        return curSize;
    }

    /**
     * Adds a new node to this tree.
     *
     * @param node added node
     */
    public void insert(Node node) {
        insert(node.getKey(), node.getLastBlockLength(), node.getPositions());
    }

    /**
     * Adds a new key to this tree.
     *
     * @param key        The new key.
     * @param lastLength the length of data array in last content block
     * @param positions  content indexes
     */
    public void insert(int key, int lastLength, int[] positions) {
        // No root yet exists -> Insert as root and return.
        if (root == null) {
            new Node(key, lastLength, positions).setRoot();
            return;
        }

        // Find right path for insertion.
        Node c = root;
        while (true) {

            // Element is smaller than current node's key -> Continue at right.
            if (key < c.getKey()) {
                if (c.getLeft() == null) {
                    c.setLeft(new Node(key, lastLength, positions));
                    up(c);
                    serializer.writeAvlTree(this);
                    return;
                } else
                    c = c.getLeft();

                // Element is bigger than current node's key -> Continue at right.
            } else if (key > c.getKey()) {
                if (c.getRight() == null) {
                    c.setRight(new Node(key, lastLength, positions));
                    up(c);
                    serializer.writeAvlTree(this);
                    return;
                } else
                    c = c.getRight();
                // Element is already included -> Exception.
            } else {
                c.setLastBlockLength(lastLength);
                c.setPositions(positions);
                return;
            }
        }
    }

    /**
     * Removes an element from the tree.
     *
     * @param key The key.
     * @return deleted node
     */
    public Node remove(int key) {

        Node k = findNode(root, key);
        if (k == null) {
            return null;
        }
        Node kParent = k.getParent();

        // Delete node that is a leaf.
        if (k.isLeaf()) {
            if (k == root)
                root = null;
            else
                k.getParent().removeSon(k);
            // Delete node that us a single son.
        } else if (k.isSingleSon()) {
            if (k == root)
                k.onlySon().setRoot();
            else
                k.getParent().replace(k, k.onlySon());
            // Delete inner node.
        } else {

            // Check if it is more efficient to exchange node by another left or right side node
            // If uncertain, choose right side (and thus bigger element) for finding a replacement.
            boolean replaceByLeft = k.getLeft() != null &&
                    (k.getRight() == null || k.getLeft().getHeight() > k.getRight().getHeight());

            Node s;
            if (replaceByLeft)
                s = findNextSmallerNode(k);
            else
                s = findNextBiggerNode(k);

            // Remove node first that is to be used as a replacement.
            remove(s.getKey());

            // Set new node in place of the removed node.
            if (k == root)
                s.setRoot();
            else
                k.getParent().replace(k, s);

            // Fix father - son relationships.
            s.setLeft(k.getLeft());
            s.setRight(k.getRight());
            s.update();
        }
        if (kParent != null)
            up(kParent);

        serializer.writeAvlTree(this);
        return k;
    }

    /**
     * Finds the next bigger valued node for a starting node.
     *
     * @param n The starting node.
     * @return The next bigger node.
     */
    private Node findNextBiggerNode(Node n) {
        if (n.getRight() == null) {
            return null;
        }
        Node c = n.getRight();
        while (c.getLeft() != null) {
            c = c.getLeft();
        }
        return c;
    }

    /**
     * Finds the next smaller valued node for a starting node.
     *
     * @param n The starting node.
     * @return The next smaller node.
     */
    private Node findNextSmallerNode(Node n) {
        if (n.getLeft() == null) {
            return null;
        }
        Node c = n.getLeft();
        while (c.getRight() != null) {
            c = c.getRight();
        }
        return c;
    }

    /**
     * Finds a node of a certain key.
     *
     * @param n   The current node.
     * @param key The key of interest.
     * @return The node or <code>null</code> if no such node exists.
     */
    private Node findNode(Node n, int key) {
        if (n == null)
            return null;

        if (n.getKey() == key)
            return n;
        else if (key < n.getKey())
            return findNode(n.getLeft(), key);
        else
            return findNode(n.getRight(), key);

    }

    /**
     * Walks up from the most recent deleted item to the tree root in order to
     * inform all entities
     *
     * @param node The current node that has to be revalidated after a tree alteration.
     */
    private void up(Node node) {
        Node next = node.getParent();
        int height = node.getHeight();

        node.update();

        // Check if tree node is out of balance.
        if (Math.abs(node.getBalance()) >= 2)
            node = rotate(node);

        // Update parent nodes only if the tree root was not yet reached
        // and if the height of the current node was altered.
        if (next != null && height != node.getHeight())
            up(next);
    }

    /**
     * Performs a rotation. Will only work correctly if rotation is actually required.
     *
     * @param a The rotation anchor.
     * @return The node's replacement node.
     */
    private Node rotate(Node a) {
        // Correct for right side overweight.
        if (a.getBalance() == 2)
            // Correct for right-left side overweight.
            if (a.getRight().getBalance() == -1)
                return rotateLeftDouble(a);
                // Correct for right-right side overweight.
            else //if(a.right.balance == 1 || /* delete only: */ a.right.balance == 0)
                return rotateLeft(a);
            // Correct for left side overweight
        else //if(a.balance == -2)
            // Correct for left-right side overweight.
            if (a.getLeft().getBalance() == 1)
                return rotateRightDouble(a);
                // Correct for left-left side overweight.
            else //if(a.left.balance == -1 || /* delete only: */ a.left.balance == 0)
                return rotateRight(a);

    }

    /**
     * Performs a single right rotation:
     * a                  s
     * / \                / \
     * s   T3      =>    T1   a
     * / \                    / \
     * T1   T2                T2   T3
     *
     * @param a The rotation anchor.
     * @return The new anchor node.
     */
    private Node rotateRight(Node a) {
        Node s = a.getLeft();
        if (a == root)
            s.setRoot();
        else
            a.getParent().replace(a, s);

        a.setLeft(s.getRight());
        a.update();

        s.setRight(a);
        s.update();

        return s;
    }

    /**
     * Performs a single left rotation:
     * a                   s
     * / \                 / \
     * T1   s      =>       a   T3
     * / \             / \
     * T2   T3         T1   T2
     *
     * @param a The rotation anchor.
     * @return The new anchor node.
     */
    private Node rotateLeft(Node a) {
        Node s = a.getRight();
        if (a == root)
            s.setRoot();
        else
            a.getParent().replace(a, s);

        a.setRight(s.getLeft());
        a.update();

        s.setLeft(a);
        s.update();

        return s;

    }

    /**
     * Performs a double left rotation:
     * a                     b
     * / \                  /   \
     * T1   s               a       s
     * / \      =>     / \     / \
     * b   T4         T1   T2 T3   T4
     * / \
     * T2   T3
     *
     * @param a The rotation anchor.
     * @return The new anchor node.
     */
    private Node rotateLeftDouble(Node a) {

        Node s = a.getRight();
        Node b = s.getLeft();
        if (a == root)
            b.setRoot();
        else
            a.getParent().replace(a, b);

        a.setRight(b.getLeft());
        a.update();

        s.setLeft(b.getRight());
        s.update();

        b.setLeft(a);
        b.setRight(s);
        b.update();

        return b;

    }

    /**
     * Performs a double right rotation:
     * a                     b
     * / \                  /   \
     * s   T4              s       a
     * / \        =>       / \     / \
     * T1   b              T1   T2 T3   T4
     * / \
     * T2   T3
     *
     * @param a The rotation anchor.
     * @return The new anchor node.
     */
    private Node rotateRightDouble(Node a) {

        Node s = a.getLeft();
        Node b = s.getRight();
        if (a == root)
            b.setRoot();
        else
            a.getParent().replace(a, b);

        a.setLeft(b.getRight());
        a.update();

        s.setRight(b.getLeft());
        s.update();

        b.setRight(a);
        b.setLeft(s);
        b.update();

        return b;

    }

    /**
     * A tree node.
     */
    public class Node implements Serializable {
        private Node left;
        private Node right;
        private Node parent;
        private final int key;
        private int lastBlockLength;
        private int[] positions;
        private int balance;
        private int height = 1;

        /**
         * Creates a new node.
         *
         * @param key        The key.
         * @param lastLength the length of data array in last content block
         * @param positions  content indexes
         */
        public Node(int key, int lastLength, int[] positions) {
            this.key = key;
            this.setLastBlockLength(lastLength);
            this.setPositions(positions);
        }

        /**
         * Sets this node's left child.
         *
         * @param left The new left child.
         */
        private void setLeft(Node left) {
            this.left = left;
            if (left != null)
                left.setParent(this);
        }

        /**
         * Sets this node's right child.
         *
         * @param right The right child.
         */
        private void setRight(Node right) {
            this.right = right;
            if (right != null)
                right.setParent(this);
        }

        /**
         * Sets this node as root.
         */
        private void setRoot() {
            AvlTree.this.root = this;
            setParent(null);

        }

        /**
         * Replaces a child with another node.
         *
         * @param old The old node.
         * @param rep The new node.
         */
        private void replace(Node old, Node rep) {
            if (getLeft() == old)
                setLeft(rep);
            else if (getRight() == old)
                setRight(rep);
        }

        /**
         * Updates this node's height and balance values.
         */
        private void update() {
            int[] sonHeight = sonHeight();
            setBalance(sonHeight[0] - sonHeight[1]);
            setHeight(Math.max(sonHeight[0], sonHeight[1]) + 1);
        }

        /**
         * Computes an array containing the heights of both sons.
         * (0: left son, 1: right son)
         *
         * @return The heights of the sons as an array.
         */
        private int[] sonHeight() {
            return new int[]{
                    getRight() == null ? 0 : getRight().getHeight(),
                    getLeft() == null ? 0 : getLeft().getHeight()
            };
        }

        /**
         * Returns the only son given such a condition is satisfied.
         *
         * @return The only son of this node.
         */
        private Node onlySon() {
            if (getRight() != null && getLeft() == null)
                return getRight();
            else if (getLeft() != null && getRight() == null)
                return getLeft();
            else
                return null;
        }

        /**
         * Checks if this node is a leaf.
         *
         * @return <code>true</code> if this node is a leaf.
         */
        private boolean isLeaf() {
            return getLeft() == null && getRight() == null;
        }

        /**
         * Checks if this node has only one son.
         *
         * @return <code>true</code> if the node has only one son.
         */
        private boolean isSingleSon() {
            return getLeft() == null ^ getRight() == null;
        }

        /**
         * Removes a son of this node.
         *
         * @param son The son to remove.
         */
        private void removeSon(Node son) {
            if (son != null) {
                if (getLeft() == son)
                    setLeft(null);
                else if (getRight() == son)
                    setRight(null);
                son.setParent(null);
            }
        }

        public int getKey() {
            return key;
        }

        public int[] getPositions() {
            return positions;
        }

        /**
         * Checks if two values are equal. This method is not type save
         * since it is only called by an internal mechanism.
         */
        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            Node n = (Node) o;
            return
                    n.getKey() == this.getKey()
                            &&
                            ((this.getLeft() == null && n.getLeft() == null) || (n.getLeft() != null && n.getLeft().equals(n.getLeft())))
                            &&
                            ((this.getRight() == null && n.getRight() == null) || (n.getRight() != null && n.getRight().equals(n.getRight())))
                    ;
        }

        /**
         * This node's left and right children and this node's parent.
         */
        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public int getLastBlockLength() {
            return lastBlockLength;
        }

        public void setLastBlockLength(int lastBlockLength) {
            this.lastBlockLength = lastBlockLength;
        }

        public void setPositions(int[] positions) {
            this.positions = positions;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
