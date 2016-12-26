package com.mcmoddev.mmdlib.worldgen.structure;

import java.util.Random;

import mmd.lib.MMDLib;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

/**
 * A modified version of {@link BlockRotationProcessor}. This version has some key changes, the
 * most obvious being that the name of the class has been corrected to accurately describe what
 * the class does. This implementation also takes advantage of {@link TemplateProcessorChained}
 * to allow smoother integration with the rest of the processors.
 * 
 * Integrity is part of the vanilla structure format and is used as a way of adding
 * imperfections to a generated structure. The idea is that a percentage is passed to the
 * processor, and every block has that chance of NOT being set to air. Vanilla uses this in
 * their fossil structure to set roughly 10% of the bone blocks to coal.
 * 
 * @author Tyler Hancock (Darkhax)
 * @see BlockRotationProcessor
 */
public class TemplateProcessorIntegrity extends TemplateProcessorChained {
    
    /**
     * The chance that a block will be flawlessly copied from the template.
     */
    private final float chance;
    
    /**
     * An internal instance of random which is pulled from the placement settings.
     */
    private final Random random;
    
    /**
     * Original constructor for the integrity processor. Brought in from
     * {@link BlockRotationProcessor}.
     * 
     * @param pos The position of the structure.
     * @param settings The settings used to place the structure.
     */
    public TemplateProcessorIntegrity(BlockPos pos, PlacementSettings settings) {
        
        this(settings.getIntegrity(), settings.getRandom(pos));
    }
    
    /**
     * A light weight version of the constructor. This will use {@link MMDLib#RANDOM} for the
     * random value rather than the world seed.
     * 
     * @param chance The chance that a block will be flawlessly copied from the template.
     */
    public TemplateProcessorIntegrity(float chance) {
        
        this(chance, MMDLib.RANDOM);
    }
    
    /**
     * The base constructor for the processor.
     * 
     * @param chance The chance that a block will be flawlessly copied from the template.
     * @param random The instance of random used for RNG.
     */
    public TemplateProcessorIntegrity(float chance, Random random) {
        
        this.chance = chance;
        this.random = random;
    }
    
    @Override
    public BlockInfo handleBlock (World world, BlockPos pos, BlockInfo info) {
        
        return this.chance < 1.0F && this.random.nextFloat() > this.chance ? null : info;
    }
}
