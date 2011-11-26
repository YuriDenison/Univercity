package filesystem.avltreefilesystem;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;

/**
 * Class that encapsulates all work with disk using RandomAccessFile
 * Contains of fileInfo, iNode BTree serialization,
 * data storage (array of blocks), info about blocks (BlockInUse boolean array)
 *
 * @author Yuri Denison
 */
public class AvlTreeSerializer {
    private static final int BLOCK_SIZE = 4096;
    private static final int DEFAULT_BLOCKS_NUMBER = 1000;
    private static final double SPACE_PERCENT_FOR_INDEX_DESCRIPTORS = 0.01;
    private boolean[] blocksInUse;

    private int blocksNumber;
    private long blocksUsesArrayFirstPosition;
    private long contentFirstPosition;
    private long bTreeFirstPosition;
    private RandomAccessFile file;
    private Logger log;

    /**
     * Creates new Serializer with default parameters: Block size = 4096 bytes, number of blocks = 1000
     *
     * @param path    path of the file for file system
     * @param newFile if true - the new file system will be created
     *                if false - try to read file system from this path, if error - creates new
     */
    public AvlTreeSerializer(String path, boolean newFile) {
        log = Logger.getLogger(AvlTreeSerializer.class);

        try {
            file = new RandomAccessFile(path, "rw");
            if (!newFile && file.length() != 0) {
                file.seek(0);
                bTreeFirstPosition = file.readLong();
                contentFirstPosition = file.readLong();
                blocksNumber = file.readInt();
                blocksUsesArrayFirstPosition = file.readLong();
                file.seek(blocksUsesArrayFirstPosition);
                blocksInUse = new boolean[blocksNumber];
                for (int i = 0; i < blocksNumber; i++) {
                    blocksInUse[i] = file.readBoolean();
                }
            } else {
                blocksNumber = DEFAULT_BLOCKS_NUMBER;
                contentFirstPosition = (int) (BLOCK_SIZE * blocksNumber * SPACE_PERCENT_FOR_INDEX_DESCRIPTORS);
                blocksInUse = new boolean[blocksNumber];
                bTreeFirstPosition = 3 * Long.SIZE + Integer.SIZE;
                blocksUsesArrayFirstPosition = contentFirstPosition + blocksNumber * BLOCK_SIZE;

                writeSerializerInfo();
            }
            log.debug("\n bl = " + blocksNumber + "\n bfp = " + bTreeFirstPosition + "\n bfc = " + contentFirstPosition);

        } catch (Exception e) {
            log.error("Error creating Random Access File: ", e);
            System.exit(-1);
        }
    }

    private void writeSerializerInfo() {
        try {
            file.seek(0);
            file.writeLong(bTreeFirstPosition);
            file.writeLong(contentFirstPosition);
            file.writeInt(blocksNumber);
            file.writeLong(blocksUsesArrayFirstPosition);
            file.seek(blocksUsesArrayFirstPosition);
            for (int i = 0; i < blocksNumber; i++) {
                file.writeBoolean(blocksInUse[i]);
            }
        } catch (Exception e) {
            log.error("Error writing serializer info to file: ", e);
        }
    }

    /**
     * Write AvlTree to file on disk
     *
     * @param tree input btree
     */
    public void writeAvlTree(AvlTree tree) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(tree);
            oos.flush();
            oos.close();
            bos.close();
            byte[] treeBytes = bos.toByteArray();

            file.seek(bTreeFirstPosition);
            if (treeBytes.length >= contentFirstPosition - bTreeFirstPosition) {
                increaseBTreeSize();
            }
            file.write(new byte[(int) (contentFirstPosition - bTreeFirstPosition - 1)]);

