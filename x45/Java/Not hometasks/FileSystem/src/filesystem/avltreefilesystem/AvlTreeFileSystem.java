package filesystem.avltreefilesystem;

import filesystem.IFileSystem;
import filesystem.avltreefilesystem.AvlTree.Node;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author Yuri Denison
 */
public class AvlTreeFileSystem implements IFileSystem {
    private AvlTreeSerializer serializer;
    private AvlTree tree;
    private Logger log;

    public AvlTreeFileSystem(AvlTree tree, AvlTreeSerializer serializer) {
        log = Logger.getLogger(AvlTreeFileSystem.class);
        this.serializer = serializer;
        this.tree = tree;
    }

    @Override
    public boolean createNewFile(String fileName) {
        tree.insert(getKeyID(fileName), 0, new int[0]);
        log.debug("\n CREATE FILE\n tree.size = " + tree.size() + "\n");
        return true;
    }

    @Override
    public boolean insertFile(String filePath, String newFileName) {
        int keyId = getKeyID(newFileName);
        Node searchNode = tree.find(keyId);

        if (searchNode == null) {
            int[] positions = serializer.addFileContent(filePath);
            Node node = (positions.length == 0)
                    ? tree.new Node(keyId, 0, positions)
                    : tree.new Node(keyId, positions[0], Arrays.copyOfRange(positions, 1, positions.length));
            tree.insert(node);
        } else {
            serializer.deleteFileContent(searchNode);
            int[] positions = serializer.addFileContent(filePath);
            int lastContentPos = (positions.length == 0) ? 0 : positions[0];
            positions = (positions.length == 0) ? positions : Arrays.copyOfRange(positions, 1, positions.length);
            searchNode.setPositions(positions);
            searchNode.setLastBlockLength(lastContentPos);
        }
        serializer.writeAvlTree(tree);

        log.debug("\n ADD FILE\n tree.size = " + tree.size() + "\n");
        return true;
    }

    @Override
    public boolean deleteFile(String fileName) {
        Node node = tree.remove(getKeyID(fileName));
        if (node == null) {
            return false;
        }
        serializer.deleteFileContent(node);
        log.debug("\n DELETE FILE\n tree.size = " + tree.size() + "\n");
        serializer.writeAvlTree(tree);
        return true;
    }

    @Override
    public boolean contains(String fileName) {
        return (tree.find(getKeyID(fileName)) != null);
    }

    @Override
    public InputStream readFile(String filePath) {
        Node node = tree.find(getKeyID(filePath));
        log.debug("\n READ FILE: " + filePath + "\n");
        return (node == null) ? null : serializer.getInputStream(node);
    }

    @Override
    public void writeFile(String fileName, OutputStream stream) {
        Node node = tree.find(getKeyID(fileName));
        if (node == null) {
            int[] positions = serializer.addFileContent(stream);
            Node n = tree.new Node(getKeyID(fileName), positions[0], Arrays.copyOfRange(positions, 1, positions.length));
            tree.insert(n);
        } else {
            int[] positions = serializer.addNewFileContentToKeyNode(node, stream);
            node.setPositions(Arrays.copyOfRange(positions, 1, positions.length));
            node.setLastBlockLength(positions[0]);
        }
        log.debug("\n WRITE FILE with stream: " + fileName + "\n");

        serializer.writeAvlTree(tree);
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
