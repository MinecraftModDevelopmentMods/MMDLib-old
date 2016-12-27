package com.mcmoddev.mmdlib.worldgen.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

/**
 * This implementation of {@link ITemplateProcessor} provides the ability to chain multiple
 * processors together. Many key areas of the vanilla code will only support one processor at a
 * time, however chaining provides a workaround to that.
 * 
 * @author Tyler Hancock (Darkhax)
 * @see Template#addBlocksToWorld(World, BlockPos,ITemplateProcessor, PlacementSettings, int)
 */
public abstract class TemplateProcessorChained implements ITemplateProcessor {
    
    /**
     * The child processor. Used to daisy chain multiple processors together.
     */
    private ITemplateProcessor child;
    
    @Override
    public BlockInfo processBlock (World world, BlockPos pos, BlockInfo info) {
        
        if (this.child != null)
            info = this.child.processBlock(world, pos, info);
        
        return this.handleBlock(world, pos, info);
    }
    
    /**
     * Gets the child processor for this processor.
     * 
     * @return The child processor.
     */
    public ITemplateProcessor getChildProcessor () {
        
        return this.child;
    }
    
    /**
     * Sets the child processor.
     * 
     * @param child The new child processor.
     * @return The same instance of the processor. This is to make building cleaner.
     */
    public TemplateProcessorChained setChildProcessor (ITemplateProcessor child) {
        
        this.child = child;
        return this;
    }
    
    /**
     * A delegate of {@link #processBlock(World, BlockPos, BlockInfo)}. This method is provided
     * for convenience, as classes would have to re-implement the child processor code on their
     * own. This method can be used to change the type of block in a structure template.
     * 
     * @param world The world where the structure is being constructed within.
     * @param pos The position of the block being processed.
     * @param info The initial instance of the BlockInfo. This will be info that the child
     *        provides.
     * @return The new BlockInfo object, this is the block that will be placed in the world.
     */
    public abstract BlockInfo handleBlock (World world, BlockPos pos, BlockInfo info);
}
