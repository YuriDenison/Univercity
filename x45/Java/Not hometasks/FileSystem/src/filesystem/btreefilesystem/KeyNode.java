package filesystem.btreefilesystem;

/**
 * The iNode class
 *
 * @author Yuri Denison
 */
public class KeyNode {
    protected int key;
    protected int startContentBlockNumber;
    protected int contentLength;

    /**
     * creates new KeyNode
     *
     * @param key                     iNode id
     * @param contentLength           length of content byte array
     * @param startContentBlockNumber first content block number
     */
    public KeyNode(int key, int contentLength, int startContentBlockNumber) {
        this.key = key;
        this.startContentBlockNumber = startContentBlockNumber;
        this.contentLength = contentLength;
    }

    /**
     * Getter for content length
     *
     * @return content length
     */
    public int getContentLength() {
        return contentLength;
    }

    /**
     * Getter for key
     *
     * @return key
     */
    public int getKey() {
        return key;
    }
}
