package com.mcmoddev.lib.item;

import com.mcmoddev.lib.MMDLib;
import com.mcmoddev.lib.common.item.IHorseArmor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemTestArmor extends Item implements IHorseArmor {
    public ItemTestArmor() {
        setRegistryName(MMDLib.MOD_ID, "test_horse_armor");
        setUnlocalizedName(MMDLib.MOD_ID + ".test_horse_armor");
        setCreativeTab(CreativeTabs.DECORATIONS);
        GameRegistry.register(this);
    }

    @Override
    public HorseArmorType getArmorType() {
        return HorseArmorType.DIAMOND;
    }

    @Override
    public String getArmorTexture(EntityHorse horse, ItemStack stack) {
        return "textures/entity/horse/armor/horse_armor_test.png";
    }
}