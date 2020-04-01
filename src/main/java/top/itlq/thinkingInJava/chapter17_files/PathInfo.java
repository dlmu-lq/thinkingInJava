package top.itlq.thinkingInJava.chapter17_files;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class PathInfo {
    public static void main(String[] args) {
        info(Paths.get("PathInfo.class"));
        info(Paths.get("c:"));
        info(Paths.get("e:","projects"));
    }

    private static void info(Path p){
        System.out.println("========" + p + "========");
        System.out.println("Abosolute: " + p.isAbsolute());
        System.out.println("Exists: " + Files.exists(p));
        System.out.println("Directory: " + Files.isDirectory(p));
        System.out.println("RegularFile: " + Files.isRegularFile(p));
    }
}
