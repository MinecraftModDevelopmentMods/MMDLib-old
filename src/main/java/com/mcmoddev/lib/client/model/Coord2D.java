package com.mcmoddev.lib.client.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class represents a coordinate space and its UV coordinates. This allows for
 * easier flat shape planning.
 *
 * @author GaryCXJk
 */
@SideOnly(Side.CLIENT)
public class Coord2D {
    public double xCoord;
    public double yCoord;
    public int uCoord;
    public int vCoord;

    public Coord2D(double x, double y) {
        xCoord = x;
        yCoord = y;
        uCoord = (int) Math.floor(x);
        vCoord = (int) Math.floor(y);
    }

    public Coord2D(double x, double y, int u, int v) {
        this(x, y);
        uCoord = u;
        vCoord = v;
    }
}