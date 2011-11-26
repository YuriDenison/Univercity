package filesystem;

import filesystem.btreefilesystem.BTreeFileSystemManager;
import filesystem.ui.ConsoleUI;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

/**
 * @author Yuri Denison
 */
public class Main {
    private static final String DEFAULT_PATH = "test.fs";

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure("res/log4j.properties");
        Logger log = Logger.getLogger(Main.class);

        String fileSystemPath;
        if (args.length != 1) {
            fileSystemPath = DEFAULT_PATH;
            log.debug("Use default path for file system location");
        } else {
            fileSystemPath = args[0];
            log.debug("Use '" + fileSystemPath + "' for file system location");
        }

        IFileSystem fileSystem = new BTreeFileSystemManager().openFileSystem(fileSystemPath);
        new ConsoleUI(fileSystem).start();
    }
}
