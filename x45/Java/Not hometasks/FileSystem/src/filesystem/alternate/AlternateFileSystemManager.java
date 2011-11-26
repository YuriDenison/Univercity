package filesystem.alternate;

import filesystem.IFileSystemManager;

import java.net.URI;

/**
 * @author Yuri Denison; yuri.denison@gmail.com
 */

public class AlternateFileSystemManager implements IFileSystemManager {
    @Override
    public AlternateFileSystem createNewFileSystem(String path) {
        URI uri = URI.create("jar:file:" + path);
        return new AlternateFileSystem(uri).createNewFileSystem();
    }

    @Override
    public AlternateFileSystem openFileSystem(String path) {
        URI uri = URI.create("jar:file:" + path);
        return new AlternateFileSystem(uri).getFileSystem();
    }
}
