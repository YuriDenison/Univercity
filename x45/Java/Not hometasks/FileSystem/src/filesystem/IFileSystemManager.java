package filesystem;

/**
 * Initialization of file system (read from disk / create new)
 *
 * @author Yuri Denison
 */
public interface IFileSystemManager {
    /**
     * Creates new file system in input path
     *
     * @param path for file system
     * @return IFileSystem object
     */
    public IFileSystem createNewFileSystem(String path);

    /**
     * Opens existing on disk file system
     *
     * @param path of file system
     * @return IFileSystem object
     */
    public IFileSystem openFileSystem(String path);
}
