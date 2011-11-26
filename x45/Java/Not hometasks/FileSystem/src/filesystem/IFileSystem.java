package filesystem;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface describes possibilities of my file system
 *
 * @author Yuri Denison
 */
public interface IFileSystem {
    /**
     * Create new file in the FileSystem
     *
     * @param fileName name for new file
     * @return true if operation is successful, false if not
     */
    public boolean createNewFile(String fileName);

    /**
     * Copy file to fileSystem
     *
     * @param filePath    path on the disk
     * @param newFileName name in the fileSystem
     * @return true if operation is successful, false if not
     */
    public boolean insertFile(String filePath, String newFileName);

    /**
     * Delete file from the filesystem.
     *
     * @param fileName name of the file
     * @return true if operation is successful, false if not
     */
    public boolean deleteFile(String fileName);

    /**
     * Check contains file with input name in file system
     *
     * @param fileName search file name
     * @return if contains returns true
     */
    public boolean contains(String fileName);

    /**
     * Get Input stream to read existing file.
     *
     * @param filePath path of the reading file
     * @return InputStream object if file exists in the filesystem, null of not exists
     */
    public InputStream readFile(String filePath);

    /**
     * Modify file content from OutputStream
     *
     * @param fileName name of file
     * @param stream   OutputStream for file
     */
    public void writeFile(String fileName, OutputStream stream);

    /**
     * Close file system on exit
     */
    public void close();
}
