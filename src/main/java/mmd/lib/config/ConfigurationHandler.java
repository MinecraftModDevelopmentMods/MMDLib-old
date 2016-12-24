package mmd.lib.config;

import mmd.lib.exception.DuplicateObjectsException;

import java.util.ArrayList;
import java.util.List;

public enum ConfigurationHandler {
    INSTANCE;

    public List<MMDConfig> CONFIG_LIST = new ArrayList<MMDConfig>();

    public <T extends MMDConfig> T registerConfig(T config) {
        for (MMDConfig existing : CONFIG_LIST)
            if (existing.getIdentifier().equals(config.getIdentifier()))
                throw new DuplicateObjectsException(existing, config);
        CONFIG_LIST.add(config);
        return config;
    }

    public void load() {
        CONFIG_LIST.forEach(MMDConfig::setup);
    }

    public void save() {
        CONFIG_LIST.forEach(config -> config.save(true));
    }
}