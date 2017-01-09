package com.mcmoddev.lib.common;

import java.util.ArrayList;
import java.util.List;

import com.mcmoddev.lib.common.crafting.AnvilRecipe;
import com.mcmoddev.lib.common.crafting.IAnvilRecipe;

import net.minecraft.item.ItemStack;

/**
 * A centralized location for all registries provided by MMDLib, along with related helper
 * methods.
 *
 * @author Tyler Hancock (Darkhax)
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
     * @param leftSlot The item required in the left slot.
     * @param rightSlot The item required in the right slot.
     * @param experience The exp cost for the recipe.
     * @param materialCost The material cost for the recipe. 0 means all!
     * @param output The stack to give as an output.
     */
    public static void addAnvilRecipe (ItemStack leftSlot, ItemStack rightSlot, int experience, int materialCost, ItemStack output) {
        addAnvilRecipe(leftSlot, rightSlot, null, experience, materialCost, output);
    }

    /**
     * Adds a new anvil recipe to the list.
     *
     * @param leftSlot The item required in the left slot.
     * @param rightSlot The item required in the right slot.
     * @param requiredName The string required in the name field.
     * @param experience The exp cost for the recipe.
     * @param materialCost The material cost for the recipe. 0 means all!
     * @param output The stack to give as an output.
     */
    public static void addAnvilRecipe (ItemStack leftSlot, ItemStack rightSlot, String requiredName, int experience, int materialCost, ItemStack output) {
        addAnvilRecipe(new AnvilRecipe(leftSlot, rightSlot, requiredName, experience, materialCost, output));
    }

    /**
     * Adds a new anvil recipe to the list.
     *
     * @param recipe The recipe to add.
     */
    public static void addAnvilRecipe (AnvilRecipe recipe) {
        ANVIL_RECIPES.add(recipe);
    }

    /**
     * Retrieves the List of all registered anvil recipes.
     *
     * @return List<AnvilRecipe> A List of all AnvilRecipes that have been registered with
     *         booksehfl.
     */
    public static List<IAnvilRecipe> getAnvilRecipes () {
        return ANVIL_RECIPES;
    }
}