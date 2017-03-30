package com.mcmoddev.lib.asm;

import com.mcmoddev.lib.common.item.IHorseArmor;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorseArmorType.class)
public class MixinEntityHorseArmor {

    @Shadow
    @Final
    public static HorseArmorType IRON;

    @Shadow
    @Final
    public static HorseArmorType GOLD;

    @Shadow
    @Final
    public static HorseArmorType DIAMOND;

    @Shadow
    @Final
    public static HorseArmorType NONE;

    @Inject(method = "getByItem", at = @At("HEAD"), cancellable = true)
    private static void getByItem(Item itemIn, CallbackInfoReturnable<HorseArmorType> cir) {
        if (itemIn instanceof IHorseArmor)
            cir.setReturnValue(((IHorseArmor) itemIn).getArmorType());
        else
            cir.setReturnValue(itemIn == Items.IRON_HORSE_ARMOR ? IRON : (itemIn == Items.GOLDEN_HORSE_ARMOR ? GOLD : (itemIn == Items.DIAMOND_HORSE_ARMOR ? DIAMOND : NONE)));
    }
}