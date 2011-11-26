package filesystem.btreefilesystem;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;

/**
 * Serializer class for disk operations in btree
 * <p/>
 * Contains blocks of BLOCK_SIZE bytes (default is 4096)
 * <p/>
 * Each file content block contains information in it:
 * isUsed (boolean) - contains data,
 * isLast (boolean) - flag indicating whether this is the last block,
 * lastSize (int) - length of data array (if isLast == true) OR
 * nextBlock (int) - index of the next block (if isLast == false)
 * data (byte[]) - data array
 * <p/>
 * Each BTreeNode block contains information:
 * isUsed (boolean) - contains data
 * nKey (int) - number of keys in node
 * isLeaf (boolean) - true if node is a leaf
 * Sequence of keyNodes info (key (int), content length (int), first content block index (int))
 * Sequence of links to child nodes (int[])
 * <p/>
 * Because of restrictions on the number of keys (2 * order - 1) and the number of child nodes (2 * order),
 * the maximum size of BTreeNode block assumed to be
 * (1 + 4 + 1 + 3 * 4 * (2 * order - 1) + 4 * (2 * order)) = (32 * order - 6) bytes
 * Because of default BLOCK_SIZE = 4096 bytes, the maximum order assumed to be:
 * order = (int) ((4096 - 6) / 32) = 127
 *
 * @author Yuri Denison
 */
public class BTreeSerializer {
    private static final int DEFAULT_BLOCKS_NUMBER = 1000;
    private static final int BLOCK_SIZE = 4096;
    private static final int BLOCK_DATA_SIZE = BLOCK_SIZE - 6; // 6 = 2 * boolean + (Integer.SIZE / 8)

    private RandomAccessFile file;
    private Logger log;
    private boolean isNewFile;

    /**
     * Creates new Serializer with default parameters: Block size = 4096 bytes, number of blocks = 1000
     *
     * @param path   path of the file system file
     * @param create create a new file system or read the existing
     */
    public BTreeSerializer(String path, boolean create) {
        log = Logger.getLogger(BTreeSerializer.class);

        try {
            if (create) {
                File f = new File(path);
                if (!f.delete()) {
                    log.error("Error deleting previous file.");
                }
            }
            file = new RandomAccessFile(path, "rw");

            isNewFile = create;
            if (create) {
                for (int i = 1; i < DEFAULT_BLOCKS_NUMBER; i++) {
                    file.seek(i * BLOCK_SIZE);
                    file.writeBoolean(false);
                }
            }
        } catch (Exception e) {
            log.error("Error creating Random Access File: ", e);
            System.exit(-1);
        }
    }

    /**
     * Creates new block
     *
     * @return position number
     */
    public BTreeNode allocateNode() {
        try {
            int pos = getFirstClearBlockNumber(0);
            file.seek(pos * BLOCK_SIZE);
            file.writeBoolean(true);        // isUsed
            file.writeInt(0);               // nKey
            file.writeBoolean(true);        // isLeaf
            return new BTreeNode(pos, 0, true,
                    new KeyNode[2 * BTreeFileSystemManager.ORDER - 1],
                    new int[2 * BTreeFileSystemManager.ORDER]);
        } catch (Exception e) {
            log.error("Error allocating node: ", e);
            return null;
        }
    }

    /**
     * Reading BTreeNode from file
     *
     * @param pos block index of BTreeNode
     * @return BTreeNode object or null of any errors have happened
     */
    public BTreeNode readBTreeNode(int pos) {
        try {
            file.seek(pos * BLOCK_SIZE);
            if (!file.readBoolean()) {
                return null;  // if block is empty
            }
            int nKey = file.readInt();
            boolean isLeaf = file.readBoolean();
            KeyNode[] kArray = new KeyNode[2 * BTreeFileSystemManager.ORDER - 1];
            for (int i = 0; i < nKey; i++) {
                int key = file.readInt();
                int contentLength = file.readInt();
                int startContentPosition = file.readInt();
                kArray[i] = new KeyNode(key, contentLength, startContentPosition);
            }
            int[] btnArray = new int[2 * BTreeFileSystemManager.ORDER];
            if (!isLeaf) {
                for (int i = 0; i < nKey + 1; i++) {
                    btnArray[i] = file.readInt();
                }
            }
            return new BTreeNode(pos, nKey, isLeaf, kArray, btnArray);
        } catch (Exception e) {
            log.error("Error reading BTreeNode: ", e);
            return null;
        }
    }

