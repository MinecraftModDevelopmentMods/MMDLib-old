package com.mcmoddev.lib.common.item;

import net.minecraft.item.ItemStack;

public interface IItemStackQuery {

    boolean matches (ItemStack itemStack);
}