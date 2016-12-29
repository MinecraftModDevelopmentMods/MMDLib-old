package com.mcmoddev.lib.util.block;

import com.mcmoddev.lib.common.item.IItemStackQuery;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackQuery {
    public static IItemStackQuery ANYTHING = (stack) -> true;
    public static IItemStackQuery NOTHING = (stack) -> false;

    public static interface IItemStackNBTQuery extends IItemStackQuery {
        boolean matches(NBTTagCompound nbt);
    }

    public static class ItemQuery implements IItemStackQuery {
        protected Item item;

        public ItemQuery(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }

        @Override
        public boolean matches(ItemStack itemStack) {
            return itemStack.getItem().equals(this.item);
        }
    }

    public static class ItemMetaQuery extends ItemQuery {
        protected int meta;

        public ItemMetaQuery(Item item, int meta) {
            super(item);
            this.meta = meta;
        }

        public int getMeta() {
            return meta;
        }

        @Override
        public boolean matches(ItemStack itemStack) {
            return itemStack.getItem().equals(this.item) && itemStack.getMetadata() == this.meta;
        }
    }

    public abstract static class ItemNBTQueryBase implements IItemStackNBTQuery {
        protected ItemStack stack;

        public ItemNBTQueryBase(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean matches(ItemStack itemStack) {
            return this.matches(itemStack.getTagCompound());
        }
    }

    public static class ItemNBTQueryColored extends ItemNBTQueryBase {
        protected int color;

        public ItemNBTQueryColored(ItemStack stack, int color) {
            super(stack);
            this.color = color;
        }

        public ItemNBTQueryColored(ItemStack stack, EnumDyeColor color) {
            this(stack, color.getMapColor().colorValue);
        }

        public int getColor() {
            return color;
        }

        @Override
        public boolean matches(ItemStack itemStack) {
            return itemStack.getItem() == Items.DYE ? EnumDyeColor.byDyeDamage(itemStack.getMetadata()).getMapColor().colorValue == this.color : super.matches(itemStack);
        }

        @Override
        public boolean matches(NBTTagCompound nbt) {
            return nbt.hasKey("color", 3) && nbt.getInteger("color") == this.color || nbt.hasKey("display", 10) && nbt.getCompoundTag("display").hasKey("color", 3) && nbt.getCompoundTag("display").getInteger("color") == this.color;
        }
    }

}