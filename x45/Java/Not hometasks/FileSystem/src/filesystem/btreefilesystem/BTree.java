package filesystem.btreefilesystem;

import org.apache.log4j.Logger;

/**
 * BTree implementation class
 *
 * @author Yuri Denison
 */
public class BTree {
    protected int order;             // order of the tree
    protected BTreeNode root;        // root of the tree

    private BTreeSerializer serializer;
    private Logger log;

    /**
     * Creates the root of a btree
     *
     * @param serializer serializer for btree
     */
    public BTree(BTreeSerializer serializer) {
        this.serializer = serializer;
        this.order = BTreeFileSystemManager.ORDER;
        log = Logger.getLogger(BTree.class);
    }

    /**
     * Delete keyNode from btree
     *
     * @param key key of deleting keyNode
     * @return deleted KeyNode if operation was successful, null if no such file was found
     */
    public KeyNode deleteKeyNode(int key) {
        // get information needed
        SearchResult tmpResult = searchKeyNode(root, key);
        if (tmpResult == null) {
            log.trace("DELETE NODE: no node found");
            return null;
        }
        BTreeNode delBTreeNode = tmpResult.getBTreeNode();
        boolean rootContainsKey = (delBTreeNode.equals(root));
        int keyIndex = tmpResult.getKeyIndex();
        KeyNode returnNode = delBTreeNode.getKeyNode(keyIndex);
        deleteKeyNode(root, key);
        if (root.nKey == 0 && !rootContainsKey) {
            root = serializer.readBTreeNode(root.btnArray[0]);
            int prevPos = root.getNodePosition();
            root.nodePosition = 0;
            serializer.writeBTreeNode(root);
            serializer.clearBlock(prevPos);
        }
        return returnNode;
    }

