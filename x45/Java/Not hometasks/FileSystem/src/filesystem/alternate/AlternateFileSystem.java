package filesystem.alternate;

import filesystem.IFileSystem;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * File system implementation that uses NIO 2 (JDK 7)
 *
 * @author Yuri Denison
 */
public class AlternateFileSystem implements IFileSystem {
    private FileSystem fileSystem;
    private Logger log;
    private URI uri;

    public AlternateFileSystem(URI uri) {
        log = Logger.getLogger(AlternateFileSystem.class);
        this.uri = uri;
    }

    @Override
    public boolean createNewFile(String fileName) {
        Path path = fileSystem.getPath(fileName);
        try {
            Files.createFile(path);
            return true;
        } catch (Exception e) {
            log.error("Exception in insertFile:", e);
            return false;
        }
    }

    @Override
    public boolean insertFile(String file, String fsPath) {
        Path filePath = Paths.get(file);
        Path fs = fileSystem.getPath(fsPath);
        // copy a file into the zip file
        try {
            Files.copy(filePath, fs, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            log.error("Exception in insertFile:", e);
            return false;
        }
    }

    @Override
    public boolean deleteFile(String filePath) {
        try {
            Files.delete(fileSystem.getPath(filePath));
            return true;
        } catch (Exception e) {
            log.error("Error deleting path: ", e);
            return false;
        }
    }


    public List<String> find(String pattern) {
        log.debug("find: " + pattern);
        try {
            Finder finder = new Finder(fileSystem, pattern);
            Files.walkFileTree(fileSystem.getPath(""), finder);
            return finder.getResult();
        } catch (Exception e) {
            log.error("Exception in find:", e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean contains(String fileName) {
        return fileName.contains(fileName);
    }

    @Override
    public InputStream readFile(String filePath) {
        try {
            return Files.newInputStream(fileSystem.getPath(filePath));
        } catch (IOException e) {
            log.error("Error getting input stream: ", e);
            return null;
        }
    }

    @Override
    public void writeFile(String fileName, OutputStream stream) {
        try {
            Files.write(fileSystem.getPath(fileName), ((ByteArrayOutputStream) stream).toByteArray());
        } catch (IOException e) {
            log.error("Error writing file content: ", e);
        }
    }

    @Override
    public void close() {
        try {
            fileSystem.close();
        } catch (IOException e) {
            log.error("Error closing file system: ", e);
        }
    }

    public AlternateFileSystem createNewFileSystem() {
        try {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");

            fileSystem = FileSystems.newFileSystem(uri, env);
            log.debug("File system created.");
            return this;
        } catch (Exception e1) {
            log.error("Error creating file system. ", e1);
            System.exit(-1);
        }
        return null;
    }

    public AlternateFileSystem getFileSystem() {
        try {
            fileSystem = FileSystems.getFileSystem(uri);
            log.debug("File system opened.");
            return this;
        } catch (Exception e) {
            log.error("Error opening file system: ", e);
            return createNewFileSystem();
        }
    }
}
