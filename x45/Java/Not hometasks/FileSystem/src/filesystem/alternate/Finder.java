package filesystem.alternate;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Finder extends SimpleFileVisitor<Path> {
    private final PathMatcher matcher;
    private List<Path> matches;
    private int numMatches = 0;

    public Finder(FileSystem fileSystem, String pattern) {
        matches = new ArrayList<Path>();
        matcher = fileSystem.getPathMatcher("glob:" + pattern);
    }

    //Compares the glob pattern against the file or directory name.
    public void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            numMatches++;
            matches.add(file);
        }
    }

    //Prints the total number of matches to standard out.
    public List<String> getResult() {
        List<String> res = new LinkedList<String>();
        for (Path file : matches) {
            res.add(file.toString());
        }
        return res;
    }

    //Invoke the pattern matching method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    //Invoke the pattern matching method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

//   static void usage() {
//        System.err.println("java Finder <path> -name \"<glob_pattern>\"");
//        System.exit(-1);
//    }
//
//    public static void main(String[] args) throws IOException {
//
//        if (args.length < 3 || !args[1].equals("-name"))
//            usage();
//
//        Path startingDir = Paths.get(args[0]);
//        String pattern = args[2];
//
//        Finder finder = new Finder(FileSystems.getDefault(), pattern);
//        Files.walkFileTree(startingDir, finder);
//        for (String s : finder.getResult()) {
//            System.out.println(s);
//        }
//    }
}