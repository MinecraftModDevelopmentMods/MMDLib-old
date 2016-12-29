package com.mcmoddev.lib.common.item;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IItemStackQuery {
    boolean matches(World world, BlockPos pos);
}