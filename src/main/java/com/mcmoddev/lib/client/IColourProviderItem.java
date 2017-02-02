package com.mcmoddev.lib.client;

import net.minecraft.item.ItemStack;

public interface IColourProviderItem {
    int getColorFromItemstack(ItemStack stack, int tintIndex);
}