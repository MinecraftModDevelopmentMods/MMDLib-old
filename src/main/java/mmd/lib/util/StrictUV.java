package mmd.lib.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StrictUV {
    public double minU = 0, minV = 0, maxU = 0, maxV = 0;

    public StrictUV(double minU, double minV, double maxU, double maxV) {
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
    }

    public StrictUV(double minU, double minV, double maxU, double maxV, double textureWidth, double textureHeight) {
        this(minU / textureWidth, minV / textureHeight, maxU / textureWidth, maxV / textureHeight);
    }
}