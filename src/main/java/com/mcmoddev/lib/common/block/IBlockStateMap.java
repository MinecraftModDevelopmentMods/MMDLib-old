package com.mcmoddev.lib.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public interface IBlockStateMap {
    default ResourceLocation getResourceLocation(IBlockState state) {
        return state.getBlock().getRegistryName();
    }

    default String getVarient(IBlockState state) {
        return "normal";
    }
}
