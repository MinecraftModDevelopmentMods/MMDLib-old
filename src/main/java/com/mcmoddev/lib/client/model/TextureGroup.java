package com.mcmoddev.lib.client.model;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureGroup {

    public ArrayList<TexturedPolygon> poly;
    public String texture;

    public TextureGroup() {
        this.poly = new ArrayList<>();
        this.texture = "";
    }

    public void addPoly (TexturedPolygon quad) {
        this.poly.add(quad);
    }

    public void loadTexture () {
        this.loadTexture(-1);
    }

    public void loadTexture (int defaultTexture) {
        if (!this.texture.equals("")) {
            final TextureManager renderengine = Minecraft.getMinecraft().renderEngine;
            renderengine.bindTexture(new ResourceLocation("", this.texture));
        }
        else if (defaultTexture > -1)
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("", ""));
    }
}
