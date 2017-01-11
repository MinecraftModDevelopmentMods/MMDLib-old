package com.mcmoddev.lib.client.particle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.world.World;

/**
 * The constructor for {@link net.minecraft.client.particle.ParticleDigging} is made private,
 * making it hard to use. This wrapper class makes the constructor public.
 * 
 * @author Tyler Hancock (Darkhax)
 */
public class ParticleDiggingOpen extends ParticleDigging {
    
    /**
     * Constructs a new ParticleDigging. The constructor in ParticleDigging is not publicly
     * available, so a work around is required if you want to spawn one of those. This version
     * of the particle opens up that constructor.
     * 
     * @param world The World instance to spawn the particle in.
     * @param x The X position for the particle.
     * @param y The Y position for the particle.
     * @param z The Z position for the particle.
     * @param xSpeed The velocity of the particle on the X axis.
     * @param ySpeed The velocity of the particle on the Y axis.
     * @param zSpeed The velocity of the particle on the Z axis.
     * @param state The IBlockState of the block to create the particle for.
     */
    public ParticleDiggingOpen(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, IBlockState state) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed, state);
    }
}