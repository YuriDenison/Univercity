package test.avltreefilesystem;

import filesystem.avltreefilesystem.AvlTreeFileSystem;
import filesystem.avltreefilesystem.AvlTreeFileSystemManager;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * AvlTreeFileSystem test class
 *
 * @author Yuri Denison
 */
public class AvlTreeFileSystemTest {
    private AvlTreeFileSystem fs;
    private AvlTreeFileSystemManager fileSystemManager;
    private static final int ITERATIONS = 1100;

    @Before
    public void setUp() throws Exception {
        PropertyConfigurator.configure("res/log4j.properties");
        fileSystemManager = new AvlTreeFileSystemManager();
    }

    @Test
    public void testCreateNewFile() throws Exception {
        fs = fileSystemManager.createNewFileSystem("test.fs");

        for (int i = 0; i < ITERATIONS; i++) {
            fs.createNewFile("File " + i);
            assertEquals(fs.getSize(), i + 1);
        }
    }

    @Test
    public void testInsertFile() throws Exception {
        fs = fileSystemManager.createNewFileSystem("test.fs");

        for (int i = 0; i < ITERATIONS; i++) {
            fs.insertFile("res/1.torrent", "file " + i);
            assertEquals(fs.getSize(), i + 1);
        }
    }

    @Test
    public void testDeleteFile() throws Exception {
        fs = fileSystemManager.createNewFileSystem("test.fs");

        for (int i = 0; i < ITERATIONS; i++) {
            fs.insertFile("res/1.torrent", Integer.toString(i));
            assertEquals(fs.getSize(), i + 1);
        }
        for (int i = 0; i < ITERATIONS; i++) {
            fs.deleteFile(Integer.toString(i));
            assertEquals(fs.getSize(), (ITERATIONS - i - 1));
        }
    }

    @Test
    public void testFind() throws Exception {

    }

    @Test
    public void testReadFile() throws Exception {
        fs = fileSystemManager.createNewFileSystem("test.fs");

        String filePath = "res/1.torrent";
        byte[] b = getExampleFile(filePath);

        String fileName = "torrent file";
        fs.insertFile(filePath, fileName);
        byte[] fsb = readByteArray(fileName);
        assertTrue(Arrays.equals(b, fsb));
    }

    private byte[] readByteArray(String fileName) throws IOException {
        ByteArrayInputStream stream = ((ByteArrayInputStream) fs.readFile(fileName));
        byte[] fsb = new byte[stream.available()];
        stream.read(fsb);
        stream.close();
        return fsb;
    }

    private byte[] getExampleFile(String filePath) throws IOException {
        File f = new File(filePath);
        FileInputStream fis = new FileInputStream(f);
        byte[] b = new byte[(int) f.length()];
        fis.read(b);
        fis.close();
        return b;
    }

    @Test
    public void testWriteFile() throws Exception {
        fs = fileSystemManager.openFileSystem("test.fs");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] b = getExampleFile("res/1.torrent");
        os.write(b);
        os.close();

        String fileName = "inputFile";
        fs.createNewFile(fileName);
        fs.writeFile(fileName, os);
        byte[] out = readByteArray(fileName);
        assertTrue(Arrays.equals(b, out));
    }

    @Test
    public void testOpenFileSystem() throws Exception {
        fs = fileSystemManager.createNewFileSystem("test.fs");

        fs.createNewFile("file 1");
        assertEquals(fs.getSize(), 1);

        AvlTreeFileSystem fs1 = fileSystemManager.openFileSystem("test.fs");
        assertEquals(fs1.getSize(), 1);
    }

    @Test
    public void testMixedAddDelete() throws Exception {
        fs = fileSystemManager.createNewFileSystem("test.fs");

        fs.insertFile("res/1.torrent", "file 1");
        fs.insertFile("res/lp-numb.txt", "file 2");
        fs.insertFile("res/1.torrent", "file 3");
        fs.deleteFile("file 2");
        fs.insertFile("res/1.torrent", "file 4");
        fs.deleteFile("file 1");
        fs.insertFile("res/lp-numb.txt", "file 5");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] b1 = getExampleFile("res/1.torrent");
        byte[] b2 = getExampleFile("res/lp-numb.txt");
        os.write(b1);
        os.write(b2);
        os.close();

        byte[] out1 = readByteArray("file 4");
        byte[] out2 = readByteArray("file 5");

        assertTrue(Arrays.equals(b1, out1));
        assertTrue(Arrays.equals(b2, out2));
    }

    @Test
    public void testContains() {
        fs = fileSystemManager.createNewFileSystem("test.fs");
        String fileName = "file 1";

        fs.insertFile("res/1.torrent", fileName);
        assertTrue(fs.contains(fileName));

        fs.deleteFile(fileName);
        assertTrue(!fs.contains(fileName));

    }
}
