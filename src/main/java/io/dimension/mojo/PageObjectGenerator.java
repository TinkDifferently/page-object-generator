package io.dimension.mojo;

import io.dimension.config.Info;
import io.dimension.generator.PageLoader;
import io.dimension.log.Response;
import io.dimension.utils.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.nio.file.Paths;


@Mojo(name = "build-pages", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class PageObjectGenerator extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    public MavenProject project;

    @Parameter(defaultValue = "page", required = true, readonly = true)
    public String pagePatternPath;

    @Parameter(defaultValue = "element", required = true, readonly = true)
    public String elementPatternPath;

    @Parameter(defaultValue = "pages", required = true, readonly = true)
    public String sourcePagePath;

    @Parameter(required = true, readonly = true)
    public String targetPagePath;


    private void initLogger() {
        Response.init(msg -> this.getLog().info(msg), (msg, error) -> this.getLog().info(msg, error));
    }

    private void initData() {
        Info.init(project, elementPatternPath, pagePatternPath, sourcePagePath, targetPagePath);
    }

    private void clean() {
        var path = Paths.get("src/main/generated", Info.getInstance().getTargetPagePath());
        try {
            FileUtils.deleteDir(path);
        } catch (Exception any){
            getLog().debug("Could not clean base dir", any);
        }
    }

    private void loadPages() {
        PageLoader.getInstance().loadPages();
    }

    private void generatePages() {
        initLogger();
        initData();
        clean();
        loadPages();
    }

    @Override
    public void execute() {
        try {
            generatePages();
        } catch (Throwable any) {
            getLog().info(any);
        }
    }

    public static void main(String[] args) {
        Integer a=129;
        Integer b=129;
        System.out.println(a==b);
    }
}
