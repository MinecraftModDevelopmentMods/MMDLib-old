package com.mcmoddev.mmdlib.worldgen.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

/**
 * This implementation of {@link ITemplateProcessor} allows for template processors from
 * outside sources to be wrapped into the chaining system. This is primarily useful when
 * working with vanilla or 3rd party mods.
 * 
 * @author Tyler Hancock (Darkhax)
 */
public class TemplateProcessorChainedWrapper extends TemplateProcessorChained {
    
    /**
     * The processor that is being wrapped.
     */
    private final ITemplateProcessor delegate;
    
    /**
     * Base constructor for the wrapper.
     * 
     * @param delegate The processor to wrap into the chained system.
     */
    public TemplateProcessorChainedWrapper(ITemplateProcessor delegate) {
        
        this.delegate = delegate;
    }
    
    @Override
    public BlockInfo handleBlock (World world, BlockPos pos, BlockInfo info) {
        
        return this.delegate.processBlock(world, pos, info);
    }
}
