package io.dimension.utils;

import edu.emory.mathcs.backport.java.util.Collections;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class FileUtils {

    private FileUtils() {

    }

    public static List<Path> getFiles(String baseDir, Predicate<Path> filter) {
        try {
            try (var stream = Files.walk(Paths.get(baseDir), 100)) {
                return stream
                        .filter(filter)
                        .collect(Collectors.toList());
            }
        } catch (Exception any) {
            return (List<Path>) Collections.emptyList();
        }
    }

    public static List<Path> getFiles(String baseDir) {
        try {
            try (var stream = Files.walk(Paths.get(baseDir), 100)) {
                return stream
                        .collect(Collectors.toList());
            }
        } catch (Exception any) {
            return (List<Path>) Collections.emptyList();
        }
    }

    public static Path getFile(String baseDir, Predicate<Path> filter) {
        try (var stream = Files.walk(Paths.get(baseDir), 100)) {
            return stream.filter(filter)
                    .findFirst()
                    .orElseThrow();
        } catch (Exception any) {
            throw new RuntimeException(any);
        }
    }

    @SneakyThrows
    public static void deleteDir(Path path){
        Files.walkFileTree(path,
                new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult postVisitDirectory(
                            Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(
                            Path file, BasicFileAttributes attrs)
                            throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                });

    }
}
