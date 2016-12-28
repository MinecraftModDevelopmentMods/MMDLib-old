package com.mcmoddev.lib.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModUtils {

    /**
     * Attempts to get the name of the mod which registered the passed object. Minecraft will
     * be returned for vanilla content while Unknown will be used for invalid cases.
     *
     * @param registerable The object to get the mod name for.
     * @return The name of the mod which registered the object.
     */
    public static String getModName (IForgeRegistryEntry.Impl<?> registerable) {

        final String modID = registerable.getRegistryName().getResourceDomain();
        final ModContainer mod = getModContainer(modID);
        return mod != null ? mod.getName() : modID.equalsIgnoreCase("minecraft") ? "Minecraft" : "Unknown";
    }

    /**
     * Attempts to get the name of the mod which registered the entity. Minecraft will be
     * returned for vanilla content while Unknown will be used for invalid cases.
     *
     * @param entity The entity to get the mod name for.
     * @return The name of the mod which registered the entity.
     */
    public static String getModName (Entity entity) {

        if (entity == null)
            return "Unknown";

        final EntityRegistration reg = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);

        if (reg != null) {

            final ModContainer mod = reg.getContainer();

            if (mod != null)
                return mod.getName();

            return "Unknown";
        }

        return "Minecraft";
    }

    /**
     * Gets a mod container by it's id.
     *
     * @param modID The id of the mod to lookup.
     * @return The {@link ModContainer} that was registered using the passed id.
     */
    public static ModContainer getModContainer (String modID) {

        return Loader.instance().getIndexedModList().get(modID);
    }

    /**
     * Gets the name of a mod from it's id.
     *
     * @param modId The id of the mod to lookup.
     * @return The name of the mod.
     */
    public static String getModName (String modId) {

        final ModContainer mod = getModContainer(modId);
        return mod != null ? mod.getName() : modId;
    }

    /**
     * Searches through the array of CreativeTabs and finds the first tab with the same label
     * as the one passed.
     *
     * @param label: The label of the tab you are looking for.
     * @return A CreativeTabs with the same label as the one passed. If this is not found, you
     *         will get null.
     */
    @SideOnly(Side.CLIENT)
    public static CreativeTabs getTabFromLabel (String label) {

        for (final CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY)
            if (tab.getTabLabel().equalsIgnoreCase(label))
                return tab;

        return null;
    }
}