    /**
     * Writes node to file
     *
     * @param node node for writing
     */
    public void writeBTreeNode(BTreeNode node) {
        writeBTreeNode(node.nodePosition, node);
    }

    private void writeBTreeNode(int nodePosition, BTreeNode node) {
        try {
            file.seek(nodePosition * BLOCK_SIZE);
            file.writeBoolean(true);
            file.writeInt(node.nKey);
            file.writeBoolean(node.isLeaf);
            for (int i = 0; i < node.nKey; i++) {
                file.writeInt(node.kArray[i].key);
                file.writeInt(node.kArray[i].contentLength);
                file.writeInt(node.kArray[i].startContentBlockNumber);
            }
            if (!node.isLeaf) {
                for (int i = 0; i < node.nKey + 1; i++) {
                    file.writeInt(node.btnArray[i]);
                }
            }
        } catch (Exception e) {
            log.error("Error writing BTreeNode to file: ", e);
        }
    }

    /**
     * Deletes block
     *
     * @param num num of block to delete
     */
    public void clearBlock(int num) {
        try {
            file.seek(num * BLOCK_SIZE);
            file.writeBoolean(false);       // set node block unused
        } catch (Exception e) {
            log.error("Error clearing block: ", e);
        }
    }


    /**
     * Read file content of input iNode
     *
     * @param keyNode iNode
     * @return content byte array, if any exception throws returns empty array
     */
    public byte[] readFileContent(KeyNode keyNode) {
        int start = keyNode.startContentBlockNumber;
        byte[] bytes = new byte[keyNode.contentLength];
        try {
            readContent(bytes, 0, start);
            return bytes;
        } catch (Exception e) {
            log.error("Error reading file content: ", e);
            return new byte[0];
        }
    }

    private void readContent(byte[] bytes, int currentLength, int start) {
        try {
            file.seek(start * BLOCK_SIZE);
            if (!file.readBoolean()) {
                return;
            }
            boolean isLast = file.readBoolean();
            if (isLast) {
                int size = file.readInt();
                byte[] cur = new byte[size];
                file.read(cur);
                System.arraycopy(cur, 0, bytes, currentLength, size);
            } else {
                int next = file.readInt();
                byte[] cur = new byte[BLOCK_DATA_SIZE];
                file.read(cur);
                System.arraycopy(cur, 0, bytes, currentLength, cur.length);
                currentLength += cur.length;
                readContent(bytes, currentLength, next);
            }
        } catch (Exception e) {
            log.error("cur = " + currentLength + " Error reading file content: ", e);
        }
    }

    /**
     * @param keyNode modifying keyNode
     * @param stream  data stream
     * @return int[] where index 0 - length of the content, 1 - first block index
     */
    public int[] addNewFileContentToKeyNode(KeyNode keyNode, OutputStream stream) {
        deleteFileContent(keyNode);
        return addFileContent(stream);
    }

    /**
     * Add file content from file on disk
     *
     * @param filePath path of input file
     * @return int[] where index 0 - length of the content, 1 - first block index
     */
    public int[] addFileContent(String filePath) {
        try {
            File f = new File(filePath);
            FileInputStream fis = new FileInputStream(f);
            byte[] b = new byte[(int) f.length()];
            fis.read(b);
            fis.close();
            return writeByteArray(b);
        } catch (Exception e) {
            log.error("Error reading file from disk: ", e);
            return new int[0];
        }
    }

    /**
     * Add file content from stream
     *
     * @param stream output stream from user
     * @return int[] where index 0 - length of the content, 1 - first block index
     */
    public int[] addFileContent(OutputStream stream) {
        try {
            return writeByteArray(((ByteArrayOutputStream) stream).toByteArray());
        } catch (Exception e) {
            log.error("Error reading file from disk: ", e);
            return null;
        }
    }

    /**
     * Delete file content
     *
     * @param keyNode iNode
     */
    public void deleteFileContent(KeyNode keyNode) {
        int start = keyNode.startContentBlockNumber;
        if (start > 0) {
            deleteContent(start);
        }
    }

