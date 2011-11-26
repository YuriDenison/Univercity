package filesystem.avltreefilesystem;

import filesystem.IFileSystemManager;

/**
 * Manager for BTree implementation of file system
 *
 * @author Yuri Denison
 * @see IFileSystemManager
 * @see filesystem.IFileSystem
 * @see AvlTreeFileSystem
 */
public class AvlTreeFileSystemManager implements IFileSystemManager {
    private AvlTreeSerializer serializer;
    private AvlTree tree;

    @Override
    public AvlTreeFileSystem createNewFileSystem(String filePath) {
        serializer = new AvlTreeSerializer(filePath, true);
        tree = new AvlTree(serializer);
        return new AvlTreeFileSystem(tree, serializer);
    }

    @Override
    public AvlTreeFileSystem openFileSystem(String filePath) {
        serializer = new AvlTreeSerializer(filePath, false);
        tree = serializer.readAvlTree();
        return new AvlTreeFileSystem(tree, serializer);
    }
}
