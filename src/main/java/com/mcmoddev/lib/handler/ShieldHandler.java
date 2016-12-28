package com.mcmoddev.lib.handler;

import com.mcmoddev.lib.item.ItemCustomShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ShieldHandler {

    @SubscribeEvent
    public void attackEvent(LivingAttackEvent e) {
        if (e.getEntityLiving().getActiveItemStack() == null)
            return;
        ItemStack stack = e.getEntityLiving().getActiveItemStack();
        if (stack.getItem() instanceof ItemCustomShield && e.getAmount() > 0.0f) {
            int i = 1 + MathHelper.floor(e.getAmount());
            stack.damageItem(i, e.getEntityLiving());
            if (stack.stackSize <= 0) {
                EnumHand enumhand = e.getEntityLiving().getActiveHand();
                if (e.getEntityLiving() instanceof EntityPlayer)
                    ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) e.getEntityLiving(), stack, enumhand);
                e.getEntityLiving().setItemStackToSlot(enumhand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, null);
                if (e.getEntityLiving().getEntityWorld().isRemote)
                    e.getEntityLiving().playSound(SoundEvents.BLOCK_ANVIL_BREAK, 0.8F, 0.8F + e.getEntityLiving().getEntityWorld().rand.nextFloat() * 0.4F);
            }
        }
    }
}