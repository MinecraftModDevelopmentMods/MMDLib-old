package com.mcmoddev.lib.util;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class TileHelper {

    /**
     * Gets a TileEntity of a specific instance.
     *
     * @param world World to find TileEntity in
     * @param blockPos Pos looking for TileEntity
     * @param tClass TileEntity class you're looking for
     * @param <T> TileEntity type you're looking for
     *
     * @return null or instance of T tile
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T getTileEntity (IBlockAccess world, BlockPos blockPos, Class<T> tClass) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return !tClass.isInstance(tileEntity) ? null : (T) tileEntity;
    }
}