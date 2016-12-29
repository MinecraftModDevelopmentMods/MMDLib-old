package com.mcmoddev.lib.util.block;

import com.mcmoddev.lib.common.item.IItemStackQuery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackQuery {
    public static IItemStackQuery ANYTHING = (stack) -> true;
    public static IItemStackQuery NOTHING = (stack) -> false;

    public static class ItemQuery implements IItemStackQuery {
        private Item item;

        public ItemQuery(Item item) {
            this.item = item;
        }

        @Override
        public boolean matches(ItemStack itemStack) {
            return itemStack.getItem().equals(this.item);
        }
    }
}