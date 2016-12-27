package mmd.lib.config;

import mmd.lib.MMDLib;
import net.minecraft.util.ResourceLocation;

import java.io.File;

public class LibConfig extends MMDConfig {

	public static final ResourceLocation IDENTITY = new ResourceLocation(MMDLib.MOD_ID, "config");

	public LibConfig(File file) {
		super(file);
	}

	@Override
	public ResourceLocation getIdentifier() {
		return IDENTITY;
	}
}