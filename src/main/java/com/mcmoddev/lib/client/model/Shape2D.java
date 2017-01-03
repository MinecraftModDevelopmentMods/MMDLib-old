package com.mcmoddev.lib.client.model;

import java.util.ArrayList;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Shape2D {

    public ArrayList<Coord2D> coords;

    public Shape2D() {
        this.coords = new ArrayList<>();
    }

    public Shape2D(Coord2D[] coordArray) {
        this.coords = new ArrayList<>();
        for (final Coord2D element : coordArray)
            this.coords.add(element);
    }

    public Shape2D(ArrayList<Coord2D> coordList) {
        this.coords = coordList;
    }

    public Coord2D[] getCoordArray () {
        return (Coord2D[]) this.coords.toArray();
    }

    public Shape3D extrude (float x, float y, float z, float rotX, float rotY, float rotZ, float depth, int u, int v, float textureWidth, float textureHeight, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, float[] faceLengths) {
        final PositionTransformVertex[] verts = new PositionTransformVertex[this.coords.size() * 2];
        final PositionTransformVertex[] vertsTop = new PositionTransformVertex[this.coords.size()];
        final PositionTransformVertex[] vertsBottom = new PositionTransformVertex[this.coords.size()];
        final TexturedPolygon[] poly = new TexturedPolygon[this.coords.size() + 2];
        final Vec3d extrudeVector = new Vec3d(0, 0, depth);
        this.setVectorRotations(extrudeVector, rotX, rotY, rotZ);
        if (faceLengths != null && faceLengths.length < this.coords.size())
            faceLengths = null;
        float totalLength = 0;
        for (int idx = 0; idx < this.coords.size(); idx++) {
            final Coord2D curCoord = this.coords.get(idx);
            final Coord2D nextCoord = this.coords.get((idx + 1) % this.coords.size());
            final float texU1 = (curCoord.uCoord + u) / textureWidth;
            final float texU2 = (shapeTextureWidth * 2 - curCoord.uCoord + u) / textureWidth;
            final float texV = (curCoord.vCoord + v) / textureHeight;
            final Vec3d vecCoord = new Vec3d(curCoord.xCoord, curCoord.yCoord, 0);
            this.setVectorRotations(vecCoord, rotX, rotY, rotZ);
            verts[idx] = new PositionTransformVertex(x + (float) vecCoord.xCoord, y + (float) vecCoord.yCoord, z + (float) vecCoord.zCoord, texU1, texV);
            verts[idx + this.coords.size()] = new PositionTransformVertex(x + (float) vecCoord.xCoord - (float) extrudeVector.xCoord, y + (float) vecCoord.yCoord - (float) extrudeVector.yCoord, z + (float) vecCoord.zCoord - (float) extrudeVector.zCoord, texU2, texV);
            vertsTop[idx] = new PositionTransformVertex(verts[idx]);
            vertsBottom[this.coords.size() - idx - 1] = new PositionTransformVertex(verts[idx + this.coords.size()]);
            if (faceLengths != null)
                totalLength += faceLengths[idx];
            else
                totalLength += Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2));
        }
        poly[this.coords.size()] = new TexturedPolygon(vertsTop);
        poly[this.coords.size() + 1] = new TexturedPolygon(vertsBottom);
        float currentLengthPosition = totalLength;
        for (int idx = 0; idx < this.coords.size(); idx++) {
            final Coord2D curCoord = this.coords.get(idx);
            final Coord2D nextCoord = this.coords.get((idx + 1) % this.coords.size());
            float currentLength = (float) Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2));
            if (faceLengths != null)
                currentLength = faceLengths[faceLengths.length - idx - 1];
            final float ratioPosition = currentLengthPosition / totalLength;
            final float ratioLength = (currentLengthPosition - currentLength) / totalLength;
            final float texU1 = (ratioLength * sideTextureWidth + u) / textureWidth;
            final float texU2 = (ratioPosition * sideTextureWidth + u) / textureWidth;
            final float texV1 = ((float) v + (float) shapeTextureHeight) / textureHeight;
            final float texV2 = ((float) v + (float) shapeTextureHeight + sideTextureHeight) / textureHeight;
            final PositionTransformVertex[] polySide = new PositionTransformVertex[4];
            polySide[0] = new PositionTransformVertex(verts[idx], texU2, texV1);
            polySide[1] = new PositionTransformVertex(verts[this.coords.size() + idx], texU2, texV2);
            polySide[2] = new PositionTransformVertex(verts[this.coords.size() + (idx + 1) % this.coords.size()], texU1, texV2);
            polySide[3] = new PositionTransformVertex(verts[(idx + 1) % this.coords.size()], texU1, texV1);
            poly[idx] = new TexturedPolygon(polySide);
            currentLengthPosition -= currentLength;
        }
        return new Shape3D(verts, poly);
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
