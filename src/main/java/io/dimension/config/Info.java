package io.dimension.config;

import io.dimension.utils.FileUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Info {
    @Nullable
    private static Info instance;

    @Getter
    @NotNull
    private final String elementPattern;

    @Getter
    @NotNull
    private final String pagePattern;

    @Getter
    @NotNull
    private final String sourcePagePath;

    @Getter
    @NotNull
    private final String targetPagePath;

    private Info(@NotNull String elementPattern, @NotNull String pagePattern, @NotNull String sourcePagePath, @NotNull String targetPagePath) {
        this.elementPattern = elementPattern;
        this.pagePattern = pagePattern;
        this.sourcePagePath = sourcePagePath;
        this.targetPagePath = targetPagePath;
    }

    @SneakyThrows
    public static void init(@NotNull MavenProject project, @NotNull String elementPatternPath, @NotNull String pagePatternPath, @NotNull String sourcePagePath, @NotNull String targetPagePath) {
        List<Resource> resources = project.getResources();
        var resource = resources.get(0).getDirectory();
        var file = FileUtils.getFile(resource, path -> path.toFile().getName().equals(elementPatternPath + ".pattern"));
        var elementPattern = Files.readString(file);
        file = FileUtils.getFile(resource, path -> path.toFile().getName().equals(pagePatternPath + ".pattern"));
        var pagePattern = Files.readString(file);
        instance = new Info(elementPattern, pagePattern, resources.get(0).getDirectory()+ File.separator+sourcePagePath, targetPagePath);
    }

    @Override
    public String toString() {
        return "Info{" +
                "elementPattern='" + elementPattern + '\'' +
                ", pagePattern='" + pagePattern + '\'' +
                ", sourcePagePath='" + sourcePagePath + '\'' +
                ", targetPagePath='" + targetPagePath + '\'' +
                '}';
    }

    @Contract(pure = true)
    public static Info getInstance() {
        if (instance == null) {
            throw new RuntimeException();
        }
        return instance;
    }


}
