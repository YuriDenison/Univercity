package test.alternate;

import filesystem.alternate.AlternateFileSystem;
import filesystem.alternate.AlternateFileSystemManager;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * AlternateFileSystem test class
 *
 * @author Yuri Denison; yuri.denison@gmail.com
 */

public class AlternateFileSystemTest {
    private AlternateFileSystemManager manager;
    private AlternateFileSystem system;
    private static final String DEFAULT_PATH = "/home/volkman/testNIO.zip";

    @Before
    public void setUp() throws Exception {
        PropertyConfigurator.configure("res/log4j.properties");
        manager = new AlternateFileSystemManager();
    }

    @Test
    public void testCreateNewFile() throws Exception {
        system = manager.createNewFileSystem(DEFAULT_PATH);

        system.createNewFile("/lol1.txt");
        system.createNewFile("/lol2.txt");

        assertTrue(system.contains("/lol1.txt"));
        assertTrue(!system.contains("/lol3.txt"));
    }

    @Test
    public void testInsertFile() throws Exception {
        system = manager.createNewFileSystem(DEFAULT_PATH);

    }

    @Test
    public void testDeleteFile() throws Exception {
        system = manager.createNewFileSystem(DEFAULT_PATH);

    }

    @Test
    public void testFind() throws Exception {
        system = manager.createNewFileSystem(DEFAULT_PATH);

    }

    @Test
    public void testContains() throws Exception {
        system = manager.createNewFileSystem(DEFAULT_PATH);

    }

    @Test
    public void testReadFile() throws Exception {
        system = manager.createNewFileSystem(DEFAULT_PATH);

    }

    @Test
    public void testWriteFile() throws Exception {
        system = manager.createNewFileSystem(DEFAULT_PATH);

    }
}
