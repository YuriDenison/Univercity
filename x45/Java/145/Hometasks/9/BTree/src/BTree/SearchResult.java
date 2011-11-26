package BTree;

/**
 * Class SearchResult
 * A dummy class for saving the search result
 */
class SearchResult {
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
