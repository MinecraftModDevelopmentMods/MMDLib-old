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
    GITIGNORE("gitignore"),
    JAVA("java"),
    GZIP("gzip"),
    LOG("log"),
    DAT("dat"),
    OBJ("obj"),
    CLASS("class");

    private String file;

    NameFilters(String file) {
        this.file = file;
    }

    public String getFile () {
        return this.file;
    }

    @Override
    public boolean accept (File dir, String name) {
        return name.toLowerCase().endsWith("." + this.file.toLowerCase());
    }
}