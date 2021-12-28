package io.dimension.generator;

import io.dimension.config.Info;
import lombok.Data;

@Data
class ElementBuilder {
    private String androidPath;
    private String iosPath;
    private String bind;
    private String name;
    private String type;
    private boolean collection = false;
    private String androidStrategy = "xpath";
    private String iosStrategy = "iOSClassChain";


    String build() {
        return Info.getInstance().getElementPattern().replaceFirst("\\$elementBindPlaceHolder", bind)
                .replaceFirst("\\$elementNamePlaceHolder", name)
                .replaceAll("\\$elementTypePlaceHolder", type)
                .replaceFirst("\\$androidByPlaceHolder", String.format("MobileBy.%s(\"%s\")", androidStrategy, androidPath))
                .replaceFirst("\\$iosByPlaceHolder", String.format("MobileBy.%s(\"%s\")", iosStrategy, iosPath));
    }
}
