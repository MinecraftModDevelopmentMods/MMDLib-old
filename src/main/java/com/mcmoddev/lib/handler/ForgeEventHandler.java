package com.mcmoddev.lib.handler;

import com.mcmoddev.lib.common.MMDLibRegistry;
import com.mcmoddev.lib.common.crafting.IAnvilRecipe;
import com.mcmoddev.lib.item.ItemCustomShield;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {

    @SubscribeEvent
    public void attackEvent(LivingAttackEvent e) {
        if (e.getEntityLiving().getActiveItemStack() == null)
            return;
        final ItemStack stack = e.getEntityLiving().getActiveItemStack();
        if (stack.getItem() instanceof ItemCustomShield && e.getAmount() > 0.0f) {
            final int i = 1 + MathHelper.floor(e.getAmount());
            stack.damageItem(i, e.getEntityLiving());
            if (stack.getCount() <= 0) {
                final EnumHand enumhand = e.getEntityLiving().getActiveHand();
                if (e.getEntityLiving() instanceof EntityPlayer)
                    ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) e.getEntityLiving(), stack, enumhand);
                e.getEntityLiving().setItemStackToSlot(enumhand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, null);
                if (e.getEntityLiving().getEntityWorld().isRemote)
                    e.getEntityLiving().playSound(SoundEvents.BLOCK_ANVIL_BREAK, 0.8F, 0.8F + e.getEntityLiving().getEntityWorld().rand.nextFloat() * 0.4F);
            }
        }
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        for (final IAnvilRecipe recipe : MMDLibRegistry.getAnvilRecipes())
            if (recipe.isValidRecipe(event.getLeft(), event.getRight(), event.getName())) {
                event.setCost(recipe.getExperienceCost(event.getLeft(), event.getRight(), event.getName()));
                event.setMaterialCost(recipe.getMaterialCost(event.getLeft(), event.getRight(), event.getName()));
                event.setOutput(recipe.getOutput(event.getLeft(), event.getRight(), event.getName()));
                return;
            }
    }
}