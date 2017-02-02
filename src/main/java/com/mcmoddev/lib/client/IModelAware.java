package com.mcmoddev.lib.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public interface IModelAware {

    default List<String> getModelNames(List<String> modelNames) {
        return modelNames;
    }

    default List<ResourceLocation> getResourceLocations(List<ResourceLocation> resourceLocations) {
        getModelNames(new ArrayList<>()).forEach(modelName -> resourceLocations.add(new ResourceLocation("mmdlib", modelName)));
        return resourceLocations;
    }
    default List<ModelResourceLocation> getModelResourceLocations(List<ModelResourceLocation> modelResourceLocations) {
        List<ResourceLocation> resourceLocations = getResourceLocations(new ArrayList<>());
        for (ResourceLocation resourceLocation : resourceLocations)
            modelResourceLocations.add(new ModelResourceLocation(resourceLocation, "inventory"));
        return modelResourceLocations;
    }
}
