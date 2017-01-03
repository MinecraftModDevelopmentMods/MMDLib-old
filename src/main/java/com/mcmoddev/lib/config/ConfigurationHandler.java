package com.mcmoddev.lib.config;

import java.util.ArrayList;
import java.util.List;

import com.mcmoddev.lib.exception.DuplicateObjectsException;

public enum ConfigurationHandler {
    INSTANCE;

    public List<MMDConfig> CONFIG_LIST = new ArrayList<>();

    public <T extends MMDConfig> T registerConfig (T config) {
        for (final MMDConfig existing : this.CONFIG_LIST)
            if (existing.getIdentifier().equals(config.getIdentifier()))
                throw new DuplicateObjectsException(existing, config);
        this.CONFIG_LIST.add(config);
        return config;
    }

    public void load () {
        this.CONFIG_LIST.forEach(MMDConfig::setup);
    }

    public void save () {
        this.CONFIG_LIST.forEach(config -> config.save(true));
    }
}