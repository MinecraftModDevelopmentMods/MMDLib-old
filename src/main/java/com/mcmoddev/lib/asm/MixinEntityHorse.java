package com.mcmoddev.lib.asm;

import com.google.common.base.Optional;
import com.mcmoddev.lib.common.item.IHorseArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHorse.class)
public abstract class MixinEntityHorse extends AbstractHorse {
    private static final DataParameter<Optional<ItemStack>> ARMOR_STACK = EntityDataManager.createKey(EntityHorse.class, (DataSerializer) DataSerializers.OPTIONAL_ITEM_STACK);

    @Shadow
    public abstract HorseArmorType getHorseArmorType();

    @Shadow
    public abstract int getHorseVariant();

    @Shadow
    @Final
    private String[] horseTexturesArray;

    @Inject(method = "entityInit", at = @At("HEAD"), cancellable = true)
    protected void entityInit(CallbackInfo ci) {
        this.getDataManager().register(ARMOR_STACK, Optional.absent());
    }

    @Inject(method = "setHorseArmorStack", at = @At("HEAD"), cancellable = true)
    protected void setHorseArmorStack(ItemStack itemStackIn, CallbackInfo ci) {
        this.getDataManager().set(ARMOR_STACK, Optional.fromNullable(itemStackIn));
    }

    @SideOnly(Side.CLIENT)
    @Inject(method = "setHorseTexturePaths", at = @At("TAIL"), cancellable = true)
    protected void setHorseTexturePaths(CallbackInfo ci) {
        this.horseTexturesArray[2] = getTextureName(getHorseArmorType(), this);
    }

    @SideOnly(Side.CLIENT)
    private static String getTextureName(HorseArmorType type, AbstractHorse entity) {
        final ItemStack stack = entity.getDataManager().get(ARMOR_STACK).orNull();
        if (stack != null && stack.getItem() instanceof IHorseArmor)
            return ((IHorseArmor) stack.getItem()).getArmorTexture(entity, stack);
        return type.getTextureName();
    }

    public MixinEntityHorse(World worldIn) {
        super(worldIn);
    }
}