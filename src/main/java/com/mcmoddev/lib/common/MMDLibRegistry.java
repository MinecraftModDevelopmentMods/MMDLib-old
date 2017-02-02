package com.mcmoddev.lib.common;

import java.util.ArrayList;
import java.util.List;

import com.mcmoddev.lib.MMDLib;
import com.mcmoddev.lib.client.IColourProviderItem;
import com.mcmoddev.lib.client.IModelAware;
import com.mcmoddev.lib.common.block.IBlockStateMap;
import com.mcmoddev.lib.common.crafting.AnvilRecipe;
import com.mcmoddev.lib.common.crafting.IAnvilRecipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * A centralized location for all registries provided by MMDLib, along with related helper
 * methods.
 *
 * @author Tyler Hancock (Darkhax), TheCodedOne
 */
public class MMDLibRegistry {

    /**
     * A List of all the anvil recipes that have been registered with Bookshelf.
     */
    // TODO Add support for oredict recipes by default.
    // TODO add JEI support for showing recipes!
    private static final List<IAnvilRecipe> ANVIL_RECIPES = new ArrayList<>();

    /**
     * Adds a new anvil recipe to the list. This recipe will have no name requirement.
     *
     * @param leftSlot     The item required in the left slot.
     * @param rightSlot    The item required in the right slot.
     * @param experience   The exp cost for the recipe.
     * @param materialCost The material cost for the recipe. 0 means all!
     * @param output       The stack to give as an output.
     */
    public static void addAnvilRecipe(ItemStack leftSlot, ItemStack rightSlot, int experience, int materialCost, ItemStack output) {
        addAnvilRecipe(leftSlot, rightSlot, null, experience, materialCost, output);
    }

    /**
     * Adds a new anvil recipe to the list.
     *
     * @param leftSlot     The item required in the left slot.
     * @param rightSlot    The item required in the right slot.
     * @param requiredName The string required in the name field.
     * @param experience   The exp cost for the recipe.
     * @param materialCost The material cost for the recipe. 0 means all!
     * @param output       The stack to give as an output.
     */
    public static void addAnvilRecipe(ItemStack leftSlot, ItemStack rightSlot, String requiredName, int experience, int materialCost, ItemStack output) {
        addAnvilRecipe(new AnvilRecipe(leftSlot, rightSlot, requiredName, experience, materialCost, output));
    }

    /**
     * Adds a new anvil recipe to the list.
     *
     * @param recipe The recipe to add.
     */
    public static void addAnvilRecipe(AnvilRecipe recipe) {
        ANVIL_RECIPES.add(recipe);
    }

    /**
     * Retrieves the List of all registered anvil recipes.
     *
     * @return List<AnvilRecipe> A List of all AnvilRecipes that have been registered with
     * bookshelf.
     */
    public static List<IAnvilRecipe> getAnvilRecipes() {
        return ANVIL_RECIPES;
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemColour(Object object, IItemColor colourProvider) {
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        if (object instanceof Item)
            itemColors.registerItemColorHandler(colourProvider, (Item) object);
        else if (object instanceof Block)
            itemColors.registerItemColorHandler(colourProvider, (Block) object);
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColour(Block block) {
        if (!(block instanceof IBlockColor)) return;
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((IBlockColor) block, block);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModel(Item item, int metadata, ResourceLocation resourceLocation) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(resourceLocation, ""));
    }

    @SideOnly(Side.CLIENT)
    public static void registerStateMapper(Block block) {
        if (!(block instanceof IBlockStateMap)) return;
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(((IBlockStateMap) block).getResourceLocation(state), ((IBlockStateMap) block).getVarient(state));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels(IModelAware modelAware) {
        if (modelAware instanceof Item) {
            List<ItemStack> allSubItems = new ArrayList<>();
            ((Item) modelAware).getSubItems((Item) modelAware, null, allSubItems);
            int index = 0;
            List<ModelResourceLocation> modelResourceLocations = modelAware.getModelResourceLocations(new ArrayList<>());
            if (!modelResourceLocations.isEmpty()) {
                for (ItemStack stack : allSubItems) {
                    ModelResourceLocation location = modelResourceLocations.get(index);
                    if (stack != null && location != null) {
                        ModelLoader.setCustomModelResourceLocation(stack.getItem(), stack.getMetadata(), location);
                        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(stack.getItem(), stack.getMetadata(), location);
                        index++;
                        if (index >= modelResourceLocations.size())
                            index = 0;
                    }
                }
            }
        }
    }
}