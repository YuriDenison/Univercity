package BTree;

/**
 * Created by IntelliJ IDEA.
 * User: Volkman
 * Date: 28.05.2010
 * Time: 14:30:55
 */
public class BTree {
    private int order;
    private BTreeNode root;

    /**
     * Constructor
     * creates the root of a btree
     */
    public BTree(int order) {
        this.order = order;
        root = new BTreeNode(order, null);
    }

    private KeyNode deleteNode(BTreeNode btnode, int key) {
        // get information needed
        SearchResult tmpResult = searchObj(btnode, key);
        BTreeNode delBTreeNode = tmpResult.getBTreeNode();
        if (delBTreeNode == null) {
            return null;
        }
        int keyIndex = tmpResult.getKeyIndex();
        KeyNode returnNode = delBTreeNode.getKeyNode(keyIndex);

        // is a leaf **********************************
        if (delBTreeNode.isLeaf) {
            if (delBTreeNode.nKey > order - 1)
            // we can delete KeyNode directly
            {
                delBTreeNode.extractKeyNode(keyIndex);
            } else
            // we need to get more keys so that we can delete it
            {
                BTreeNode parentBTreeNode = delBTreeNode.parent;
                int parentIndex = 0;
                while (parentBTreeNode.getBTNode(parentIndex) != delBTreeNode)
                    parentIndex++;
                if (parentIndex == parentBTreeNode.nKey) {
                    BTreeNode leftBTreeNode = parentBTreeNode.getBTNode(parentIndex - 1);
                    if (leftBTreeNode.nKey > order - 1) {
                        delBTreeNode.kArray[keyIndex] = parentBTreeNode.getKeyNode(parentIndex);
                        parentBTreeNode.kArray[parentIndex] = leftBTreeNode.getKeyNode(leftBTreeNode.nKey - 1);
                        deleteNode(leftBTreeNode, leftBTreeNode.getKeyNode(leftBTreeNode.nKey - 1).getKey());
                    } else {
                        delBTreeNode.mergeWithBTNode();
                    }
                } else {
                    BTreeNode rightBTreeNode = parentBTreeNode.getBTNode(parentIndex + 1);
                    if (rightBTreeNode.nKey > order - 1) {
                        delBTreeNode.kArray[keyIndex] = parentBTreeNode.getKeyNode(parentIndex);
                        parentBTreeNode.kArray[parentIndex] = rightBTreeNode.getKeyNode(0);
                        deleteNode(rightBTreeNode, rightBTreeNode.getKeyNode(0).getKey());
                    } else {
                        delBTreeNode.mergeWithBTNode();
                    }
                }
            }
        } else
        // is internal node *********************
        {
            // get the node to exchange and delete it at leaf position
            BTreeNode preBTreeNode = delBTreeNode.getBTNode(keyIndex);
            while (!preBTreeNode.isLeaf) {
                preBTreeNode = preBTreeNode.getBTNode(preBTreeNode.nKey);
            }

            // swap nodes
            KeyNode tmpKeyNode = preBTreeNode.getKeyNode(preBTreeNode.nKey - 1);
            preBTreeNode.kArray[preBTreeNode.nKey - 1] = delBTreeNode.kArray[keyIndex];
            delBTreeNode.kArray[keyIndex] = tmpKeyNode;
            deleteNode(preBTreeNode, preBTreeNode.getKeyNode(preBTreeNode.nKey - 1).getKey());
        }
        return returnNode;
    }

    /**
     * insert a object with the given key into the tree
     */
    public void insert(KeyNode keyNode) {
        BTreeNode BTreeNode = root;
        while (!BTreeNode.isLeaf) {
            int i = 0;
            while (keyNode.getKey() > BTreeNode.kArray[i].getKey()) {
                i++;
                if (i == BTreeNode.nKey) break;
            }
            BTreeNode = BTreeNode.btnArray[i];
        }
        BTreeNode.insert(keyNode);
        if (root.nKey == order * 2 - 1) root.split();
    }

    /**
     * returns the object stored behind the given key
     */
    public Object getKeyObj(int key) {
        SearchResult result = searchObj(root, key);
        if (result.getBTreeNode() == null)
            return null;
        else
            return (result.getBTreeNode().getKeyNode(result.getKeyIndex())).getData();
    }

    public KeyNode deleteObj(int key) {
        return deleteNode(root, key);
    }

    private SearchResult searchObj(BTreeNode btnode, int key) {
        SearchResult result = new SearchResult(null, -1);
        int i = 0;
        boolean keyNotInNode = false;
        boolean keyFound = false;

        while (!keyNotInNode && !keyFound) {

            if (btnode.getKeyNode(i) != null && key < btnode.getKeyNode(i).getKey()) {
                keyNotInNode = true;
                if (!btnode.isLeaf)
                    result = searchObj(btnode.getBTNode(i), key);
            } else if (btnode.getKeyNode(i) != null && key == btnode.getKeyNode(i).getKey()) {
                keyFound = true;
                result = new SearchResult(btnode, i);//key found
            } else if (i < (btnode.nKey - 1)) i++;
            else if (btnode.getKeyNode(i) != null && key > btnode.getKeyNode(i).getKey()) {
                keyNotInNode = true;
                if (!btnode.isLeaf)
                    result = searchObj(btnode.getBTNode(i + 1), key);
            } else keyNotInNode = true;

        }
        return result;
    }
}
