package com.mcmoddev.lib.common.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockPosQuery {

    boolean matches (World world, BlockPos pos);
}