    private void deleteContent(int start) {
        try {
            file.seek(start * BLOCK_SIZE);
            if (!file.readBoolean()) {
                return;
            }
            boolean isLast = file.readBoolean();
            if (isLast) {
                clearBlock(start);
            } else {
                int next = file.readInt();
                clearBlock(start);
                deleteContent(next);
            }
        } catch (IOException e) {
            log.error("Error deleting file content: ", e);
        }
    }

    /**
     * write bytes to disk
     *
     * @param bytes input array
     * @return int[] where index 0 - length of the content, 1 - first block index
     */
    private int[] writeByteArray(byte[] bytes) {
        try {
            int parts = (int) (Math.ceil((double) bytes.length / BLOCK_DATA_SIZE));
            int[] positions = new int[2];

            int startPos = getFirstClearBlockNumber(0);
            int currentBlock = startPos;
            int prevBlock = startPos;
            byte[] block;
            for (int i = 0; i < parts - 1; i++) {
                currentBlock = getFirstClearBlockNumber(currentBlock);
                file.seek(prevBlock * BLOCK_SIZE + 2);
                file.writeInt(currentBlock);      //next

                file.seek(currentBlock * BLOCK_SIZE);
                block = Arrays.copyOfRange(bytes, i * BLOCK_DATA_SIZE, (i + 1) * BLOCK_DATA_SIZE);
                file.writeBoolean(true);       // isUsed
                file.writeBoolean(false);      // isLast
                file.writeInt(0);              // next
                file.write(block);             // data
                prevBlock = currentBlock;
                currentBlock++;
            }
            currentBlock = getFirstClearBlockNumber(currentBlock);
            file.seek(currentBlock * BLOCK_SIZE);
            block = Arrays.copyOfRange(bytes, (parts - 1) * BLOCK_DATA_SIZE, bytes.length);
            file.seek(currentBlock * BLOCK_SIZE);
            file.writeBoolean(true);           // isUsed
            file.writeBoolean(true);           // isLast
            file.writeInt(block.length);       // last length
            file.write(block);                 // data

            if (parts != 1) {
                file.seek(prevBlock * BLOCK_SIZE);
                file.writeBoolean(true);
                file.writeBoolean(false);
                file.writeInt(currentBlock);
            }

            positions[0] = bytes.length;
            positions[1] = startPos;
            return positions;
        } catch (Exception e) {
            log.error("Error writing to disk: ", e);
            return new int[0];
        }
    }

    /**
     * Get the first empty block with greater number than the startPos
     *
     * @param startPos the search start index
     * @return index of the first empty block
     */
    private int getFirstClearBlockNumber(int startPos) {
        int pos = startPos;
        try {
            while (true) {
                file.seek(pos * BLOCK_SIZE);
                if (!file.readBoolean()) {
                    return pos;
                }
                pos++;
            }
        } catch (Exception e) {
            log.debug("Error finding clear block: max position = " + pos + ". Increasing file size.. ");
            increaseFileSize(pos);
            return pos;
        }
    }

    /**
     * Increase the file size by 50%
     *
     * @param pos current size of file
     */
    private void increaseFileSize(int pos) {
        try {
            for (int i = 0; i < pos / 2; i++) {
                file.seek((pos + i) * BLOCK_SIZE);
                file.writeBoolean(false);
            }
        } catch (Exception e) {
            log.error("Error increasing file size: ", e);
        }
    }

    /**
     * Close the filesystem.
     */
    public void close() {
        try {
            file.close();
        } catch (Exception e) {
            log.error("Error closing file: ", e);
        }
    }

    /**
     * Get input stream with content from node
     *
     * @param node input node
     * @return ByteArrayInputStream
     */
    public InputStream getInputStream(KeyNode node) {
        try {
            byte[] b = readFileContent(node);
            return new ByteArrayInputStream(b);
        } catch (Exception e) {
            log.error("Error constructing input stream: ", e);
            return null;
        }
    }

    /**
     * Get the BTree object
     *
     * @return (isNewFile) ? new BTree()  : readBTree()
     */
    public BTree getBTree() {
        BTree tree = new BTree(this);
        tree.root = (!isNewFile) ? readBTreeNode(0) : allocateNode();
        return tree;
    }
}
