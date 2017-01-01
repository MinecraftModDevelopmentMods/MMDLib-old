package com.mcmoddev.lib.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * An implementation of GuiButton which uses an image rather than text. By default the image
 * and button should be 20x20, however another constructor allows for this to be changed. Keep
 * in mind that textures should be below 256x in size and that scaling of images is not
 * supported.
 *
 * @author Tyler Hancock (Darkhax)
 */
@SideOnly(Side.CLIENT)
public class GuiButtonGraphical extends GuiButton {

    /**
     * The image to be drawn on the button.
     */
    private final ResourceLocation texture;

    /**
     * The width of the texture. Default is 20.
     */
    private final int textureWidth;

    /**
     * The height of the texture. Default is 20.
     */
    private final int textureHeight;

    /**
     * Constructor for a basic graphical button.
     *
     * @param id The id to assign the button. This id is specific to the GUI instance.
     * @param x The X position for the button.
     * @param y The Y position for the button.
     * @param texture The image to be drawn on the button. This should be 20x20.
     */
    public GuiButtonGraphical(int id, int x, int y, ResourceLocation texture) {

        this(id, x, y, texture, 20, 20);
    }

    /**
     * Constructor for a graphical button.
     *
     * @param id The id to assign the button. This id is specific to the GUI instance.
     * @param x The X position for the button.
     * @param y The Y position for the button.
     * @param texture The image to be drawn on the button.
     * @param textureWidth The width of the texture in pixels.
     * @param textureHeight The height of the texture in pixels.
     */
    public GuiButtonGraphical(int id, int x, int y, ResourceLocation texture, int textureWidth, int textureHeight) {

        super(id, x, y, textureWidth, textureHeight, "");
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void drawButton (Minecraft mc, int posX, int posY) {

        super.drawButton(mc, posX, posY);
        mc.getTextureManager().bindTexture(this.getTexture());

        final int textureWidth = this.getTextureWidth();
        final int textureHeight = this.getTextureHeight();
        drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0f, 0f, textureWidth, textureHeight, textureWidth, textureHeight);
    }

    /**
     * Gets the image to be drawn on the button.
     *
     * @return The image to be drawn on the button.
     */
    public ResourceLocation getTexture () {

        return this.texture;
    }

    /**
     * Gets the width of the texture being drawn.
     *
     * @return The width of the texture.
     */
    public int getTextureWidth () {

        return this.textureWidth;
    }

    /**
     * Gets the height of the texture being drawn.
     *
     * @return The height of the texture.
     */
    public int getTextureHeight () {

        return this.textureHeight;
    }
}