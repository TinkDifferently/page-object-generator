package io.dimension.generator;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.dimension.config.Info;
import lombok.Data;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "page")
public class PageBuilder {

    private String bind;
    private String name;
    private List<ElementBuilder> elements;

    @SneakyThrows
    public void build(String target) {
        var builder = new StringBuilder(Info.getInstance().getPagePattern()
                .replaceFirst("\\$pageBindPlaceHolder", bind)
                .replaceFirst("\\$pageNamePlaceHolder", name));
        for (var element : elements) {
            builder.append(element.build()).append('\n');
        }
        builder.append('}');
        var path = Paths.get("src/main/generated", target);
        path = Files.createDirectories(path).resolve(name + ".java");
        Files.write(path, builder.toString().getBytes(StandardCharsets.UTF_8));
    }
}
