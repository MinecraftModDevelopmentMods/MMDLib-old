package com.mcmoddev.lib.client.model;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The PositionTransformGroup class adds a class which allows for vertex transformations.
 *
 * @author GaryCXJk
 */
@SideOnly(Side.CLIENT)
public class TransformGroupBone extends TransformGroup {

    protected Angle3D baseAngles;
    protected Vec3d baseVector;
    protected Bone attachedBone;
    protected double weight;

    public TransformGroupBone(Bone bone, double wght) {
        this.baseVector = bone.getPosition();
        this.baseAngles = bone.getAbsoluteAngle();
        this.attachedBone = bone;
        this.weight = wght;
    }

    public Angle3D getBaseAngles () {
        return this.baseAngles.copy();
    }

    public Angle3D getTransformAngle () {
        final Angle3D returnAngle = this.attachedBone.getAbsoluteAngle().copy();
        returnAngle.angleX -= this.baseAngles.angleX;
        returnAngle.angleY -= this.baseAngles.angleY;
        returnAngle.angleZ -= this.baseAngles.angleZ;
        return returnAngle;
    }

    public Vec3d getBaseVector () {
        return new Vec3d(this.baseVector.xCoord, this.baseVector.yCoord, this.baseVector.zCoord);
    }

    public Vec3d getTransformVector () {
        return this.baseVector.subtract(this.attachedBone.getPosition());
    }

    public Vec3d getCurrentVector () {
        return this.attachedBone.getPosition();
    }

    @Override
    public double getWeight () {
        return this.weight;
    }

    public void attachBone (Bone bone) {
        this.baseVector = bone.getPosition();
        this.baseAngles = bone.getAbsoluteAngle();
        this.attachedBone = bone;
    }

    @Override
    public Vec3d doTransformation (PositionTransformVertex vertex) {
        Vec3d vector = new Vec3d(vertex.neutralVector.xCoord, vertex.neutralVector.yCoord, vertex.neutralVector.zCoord);
        vector = this.getBaseVector().subtract(vector);
        final Angle3D angle = this.getTransformAngle();
        this.setVectorRotations(vector, angle.angleX, angle.angleY, angle.angleZ);
        return vector;
    }

    protected void setVectorRotations (Vec3d vector, float xRot, float yRot, float zRot) {
        final float x = xRot;
        final float y = yRot;
        final float z = zRot;
        final float xC = MathHelper.cos(x);
        final float xS = MathHelper.sin(x);
        final float yC = MathHelper.cos(y);
        final float yS = MathHelper.sin(y);
        final float zC = MathHelper.cos(z);
        final float zS = MathHelper.sin(z);
        double xVec = vector.xCoord;
        double yVec = vector.yCoord;
        double zVec = vector.zCoord;
        // rotation around x
        final double xy = xC * yVec - xS * zVec;
        final double xz = xC * zVec + xS * yVec;
        // rotation around y
        final double yz = yC * xz - yS * xVec;
        final double yx = yC * xVec + yS * xz;
        // rotation around z
        final double zx = zC * yx - zS * xy;
        final double zy = zC * xy + zS * yx;
        xVec = zx;
        yVec = zy;
        zVec = yz;
        /*
         * vector.xCoord = xVec; vector.yCoord = yVec; vector.zCoord = zVec;
         */
        vector = new Vec3d(xVec, yVec, zVec);
    }
}