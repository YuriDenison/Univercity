package BTree;

/**
 * ********************************************************************
 * Class BTreeNode
 * The BTreeNode is nothing else than a Node in the BTree. This nodes can be
 * greater or smaller it depends on the users order.
 */

class BTreeNode {
    protected int order = 0;
    protected int nKey = 0;            // number of keys stored in node
    protected KeyNode kArray[];        // array where keys are stored
    protected BTreeNode btnArray[];    // array where references to the next BTNodes is stored
    protected boolean isLeaf;        // is the btnode a leaf
    protected BTreeNode parent;        // link to the parent node

    /**
     * BTreeNode(int order, BTreeNode parent);
     * Constructor, creates a empty node with the given order and parent
     */
    public BTreeNode(int order, BTreeNode parent) {
        this.order = order;
        this.parent = parent;
        kArray = new KeyNode[2 * order - 1];
        btnArray = new BTreeNode[2 * order];
        isLeaf = true;
    }

    /**
     * extractKeyNode(int keyPos)
     * Moves the key's at and behind the start pos one position right
     */
    public KeyNode extractKeyNode(int keyIndex) {
        KeyNode tmpKeyNode = getKeyNode(keyIndex);
        for (int i = keyIndex; i < nKey; i++) {
            kArray[i] = kArray[i + 1];
            if (!isLeaf) {
                btnArray[i] = btnArray[i + 1];
            }
        }
        nKey--;
        return tmpKeyNode;
    }

    /**
     * getBTreeNode(int keyIndex)
     * returns the link to the next node at the given index from this node
     */
    public BTreeNode getBTNode(int keyIndex) {
        return btnArray[keyIndex];
    }

    /**
     * getKeyNode(int keyIndex)
     * returns the key and object from this node at the given index
     */
    public KeyNode getKeyNode(int keyIndex) {
        return kArray[keyIndex];
    }

    /**
     * insert(KeyNode keyNode);
     * Insert a key in a Node in the right position. (Small to Big)
     */
    public void insert(KeyNode keyNode) {
        if (nKey == 0) {
            nKey++;
            kArray[0] = keyNode;
        } else {
            int pos = 0;
            while (keyNode.getKey() > kArray[pos].getKey()) {
                pos++;
                if (pos == nKey) {
                    break;
                }
            }
            if (nKey == order * 2 - 1) {
                BTreeNode right = split();
                if (pos > order - 1) {
                    right.insert(keyNode);
                } else {
                    if (pos != nKey) {
                        shift(pos);
                    } else {
                        nKey++;
                    }
                    kArray[pos] = keyNode;
                }
            } else {
                if (pos != nKey) {
                    shift(pos);
                } else {
                    nKey++;
                }
                kArray[pos] = keyNode;
            }
        }
    }
    // you may only apply method to leafs...

    public void mergeWithBTNode() {

        BTreeNode parentBTreeNode = parent;
        BTreeNode mergeBTreeNode;
        int parentIndex = 0;

        while (parentBTreeNode.getBTNode(parentIndex) != this) {
            parentIndex++;
        }

        if (parentIndex > 1) {
            mergeBTreeNode = parent.getBTNode(parentIndex - 1);
        } else {
            mergeBTreeNode = parent.getBTNode(parentIndex + 1);
        }

        if (mergeBTreeNode != null) {

            //getting node above
            kArray[nKey - 1] = parentBTreeNode.getKeyNode(parentIndex);


            for (int i = nKey, j = 0; j < mergeBTreeNode.nKey; i++, j++) {
                kArray[i] = mergeBTreeNode.getKeyNode(j);
                nKey++;
            }

            int i;
            for (i = parentIndex; i < nKey; i++) {
                parentBTreeNode.kArray[i] = parentBTreeNode.kArray[i + 1];
                parentBTreeNode.btnArray[i + 1] = parentBTreeNode.btnArray[i + 2];
            }

            parentBTreeNode.kArray[i] = null;
            parentBTreeNode.btnArray[i + 1] = null;
            parentBTreeNode.nKey--;
        }
    }

    /**
     * shift(int startPos)
     * Moves the key's at, and behind the start pos one position right
     */
    private void shift(int startPos) {
        for (int i = nKey; i > startPos; i--) {
            kArray[i] = kArray[i - 1];
            if (!isLeaf)
                btnArray[i + 1] = btnArray[i];
        }
        nKey++;
    }

    /**
     * split()
     * Splits a node into to nodes. This can only be done, if the node is full
     * The middle key go up into the parent, the left ones of them rest in
     * this node, and the right ones go into a new node.
     */
    public BTreeNode split() {
        if (nKey == order * 2 - 1) {
            BTreeNode right = null;
            if (parent == null) { // also for the root-node
                BTreeNode left = new BTreeNode(order, this);
                right = new BTreeNode(order, this);
                for (int i = 0; i < order - 1; i++) {
                    left.kArray[i] = kArray[i];
                    right.kArray[i] = kArray[order + i];
                }
                if (!isLeaf) {
                    for (int i = 0; i < order; i++) {
                        left.btnArray[i] = btnArray[i];
                        left.btnArray[i].parent = left;
                        right.btnArray[i] = btnArray[order + i];
                        right.btnArray[i].parent = right;
                    }
                    left.isLeaf = false;
                    right.isLeaf = false;
                } else
                    isLeaf = false;
                kArray[0] = kArray[order - 1];
                nKey = 1;
                left.nKey = order - 1;
                right.nKey = order - 1;
                for (int i = 1; i < order * 2 - 1; i++) {
                    kArray[i] = null;
                    btnArray[i + 1] = null;
                }
                btnArray[0] = left;
                btnArray[1] = right;
            } else { // also for non-root-nodes
                if (parent.nKey == order * 2 - 1)
                    parent.split();
                int pos = 0;
                while (kArray[order - 1].getKey() > parent.kArray[pos].getKey()) {
                    pos++;
                    if (pos == parent.nKey)
                        break;
                }
                parent.shift(pos);
                parent.kArray[pos] = kArray[order - 1];
                right = new BTreeNode(order, parent);
                for (int i = 0; i < order - 1; i++)
                    right.kArray[i] = kArray[order + i];
                if (!isLeaf) {
                    for (int i = 0; i < order; i++) {
                        right.btnArray[i] = btnArray[order + i];
                        right.btnArray[i].parent = right;
                    }
                    right.isLeaf = false;
                }
                right.nKey = order - 1;
                nKey = order - 1;
                for (int u = 0; u < order - 1; u++) {
                    kArray[order - 1 + u] = null;
                    btnArray[order + u] = null;
                }
                parent.btnArray[pos] = this;
                parent.btnArray[pos + 1] = right;
            }
            return right;
        } else
            return null;
    }
}
