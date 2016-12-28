package com.mcmoddev.lib.common;

import com.mcmoddev.lib.block.properties.PropertyObject;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Provides definitions for commonly used block state properties.
 *
 * @author Tyler Hancock (Darkhax)
 */
public class BlockStates {

    /**
     * Used by blocks which are powered by redstone.
     */
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    /**
     * Used to determine if the block is on or off. Similar to {@link #POWERED}
     */
    public static final PropertyBool ENABLED = PropertyBool.create("enabled");

    /**
     * Used to store another block states. Useful for mimicking behavior of other blocks.
     */
    public static final PropertyObject<IBlockState> HELD_STATE = new PropertyObject<>("held_state", IBlockState.class);

    /**
     * Holds a reference to the world context for a block. Effectively {@link World}.
     */
    public static final PropertyObject<IBlockAccess> BLOCK_ACCESS = new PropertyObject<>("world", IBlockAccess.class);

    /**
     * Holds a BlockPos.
     */
    public static final PropertyObject<BlockPos> BLOCKPOS = new PropertyObject<>("pos", BlockPos.class);

    /**
     * Holds a vanilla dye color.
     */
    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

    /**
     * Holds the facing direction of the block.
     */
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

    /**
     * Used to determine if a block is connected on the bottom face.
     */
    public static final PropertyBool CONNECTED_DOWN = PropertyBool.create("connected_down");

    /**
     * Used to determine if a block is connected on the upward face.
     */
    public static final PropertyBool CONNECTED_UP = PropertyBool.create("connected_up");

    /**
     * Used to determine if a block is connected on the northern face.
     */
    public static final PropertyBool CONNECTED_NORTH = PropertyBool.create("connected_north");

    /**
     * Used to determine if a block is connected on the southern face.
     */
    public static final PropertyBool CONNECTED_SOUTH = PropertyBool.create("connected_south");

    /**
     * Used to determine if a block is connected on the eastern face.
     */
    public static final PropertyBool CONNECTED_EAST = PropertyBool.create("connected_east");

    /**
     * Used to determine if a block is connected on the western face.
     */
    public static final PropertyBool CONNECTED_WEST = PropertyBool.create("connected_west");
}
