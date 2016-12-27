package com.mcmoddev.libtest;

import java.util.Random;

import com.mcmoddev.mmdlib.worldgen.structure.TemplateProcessorChained;
import com.mcmoddev.mmdlib.worldgen.structure.TemplateProcessorColored;
import com.mcmoddev.mmdlib.worldgen.structure.TemplateProcessorIntegrity;

import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = TestTemplateStructures.MOD_ID, name = TestTemplateStructures.MOD_NAME, version = TestTemplateStructures.VERSION)
public class TestTemplateStructures {
    
    public static final String MOD_ID = "templatestructures";
    public static final String MOD_NAME = "Test Template Structures";
    public static final String VERSION = "0.0.1";
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        GameRegistry.registerWorldGenerator(new WorldGenFlower(), 50);
    }
    
    public static class WorldGenFlower implements IWorldGenerator {
        
        private static final ResourceLocation FLOWER = new ResourceLocation("libtest:flower");
        
        @Override
        public void generate (Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
            
            final BlockPos basePos = new BlockPos(chunkX * 16 + random.nextInt(16), 100, chunkZ * 16 + random.nextInt(16));
            final PlacementSettings settings = new PlacementSettings().setRotation(Rotation.NONE);
            final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), FLOWER);
            final TemplateProcessorChained processor = new TemplateProcessorColored((BlockColored) Blocks.WOOL).setChildProcessor(new TemplateProcessorIntegrity(0.9f));
            
            template.addBlocksToWorld(world, basePos, processor, settings, 2);
        }
    }
}