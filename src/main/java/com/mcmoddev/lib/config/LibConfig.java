package com.mcmoddev.lib.config;

import java.io.File;

import com.mcmoddev.lib.MMDLib;

import net.minecraft.util.ResourceLocation;

public class LibConfig extends MMDConfig {

    public static final ResourceLocation IDENTITY = new ResourceLocation(MMDLib.MOD_ID, "config");

    public LibConfig(File file) {
        super(file);
    }

    @Override
    public ResourceLocation getIdentifier () {
        return IDENTITY;
    }
}