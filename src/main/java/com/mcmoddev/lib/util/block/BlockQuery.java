package com.mcmoddev.lib.util.block;

import com.google.common.collect.Sets;
import com.mcmoddev.lib.common.block.IBlockPosQuery;
import com.mcmoddev.lib.exception.BlockQueryException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;

public class BlockQuery {
    public static IBlockPosQuery ANYTHING = (world, pos) -> true;
    public static IBlockPosQuery NOTHING = (world, pos) -> false;

    public interface IBlockQuery extends IBlockPosQuery {
        boolean matches(IBlockState state);
    }

    public static class BlockPosQueryAltitude implements IBlockPosQuery {
        public int minHeight;
        public int maxHeight;

        public BlockPosQueryAltitude(int minHeight, int maxHeight) {
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        }

        @Override
        public boolean matches(World world, BlockPos pos) {
            return pos.getY() >= this.minHeight && pos.getY() <= this.maxHeight;
        }
    }

    public static abstract class BlockQueryBase implements IBlockQuery {
        @Override
        public boolean matches(World world, BlockPos pos) {
            return this.matches(world.getBlockState(pos));
        }
    }


    public static class BlockQueryNot extends BlockQueryBase {
        IBlockQuery child;

        public BlockQueryNot(IBlockQuery child) {
            this.child = child;
        }

        @Override
        public boolean matches(IBlockState state) {
            return !this.child.matches(state);
        }
    }


    public static class BlockQueryBlock extends BlockQueryBase {
        private Set<Block> blocks;

        public BlockQueryBlock(Block... blocks) {
            this.blocks = Sets.newHashSet(blocks);
        }

        public static IBlockQuery of(String blockName, boolean negated) throws BlockQueryException {
            Block block = Block.getBlockFromName(blockName);
            if (block == null) {
                throw new BlockQueryException("No block called " + blockName);
            } else {
                IBlockQuery bm = new BlockQueryBlock(block);
                return negated ? new BlockQueryNot(bm) : bm;
            }
        }

        @Override
        public boolean matches(IBlockState state) {
            return this.blocks.contains(state.getBlock());
        }
    }

    public static class BlockQueryOreDict extends BlockQueryBase {
        private String oreDict;

        public BlockQueryOreDict(String oreDict) {
            this.oreDict = oreDict;
        }

        @Override
        public boolean matches(final IBlockState state) {
            Block block = state.getBlock();
            int[] oreDicts = OreDictionary.getOreIDs(new ItemStack(block, 1, block.getMetaFromState(state)));
            for (int i : oreDicts)
                if (this.oreDict.equals(OreDictionary.getOreName(i)))
                    return true;
            return false;
        }
    }
}