    private void deleteKeyNode(BTreeNode btnode, int key) {
        int keyIndex = -1;
        int probPos = 0;
        for (int i = 0; i < btnode.nKey; i++) {
            if (btnode.kArray[i].key == key) {
                keyIndex = i;
                break;
            }
            if (btnode.kArray[i].key < key && (i == btnode.nKey - 1 || btnode.kArray[i + 1].key > key)) {
                probPos = i + 1;
            }
        }

        // is a leaf **********************************
        if (keyIndex != -1) {
            if (btnode.isLeaf) {
                // we can delete KeyNode directly
                btnode.extractKeyNode(keyIndex);
                serializer.writeBTreeNode(btnode);
            } else {
                BTreeNode leftChildBTreeNode = serializer.readBTreeNode(keyIndex);
                BTreeNode rightChildBTreeNode = serializer.readBTreeNode(keyIndex + 1);
                if (leftChildBTreeNode.nKey > order - 1) {
                    int prev = 0;
                    for (int i = 0; i < leftChildBTreeNode.nKey; i++) {
                        if (leftChildBTreeNode.kArray[i].key > keyIndex) {
                            prev = i - 1;
                        }
                    }
                    btnode.kArray[keyIndex] = leftChildBTreeNode.kArray[prev];
                    serializer.writeBTreeNode(btnode);
                    deleteKeyNode(leftChildBTreeNode, leftChildBTreeNode.kArray[prev].key);
                } else if (rightChildBTreeNode.nKey > order - 1) {
                    int next = 0;
                    for (int i = 0; i < rightChildBTreeNode.nKey; i++) {
                        if (rightChildBTreeNode.kArray[i].key > keyIndex) {
                            next = i;
                        }
                    }
                    btnode.kArray[keyIndex] = rightChildBTreeNode.kArray[next];
                    serializer.writeBTreeNode(btnode);
                    deleteKeyNode(rightChildBTreeNode, rightChildBTreeNode.kArray[next].key);
                } else {
                    leftChildBTreeNode.kArray[order - 1] = btnode.kArray[keyIndex];
                    System.arraycopy(rightChildBTreeNode.kArray, 0, leftChildBTreeNode.kArray, order, rightChildBTreeNode.nKey);

                    for (int i = keyIndex; i < btnode.nKey - 1; i++) {
                        btnode.kArray[i] = btnode.kArray[i + 1];
                        btnode.btnArray[i + 1] = btnode.btnArray[i + 2];
                    }
                    btnode.kArray[btnode.nKey] = null;
                    btnode.btnArray[btnode.nKey] = 0;
                    btnode.nKey--;

                    serializer.clearBlock(rightChildBTreeNode.getNodePosition());
                    serializer.writeBTreeNode(btnode);

                    deleteKeyNode(leftChildBTreeNode, key);
                }
            }
        } else {
            // get the node to exchange and delete it at leaf position
            BTreeNode c = serializer.readBTreeNode(btnode.btnArray[probPos]);
            if (c.nKey == order - 1) {
                BTreeNode leftChildBTreeNode = (probPos > 0) ?
                        serializer.readBTreeNode(btnode.btnArray[probPos - 1]) :
                        null;
                BTreeNode rightChildBTreeNode = (probPos < btnode.nKey) ?
                        serializer.readBTreeNode(btnode.btnArray[probPos + 1]) :
                        null;

                if (leftChildBTreeNode != null && leftChildBTreeNode.nKey > order - 1) {
                    c.kArray[c.nKey] = btnode.kArray[probPos - 1];
                    c.btnArray[c.nKey + 1] = leftChildBTreeNode.btnArray[leftChildBTreeNode.nKey];
                    c.nKey++;
                    btnode.kArray[probPos - 1] = leftChildBTreeNode.kArray[leftChildBTreeNode.nKey - 1];
                    leftChildBTreeNode.kArray[leftChildBTreeNode.nKey - 1] = null;
                    leftChildBTreeNode.btnArray[leftChildBTreeNode.nKey] = 0;
                    leftChildBTreeNode.nKey--;

                    serializer.writeBTreeNode(btnode);
                    serializer.writeBTreeNode(c);
                    serializer.writeBTreeNode(leftChildBTreeNode);
                } else if (rightChildBTreeNode != null && rightChildBTreeNode.nKey > order - 1) {
                    c.kArray[c.nKey] = btnode.kArray[probPos];
                    c.btnArray[c.nKey + 1] = rightChildBTreeNode.btnArray[0];
                    c.nKey++;
                    btnode.kArray[probPos] = rightChildBTreeNode.kArray[0];

                    rightChildBTreeNode.btnArray[0] = rightChildBTreeNode.btnArray[1];
                    for (int i = 0; i < rightChildBTreeNode.nKey - 1; i++) {
                        rightChildBTreeNode.kArray[i] = rightChildBTreeNode.kArray[i + 1];
                        rightChildBTreeNode.btnArray[i + 1] = rightChildBTreeNode.btnArray[i + 2];
                    }
                    rightChildBTreeNode.kArray[rightChildBTreeNode.nKey - 1] = null;
                    rightChildBTreeNode.btnArray[rightChildBTreeNode.nKey] = 0;
                    rightChildBTreeNode.nKey--;

                    serializer.writeBTreeNode(btnode);
                    serializer.writeBTreeNode(c);
                    serializer.writeBTreeNode(rightChildBTreeNode);
                } else {
                    c.nKey++;
                    //getting node above
                    c.kArray[c.nKey - 1] = btnode.kArray[probPos];

                    BTreeNode mergeBTreeNode = (leftChildBTreeNode != null) ? leftChildBTreeNode : rightChildBTreeNode;
                    for (int i = c.nKey, j = 0; j < mergeBTreeNode.nKey; i++, j++) {
                        c.kArray[i] = mergeBTreeNode.kArray[j];
                        c.btnArray[i] = mergeBTreeNode.btnArray[j];
                        c.nKey++;
                    }
                    c.btnArray[c.nKey] = mergeBTreeNode.btnArray[mergeBTreeNode.nKey];

                    int i;
                    for (i = probPos; i < btnode.nKey - 1; i++) {
                        btnode.kArray[i] = btnode.kArray[i + 1];
                        btnode.btnArray[i + 1] = btnode.btnArray[i + 2];
                    }

                    btnode.kArray[i] = null;
                    btnode.btnArray[i + 1] = 0;
                    btnode.nKey--;

                    serializer.writeBTreeNode(btnode);
                    serializer.writeBTreeNode(c);
                    serializer.clearBlock(rightChildBTreeNode.getNodePosition());
                }
            }
            deleteKeyNode(serializer.readBTreeNode(btnode.btnArray[probPos]), key);
        }
    }


