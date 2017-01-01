package com.mcmoddev.lib.client.model;

import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class TransformGroup {

    public abstract double getWeight ();

    public abstract Vec3d doTransformation (PositionTransformVertex vertex);
}