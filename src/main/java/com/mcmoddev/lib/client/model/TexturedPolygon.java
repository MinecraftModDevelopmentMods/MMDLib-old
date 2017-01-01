package com.mcmoddev.lib.client.model;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TexturedPolygon extends TexturedQuad {

    private boolean invertNormal;
    private float[] normals;
    private ArrayList<Vec3d> iNormals;

    public TexturedPolygon(PositionTextureVertex apositionTexturevertex[]) {
        super(apositionTexturevertex);
        this.invertNormal = false;
        this.normals = new float[0];
        this.iNormals = new ArrayList<>();
    }

    public void setInvertNormal (boolean isSet) {
        this.invertNormal = isSet;
    }

    public void setNormals (float x, float y, float z) {
        this.normals = new float[] { x, y, z };
    }

    public void setNormals (ArrayList<Vec3d> vec) {
        this.iNormals = vec;
    }

    public void draw (Tessellator tessellator, float f) {
        if (this.nVertices == 3)
            tessellator.startDrawing(GL11.GL_TRIANGLES);
        else if (this.nVertices == 4)
            tessellator.startDrawing(GL11.GL_QUADS);
        else
            tessellator.startDrawing(GL11.GL_POLYGON);
        if (this.iNormals.size() == 0)
            if (this.normals.length == 3) {
                if (this.invertNormal)
                    tessellator.setNormal(-this.normals[0], -this.normals[1], -this.normals[2]);
                else
                    tessellator.setNormal(this.normals[0], this.normals[1], this.normals[2]);
            }
            else if (this.vertexPositions.length >= 3) {
                final Vec3d Vec3d = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
                final net.minecraft.util.math.Vec3d Vec3d1 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
                final net.minecraft.util.math.Vec3d Vec3d2 = Vec3d1.crossProduct(Vec3d).normalize();
                if (this.invertNormal)
                    tessellator.setNormal(-(float) Vec3d2.xCoord, -(float) Vec3d2.yCoord, -(float) Vec3d2.zCoord);
                else
                    tessellator.setNormal((float) Vec3d2.xCoord, (float) Vec3d2.yCoord, (float) Vec3d2.zCoord);
            }
            else
                return;
        for (int i = 0; i < this.nVertices; i++) {
            final PositionTextureVertex positionTexturevertex = this.vertexPositions[i];
            if (positionTexturevertex instanceof PositionTransformVertex)
                ((PositionTransformVertex) positionTexturevertex).setTransformation();
            if (i < this.iNormals.size())
                if (this.invertNormal)
                    tessellator.setNormal(-(float) this.iNormals.get(i).xCoord, -(float) this.iNormals.get(i).yCoord, -(float) this.iNormals.get(i).zCoord);
                else
                    tessellator.setNormal((float) this.iNormals.get(i).xCoord, (float) this.iNormals.get(i).yCoord, (float) this.iNormals.get(i).zCoord);
            tessellator.addVertexWithUV((float) positionTexturevertex.vector3D.xCoord * f, (float) positionTexturevertex.vector3D.yCoord * f, (float) positionTexturevertex.vector3D.zCoord * f, positionTexturevertex.texturePositionX, positionTexturevertex.texturePositionY);
        }
        tessellator.draw();
    }
}