    /**
     * Insert keyNode with the given key into the tree
     *
     * @param keyNode input KeyNode
     */
    public void insert(KeyNode keyNode) {
        SearchResult sr = searchKeyNode(root, keyNode.key);
        if (sr != null) {
            sr.bTreeNode.kArray[sr.keyIndex] = keyNode;
            return;
        }

        BTreeNode r = root;
        if (root.nKey == 2 * order - 1) {
            BTreeNode s = serializer.allocateNode();
            root = s;
            s.isLeaf = false;
            s.nKey = 0;
            r.nodePosition = s.nodePosition;
            s.nodePosition = 0;
            s.btnArray[0] = r.nodePosition;
            serializer.writeBTreeNode(r);
            serializer.writeBTreeNode(s);

            split(s, 0, r);
            insertNonFull(s, keyNode);
        } else {
            insertNonFull(r, keyNode);
        }
    }

    private void insertNonFull(BTreeNode x, KeyNode keyNode) {
        int i = x.nKey;
        int k = keyNode.key;
        if (x.isLeaf) {
            while (i > 0 && k < x.kArray[i - 1].key) {
                x.kArray[i] = x.kArray[i - 1];
                i--;
            }
            x.kArray[i] = keyNode;
            x.nKey++;
            serializer.writeBTreeNode(x);
        } else {
            while (i > 0 && k < x.kArray[i - 1].key) {
                i--;
            }
            BTreeNode c = serializer.readBTreeNode(x.btnArray[i]);
            if (c.nKey == 2 * order - 1) {
                split(x, i, c);
                if (k > x.kArray[i].key) {
                    i++;
                    c = serializer.readBTreeNode(x.btnArray[i]);
                }
            }
            insertNonFull(c, keyNode);
        }
    }

    private void split(BTreeNode x, int index, BTreeNode y) {
        if ((x.nKey == 2 * order - 1) || (y.nKey != 2 * order - 1)) {
            return;
        }
        BTreeNode z = serializer.allocateNode();
        z.isLeaf = y.isLeaf;
        z.nKey = order - 1;
        System.arraycopy(y.kArray, order, z.kArray, 0, order - 1);
        if (!y.isLeaf) {
            System.arraycopy(y.btnArray, order, z.btnArray, 0, order);
        }
        y.nKey = order - 1;
        for (int i = x.nKey; i > index; i--) {
            x.btnArray[i + 1] = x.btnArray[i];
        }
        x.btnArray[index + 1] = z.getNodePosition();
        for (int i = x.nKey - 1; i >= index; i--) {
            x.kArray[i + 1] = x.kArray[i];
        }
        x.kArray[index] = y.kArray[order - 1];
        x.nKey++;

        for (int i = order - 1; i < 2 * order - 1; i++) {
            y.kArray[i] = null;
        }

        serializer.writeBTreeNode(y);
        serializer.writeBTreeNode(z);
        serializer.writeBTreeNode(x);
    }

    /**
     * Get object stored behind the given key
     *
     * @param key key in btree
     * @return KeyNode if found, null if not found
     */
    public KeyNode getKeyNode(int key) {
        SearchResult result = searchKeyNode(root, key);
        if (result == null) {
            return null;
        }
        return result.getBTreeNode().getKeyNode(result.getKeyIndex());
    }

    private SearchResult searchKeyNode(BTreeNode node, int key) {
        int i = 0;
        while (i < node.nKey && key > node.kArray[i].key) {
            i++;
        }
        if (i < node.nKey && key == node.kArray[i].key) {
            return new SearchResult(node, i);
        }
        if (node.isLeaf) {
            return null;
        } else {
            BTreeNode child = serializer.readBTreeNode(node.btnArray[i]);
            return searchKeyNode(child, key);
        }
    }

    public int size() {
        return size(root, 0);
    }

    private int size(BTreeNode node, int size) {
        if (node == null) {
            return size;
        }
        if (node.isLeaf) {
            return node.nKey;
        } else {
            size += node.nKey;
            for (int i = 0; i < node.nKey + 1; i++) {
                size += size(serializer.readBTreeNode(node.btnArray[i]), 0);
            }
            return size;
        }
    }

    private class SearchResult {
        private BTreeNode bTreeNode;
        private int keyIndex;

        SearchResult(BTreeNode bTreeNode, int keyIndex) {
            this.bTreeNode = bTreeNode;
            this.keyIndex = keyIndex;
        }

        BTreeNode getBTreeNode() {
            return bTreeNode;
        }

        int getKeyIndex() {
            return keyIndex;
        }
    }
}

