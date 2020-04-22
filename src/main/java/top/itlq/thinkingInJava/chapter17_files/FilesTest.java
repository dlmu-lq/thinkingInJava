package top.itlq.thinkingInJava.chapter17_files;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Files的一些静态方法
 */
public class FilesTest {

    @Test
    public void testWalkFileTree() throws IOException {
        Files.walkFileTree(Paths.get("src\\main\\java\\top\\itlq"), new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException
            {
                Objects.requireNonNull(file);
                Objects.requireNonNull(attrs);
                System.out.println(file);
                System.out.println(attrs);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException
            {
                Objects.requireNonNull(dir);
                if (exc != null)
                    throw exc;
                System.out.println(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    public void testReadAllLines() throws IOException {
        Files.readAllLines(Paths.get("src\\main\\java\\top\\itlq\\thinkingInJava\\chapter17_files\\PathInfo.java"))
                .stream()
                .forEach(System.out::println);
        Files.write(
                Paths.get("src\\main\\java\\top\\itlq\\thinkingInJava\\chapter17_files\\test.write"),
                "test".getBytes(Charset.forName("utf-8")));
    }

    /**
     * 流式读取，边消费边读取，可用来读大文件
     * @throws IOException
     */
    @Test
    public void testLines() throws IOException, URISyntaxException {
        try (
//                Stream<String> lineStream = Files.lines(Paths.get("src\\main\\java\\top\\itlq\\thinkingInJava\\chapter17_files\\PathInfo.java"))
                Stream<String> lineStream = Files.lines(Paths.get(FilesTest.class.getResource("/files/bigFile").toURI()))
        ){
            lineStream.skip(100).limit(4).forEach(System.out::println);
        };
    }
}
