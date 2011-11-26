package filesystem.btreefilesystem;

import filesystem.IFileSystem;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Yuri Denison
 */
public class BTreeFileSystem implements IFileSystem {
    private BTreeSerializer serializer;
    private BTree tree;
    private Logger log;

    public BTreeFileSystem(BTree tree, BTreeSerializer serializer) {
        log = Logger.getLogger(BTreeFileSystem.class);
        this.serializer = serializer;
        this.tree = tree;
    }

    @Override
    public boolean createNewFile(String fileName) {
        KeyNode node = new KeyNode(getKeyID(fileName), 0, -1);
        tree.insert(node);
        log.debug("\n CREATE FILE\n tree.size = " + tree.size() + "\n");
        return true;
    }

    @Override
    public boolean insertFile(String filePath, String newFileName) {
        int[] positions = serializer.addFileContent(filePath);
        KeyNode node = new KeyNode(getKeyID(newFileName), positions[0], positions[1]);
        tree.insert(node);

        log.debug("\n ADD FILE\n tree.size = " + tree.size() + "\n");
        return true;
    }

    @Override
    public boolean deleteFile(String fileName) {
        KeyNode node = tree.deleteKeyNode(getKeyID(fileName));
        if (node == null) {
            return false;
        }
        serializer.deleteFileContent(node);
        log.debug("\n DELETE FILE\n tree.size = " + tree.size() + "\n");
        return true;
    }

    @Override
    public boolean contains(String fileName) {
        return (tree.getKeyNode(getKeyID(fileName)) != null);
    }

    @Override
    public InputStream readFile(String filePath) {
        KeyNode node = tree.getKeyNode(getKeyID(filePath));
        log.debug("\n READ FILE: " + filePath + "\n");
        return (node == null) ? null : serializer.getInputStream(node);
    }

    @Override
    public void writeFile(String fileName, OutputStream stream) {
        KeyNode node = tree.getKeyNode(getKeyID(fileName));
        if (node == null) {
            int[] startPositionAndLength = serializer.addFileContent(stream);
            KeyNode n = new KeyNode(getKeyID(fileName), startPositionAndLength[0], startPositionAndLength[1]);
            tree.insert(n);
        } else {
            int[] positions = new int[0];
            positions = serializer.addNewFileContentToKeyNode(node, stream);
            node.contentLength = positions[0];
            node.startContentBlockNumber = positions[1];
        }
        log.debug("\n WRITE FILE with stream: " + fileName + "\n");
    }

    @Override
    public void close() {
        serializer.close();
    }

    public int getSize() {
        return tree.size();
    }

    private int getKeyID(String name) {
        return name.hashCode();
    }
}
