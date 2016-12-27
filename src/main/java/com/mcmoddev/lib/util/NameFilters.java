package com.mcmoddev.lib.util;

import java.io.File;
import java.io.FilenameFilter;

public enum NameFilters implements FilenameFilter {
    JSON("json"),
    CFG("cfg"),
    CONF("conf"),
    ZENSCRIPT("zs"),
    ZIP("zip"),
    TXT("txt"),
    PNG("png"),
    JAR("jar"),
    EXECUTABLE("exe"),
    LOG("log"),
    DAT("dat"),
    OBJ("obj"),
    CLASS("class");

    private String file;

    NameFilters(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith("." + this.file.toLowerCase());
    }
}