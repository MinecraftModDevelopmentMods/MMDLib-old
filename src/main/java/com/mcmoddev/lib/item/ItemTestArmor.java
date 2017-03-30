package com.mcmoddev.lib.item;

import com.mcmoddev.lib.MMDLib;
import com.mcmoddev.lib.common.item.IHorseArmor;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemTestArmor extends Item implements IHorseArmor {

    public ItemTestArmor() {
        this.setRegistryName(MMDLib.MOD_ID, "test_horse_armor");
        this.setUnlocalizedName(MMDLib.MOD_ID + ".test_horse_armor");
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        GameRegistry.register(this);
    }

    @Override
    public HorseArmorType getArmorType () {
        return HorseArmorType.DIAMOND;
    }

    @Override
    public String getArmorTexture (AbstractHorse horse, ItemStack stack) {
        return "textures/entity/horse/armor/horse_armor_test.png";
    }
}