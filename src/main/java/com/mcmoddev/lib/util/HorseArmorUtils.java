package com.mcmoddev.lib.util;

import net.minecraft.entity.passive.HorseArmorType;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HorseArmorUtils {
    @Nullable
    public HorseArmorType createHorseArmorType(int strength, @Nonnull String name, @Nonnull String hash) {
        return EnumHelper.addEnum(HorseArmorType.class, name.toUpperCase().replace(" ", "_"), new Class[]{int.class, String.class, String.class}, strength, name, hash);
    }
}