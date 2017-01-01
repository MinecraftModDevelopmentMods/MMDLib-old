package com.mcmoddev.lib.common;

import java.awt.Color;

import net.minecraft.item.EnumDyeColor;

/**
 * Contains extensive data on the vanilla colors along with utils for working with them.
 *
 * @author Tyler Hancock (Darkhax)
 */
public enum VanillaColor {
    BLACK("Black", new Color(25, 25, 25), EnumDyeColor.BLACK),
    RED("Red", new Color(153, 51, 51), EnumDyeColor.RED),
    GREEN("Green", new Color(102, 127, 51), EnumDyeColor.GREEN),
    BROWN("Brown", new Color(102, 76, 51), EnumDyeColor.BROWN),
    BLUE("Blue", new Color(51, 76, 178), EnumDyeColor.BLUE),
    PURPLE("Purple", new Color(127, 63, 178), EnumDyeColor.PURPLE),
    CYAN("Cyan", new Color(76, 127, 153), EnumDyeColor.CYAN),
    LIGHT_GRAY("LightGray", new Color(153, 153, 153), EnumDyeColor.SILVER),
    GRAY("Gray", new Color(76, 76, 76), EnumDyeColor.GRAY),
    PINK("Pink", new Color(242, 127, 165), EnumDyeColor.PINK),
    LIME("Lime", new Color(127, 204, 25), EnumDyeColor.LIME),
    YELLOW("Yellow", new Color(229, 229, 51), EnumDyeColor.YELLOW),
    LIGHT_BLUE("LightBlue", new Color(102, 153, 216), EnumDyeColor.LIGHT_BLUE),
    MAGENTAG("Magenta", new Color(178, 76, 216), EnumDyeColor.MAGENTA),
    ORANGE("Orange", new Color(216, 127, 5), EnumDyeColor.ORANGE),
    WHITE("White", new Color(255, 255, 255), EnumDyeColor.WHITE);

    /**
     * The english name for the color.
     */
    private String name;
    /**
     * An instance of the color as an AWT color.
     */
    private Color awtColor;
    /**
     * The EnumDyeColor counterpart.
     */
    private EnumDyeColor color;
    /**
     * The RGB as a packed integer.
     */
    private int packedColor;

    /**
     * A simple enumeration of all the vanilla Minecraft colors. This contains a string
     * representation of the color, which is mostly used for Ore Dictionary stuff, and a Color
     * which holds all the data for the color.
     *
     * @param name A name which represents the color within Minecraft. Example Gray
     * @param awtColor A Color which holds the RGB value for this color.
     * @param color The EnumDyeColor counterpart.
     */
    VanillaColor(String name, Color awtColor, EnumDyeColor color) {
        this.name = name;
        this.awtColor = awtColor;
        this.color = color;
        this.packedColor = awtColor.getRGB();
    }

    /**
     * Gets the name for the color.
     *
     * @return The name for the color.
     */
    public String getName () {
        return this.name;
    }

    /**
     * Provides the OreDictionary name for the dye item associated with this color.
     *
     * @return String A string which represents the associated dye within the OreDictionary.
     */
    public String getDyeName () {
        return "dye" + this.name;
    }

    /**
     * Provides the OreDictionary name for the Dyed Glass Pane associated with this color.
     *
     * @return String A string which represents the associated Dyed Glass Pane within the
     *         OreDictionary.
     */
    public String getGlassPaneName () {
        return "paneGlass" + this.name;
    }

    /**
     * Provides the OreDictionary name for the Dyed Glass Block associated with this color.
     *
     * @return String A String which represents the associated Dyed Glass Block within the
     *         OreDictionary.
     */
    public String getGlassBlockName () {
        return "blockGlass" + this.name;
    }

    /**
     * Gets the AWT version of the color.
     *
     * @return An AWT Color object.
     */
    public Color getAWTColor () {
        return this.awtColor;
    }

    /**
     * Gets the EnumDyeColor counterpart to the color.
     *
     * @return The EnumDyeColor counterpart.
     */
    public EnumDyeColor getColor () {
        return this.color;
    }

    /**
     * Gets the RGB value as a packed integer.
     *
     * @return The packed RGB value.
     */
    public int getPackedColor () {
        return this.packedColor;
    }

    /**
     * Attempts to retrieve a color, based on a provided name. The provided name does not need
     * to match the casings of the actual color name.
     *
     * @param name The name of the color you are looking for. Example Green
     * @return VanillaColor A VenillaColor which reflects the provided name. If no name is
     *         found, it will be null.
     */
    public static VanillaColor getColorByName (String name) {
        for (final VanillaColor color : VanillaColor.values())
            if (color.name.equalsIgnoreCase(name))
                return color;
        return null;
    }
}