            file.seek(bTreeFirstPosition);
            file.writeInt(treeBytes.length);
            file.write(treeBytes);
        } catch (Exception e) {
            log.error("Error writing BTree to disk: ", e);
        }
    }

    /**
     * Read AvlTree from disk
     *
     * @return AvlBTree object from disk or new BTree if any exceptions are throws
     */
    public AvlTree readAvlTree() {
        try {
            file.seek(bTreeFirstPosition);
            byte[] bTreeBytes = new byte[file.readInt()];
            file.read(bTreeBytes);

            ByteArrayInputStream bis = new ByteArrayInputStream(bTreeBytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            AvlTree tree = (AvlTree) ois.readObject();
            tree.setSerializer(this);
            return tree;
        } catch (Exception e) {
            log.error("Error reading AvlTree from disk: ", e);
            log.debug("Creating new AvlTree");
            return new AvlTree(this);
        }
    }

    /**
     * Read file content of input iNode
     *
     * @param node iNode
     * @return content byte array, if any exception throws returns empty array
     */
    public byte[] readFileContent(AvlTree.Node node) {
        try {
            byte[] result = new byte[node.getPositions().length * BLOCK_SIZE];
            int[] contentBlockPositions = node.getPositions();
            byte[] page = new byte[BLOCK_SIZE];
            for (int i = 0; i < contentBlockPositions.length; i++) {
                file.seek(contentFirstPosition + contentBlockPositions[i] * BLOCK_SIZE);
                file.read(page);

                int blockPos = i * BLOCK_SIZE;
                System.arraycopy(page, 0, result, blockPos, BLOCK_SIZE);
            }
            return result;

        } catch (Exception e) {
            log.error("Error reading file from disk: ", e);
            return new byte[0];
        }
    }

    /**
     * @param node   modifying keyNode
     * @param stream data stream
     * @return int[] where index 0 - length of the last block, 1..parts - block indexes
     */
    public int[] addNewFileContentToKeyNode(AvlTree.Node node, OutputStream stream) {
        deleteFileContent(node);
        return addFileContent(stream);
    }

    /**
     * Add file content from file on disk
     *
     * @param filePath path of input file
     * @return int[] where index 0 - length of the last block, 1..parts - block indexes
     *         if no such file exists, return new int[0]
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
     * @return int[] where index 0 - length of the last block, 1..parts - block indexes
     */
    public int[] addFileContent(OutputStream stream) {
        try {
            return writeByteArray(((ByteArrayOutputStream) stream).toByteArray());
        } catch (Exception e) {
            log.error("Error reading file from disk: ", e);
        }
        return null;
    }

    /**
     * Delete file content
     *
     * @param node iNode
     * @return true if operation is successful, false if not
     */
    public boolean deleteFileContent(AvlTree.Node node) {
        try {
            int[] contentBlockPositions = node.getPositions();
            byte[] emptyBlock = new byte[BLOCK_SIZE];
            for (int contentBlockPosition : contentBlockPositions) {
                file.seek(contentFirstPosition + contentBlockPosition * BLOCK_SIZE);
                file.write(emptyBlock);
                blocksInUse[contentBlockPosition] = false;
            }
            node.setPositions(new int[0]);
            node.setLastBlockLength(0);
            return true;

        } catch (Exception e) {
            log.error("Error deleting file from disk: ", e);
            return false;
        }
    }

    /**
     * write bytes to disk
     *
     * @param bytes input array
     * @return int[] where index 0 - length of the last block, 1..parts - block indexes
     */
    private int[] writeByteArray(byte[] bytes) {
        try {
            int parts = (int) (Math.ceil((double) bytes.length / BLOCK_SIZE));
            int[] positions = new int[parts + 1];

            int currentBlock = findFirstClearBlock();
            byte[] block;
            for (int i = 0; i < parts; i++) {
                block = Arrays.copyOfRange(bytes, i * BLOCK_SIZE, (i + 1) * BLOCK_SIZE);
                if (blocksInUse[currentBlock]) {
                    currentBlock = findFirstClearBlock();
                }
                file.seek(contentFirstPosition + currentBlock * BLOCK_SIZE);
                file.write(block);
                positions[i + 1] = currentBlock;
                blocksInUse[currentBlock] = true;

                currentBlock++;
                if (currentBlock == blocksInUse.length) {
                    increaseBlocksNumber();
                }
            }
            positions[0] = bytes.length - (parts - 1) * BLOCK_SIZE;
            return positions;
        } catch (Exception e) {
            log.error("Error writing to disk: ", e);
            return new int[0];
        }
    }

    private int findFirstClearBlock() {
        for (int i = 0; i < blocksInUse.length; i++) {
            if (!blocksInUse[i]) {
                return i;
            }
        }
        log.error("No clear blocks.");
        increaseBlocksNumber();
        return blocksInUse.length / 2;
    }

    private void increaseBlocksNumber() {
        blocksNumber *= 2;
        blocksInUse = Arrays.copyOf(blocksInUse, blocksNumber);

        writeSerializerInfo();
    }

    private void increaseBTreeSize() {
        try {
            file.seek(contentFirstPosition);
            byte[] b = new byte[BLOCK_SIZE * blocksNumber];
            file.read(b);

            contentFirstPosition *= 2;
            file.seek(contentFirstPosition);
            file.write(b);
        } catch (IOException e) {
            log.error("Error increasing btree size: ", e);
        }
    }

    public void close() {
        try {
            file.close();
        } catch (Exception e) {
            log.error("Error closing file: ", e);
        }
    }

    public InputStream getInputStream(AvlTree.Node node) {
        try {
            int[] pos = node.getPositions();
            byte[] b = new byte[pos.length * BLOCK_SIZE];
            for (int i = 0; i < pos.length; i++) {
                file.seek(contentFirstPosition + pos[i] * BLOCK_SIZE);
                byte[] cur = new byte[BLOCK_SIZE];
                file.read(cur);
                System.arraycopy(cur, 0, b, i * BLOCK_SIZE, BLOCK_SIZE);
            }
            return new ByteArrayInputStream(
                    Arrays.copyOfRange(b, 0, node.getLastBlockLength() + (pos.length - 1) * BLOCK_SIZE)
            );
        } catch (Exception e) {
            log.error("Error constructing input stream: ", e);
            return null;
        }
    }
}
