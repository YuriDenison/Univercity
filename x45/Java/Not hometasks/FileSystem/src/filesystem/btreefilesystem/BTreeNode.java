package filesystem.btreefilesystem;

/**
 * BTree node class
 *
 * @author Yuri Denison
 */
class BTreeNode {
    protected int nodePosition;        // position of appropriate block
    protected int nKey = 0;            // number of keys stored in node
    protected KeyNode[] kArray;        // array where keys are stored
    protected int[] btnArray;    // array where references to the next BTNodes is stored
    protected boolean isLeaf;        // is the btnode a leaf

    /**
     * Constructor to reconstruct BTNode from file on disk
     *
     * @param nodePosition position of appropriate block
     * @param nKey         number of keys stored in node
     * @param kArray       array where keys are stored
     * @param btnArray     array where references to the next BTNodes is stored
     * @param leaf         is the btnode a leaf
     */
    protected BTreeNode(int nodePosition, int nKey, boolean leaf, KeyNode[] kArray, int[] btnArray) {
        this.nodePosition = nodePosition;
        this.nKey = nKey;
        this.kArray = kArray;
        this.btnArray = btnArray;
        isLeaf = leaf;
    }

    /**
     * extractKeyNode(int keyPos)
     * Moves the key's at and behind the start pos one position right
     *
     * @param keyIndex key of deleted BTreeNode
     * @return deleted BTreeNode
     */
    public KeyNode extractKeyNode(int keyIndex) {
        KeyNode tmpKeyNode = getKeyNode(keyIndex);
        for (int i = keyIndex; i < nKey - 1; i++) {
            kArray[i] = kArray[i + 1];
            if (!isLeaf) {
                btnArray[i] = btnArray[i + 1];
            }
        }
        nKey--;
        kArray[nKey] = null;
        btnArray[nKey] = 0;
        return tmpKeyNode;
    }

    /**
     * Get keyNode by keyIndex from BTreeNode
     *
     * @param keyIndex input key
     * @return KeyNode if BTreeNode contains such key, null if not
     */
    public KeyNode getKeyNode(int keyIndex) {
        return (keyIndex < 2 * nKey - 1 && keyIndex >= 0) ? kArray[keyIndex] : null;
    }

    public int getNodePosition() {
        return nodePosition;
    }
}
