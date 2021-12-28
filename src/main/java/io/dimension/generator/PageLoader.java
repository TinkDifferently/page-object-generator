package io.dimension.generator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.dimension.config.Info;
import io.dimension.log.Response;
import io.dimension.utils.FileUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.nio.file.Path;

public class PageLoader {
    XmlMapper mapper;

    private PageLoader() {
        mapper = new XmlMapper();
    }

    private static PageLoader instance = new PageLoader();

    public static PageLoader getInstance() {
        return instance;
    }

    private void loadPage(Path path) {
        try {
            Response.getInstance().info(String.format("Building page: '%s'", path.getFileName()));
            XMLStreamReader sr = XMLInputFactory.newFactory().createXMLStreamReader(new FileInputStream(path.toFile()));
            PageBuilder page = mapper.readValue(sr, PageBuilder.class);
            page.build(Info.getInstance().getTargetPagePath());
        } catch (Exception e) {
            throw new RuntimeException("Could not load pages", e);
        }
    }

    public void loadPages() {
        Response.getInstance().info("Building pages");
        Response.getInstance().info(Info.getInstance().getSourcePagePath());
        FileUtils.getFiles(Info.getInstance().getSourcePagePath(), path -> path.toFile().getName().endsWith(".xml")).forEach(this::loadPage);
    }
}
