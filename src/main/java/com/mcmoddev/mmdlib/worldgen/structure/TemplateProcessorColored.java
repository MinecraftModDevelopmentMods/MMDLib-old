package com.mcmoddev.mmdlib.worldgen.structure;

import mmd.lib.MMDLib;
import net.minecraft.block.BlockColored;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

/**
 * This implementation of {@link ITemplateProcessor} will randomize the color for all instances
 * of a target block within the template.
 * 
 * @author Tyler Hancock (Darkhax)
 */
public class TemplateProcessorColored extends TemplateProcessorRandom {
    
    /**
     * The color to set the block to. If {@link #isSingleResult()} returns false, this will be
     * random for every target.
     */
    private EnumDyeColor color;
    
    /**
     * Basic constructor for the processor. Assumes that only one random color is needed for
     * all of the target blocks.
     * 
     * @param target The block to target.
     */
    public TemplateProcessorColored(BlockColored target) {
        
        super(target);
    }
    
    /**
     * Base constructor for the processor.
     * 
     * @param target target The block to target.
     * @param singleResult Whether or not all target blocks should be randomized in the same
     *        way.
     */
    public TemplateProcessorColored(BlockColored target, boolean singleResult) {
        
        super(target, singleResult);
    }
    
    @Override
    public BlockInfo handleBlock (World world, BlockPos pos, BlockInfo info) {
        
        if (info == null || info.blockState.getBlock() != this.getTarget())
            return info;
        
        if (this.color == null || !this.isSingleResult())
            this.color = EnumDyeColor.values()[MMDLib.RANDOM.nextInt(EnumDyeColor.values().length)];
        
        return new BlockInfo(info.pos, info.blockState.withProperty(BlockColored.COLOR, this.color), info.tileentityData);
    }
}
