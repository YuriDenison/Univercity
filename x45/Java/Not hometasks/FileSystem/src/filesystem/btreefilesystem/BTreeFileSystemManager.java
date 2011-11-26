package filesystem.btreefilesystem;

import filesystem.IFileSystemManager;

/**
 * Manager for BTree implementation of file system
 *
 * @author Yuri Denison
 * @see IFileSystemManager
 * @see filesystem.IFileSystem
 * @see BTreeFileSystem
 */
public class BTreeFileSystemManager implements IFileSystemManager {
    private BTreeSerializer serializer;
    private BTree tree;

    public static final int ORDER = 100;

    @Override
    public BTreeFileSystem createNewFileSystem(String filePath) {
        serializer = new BTreeSerializer(filePath, true);
        tree = serializer.getBTree();
        return new BTreeFileSystem(tree, serializer);
    }

    @Override
    public BTreeFileSystem openFileSystem(String filePath) {
        serializer = new BTreeSerializer(filePath, false);
        tree = serializer.getBTree();
        return new BTreeFileSystem(tree, serializer);
    }
}
