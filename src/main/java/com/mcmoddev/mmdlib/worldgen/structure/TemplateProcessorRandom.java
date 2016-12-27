package com.mcmoddev.mmdlib.worldgen.structure;

import net.minecraft.block.Block;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;

/**
 * This implementation of {@link ITemplateProcessor} will randomize the color for all instances
 * of a target block within the template.
 * 
 * @author Tyler Hancock (Darkhax)
 */
public abstract class TemplateProcessorRandom extends TemplateProcessorChained {
    
    /**
     * The block for this processor to target. Random processors will only modify these blocks.
     */
    private final Block target;
    
    /**
     * Whether or not all blocks should be randomized with the same result. For example, if you
     * want to randomize a wool carpet to be all one random color, this should be true.
     */
    private final boolean singleResult;
    
    /**
     * Constructor for the random template processor. Assumes that only one random result will
     * be generated and used for all the blocks.
     * 
     * @param target The block to target.
     */
    public TemplateProcessorRandom(Block target) {
        
        this(target, true);
    }
    
    /**
     * The base constructor for the random template processor.
     * 
     * @param target The block to target.
     * @param singleResult Whether or not all target blocks should be randomized in the same
     *        way.
     */
    public TemplateProcessorRandom(Block target, boolean singleResult) {
        
        this.target = target;
        this.singleResult = singleResult;
    }
    
    /**
     * Gets the block that is being targeted by the processor.
     * 
     * @return The block that is being targeted by the processor.
     */
    public Block getTarget () {
        
        return this.target;
    }
    
    /**
     * Checks if the processor should randomize all target blocks in the same way.
     * 
     * @return Whether or not all target blocks should be randomized int he same way.
     */
    public boolean isSingleResult () {
        
        return this.singleResult;
    }
}
