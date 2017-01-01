package com.mcmoddev.lib.client.model;

import java.util.ArrayList;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PositionTransformVertex extends PositionTextureVertex {

    public Vec3d neutralVector;
    public ArrayList<TransformGroup> transformGroups = new ArrayList<>();

    public PositionTransformVertex(float x, float y, float z, float u, float v) {
        this(new Vec3d(x, y, z), u, v);
    }

    public PositionTransformVertex(PositionTextureVertex vertex, float u, float v) {
        super(vertex, u, v);
        if (vertex instanceof PositionTransformVertex)
            this.neutralVector = ((PositionTransformVertex) vertex).neutralVector;
        else
            this.neutralVector = new Vec3d(vertex.vector3D.xCoord, vertex.vector3D.yCoord, vertex.vector3D.zCoord);
    }

    public PositionTransformVertex(PositionTextureVertex vertex) {
        this(vertex, vertex.texturePositionX, vertex.texturePositionY);
    }

    public PositionTransformVertex(Vec3d vector, float u, float v) {
        super(vector, u, v);
        this.neutralVector = new Vec3d(vector.xCoord, vector.yCoord, vector.zCoord);
    }

    public void setTransformation () {
        if (this.transformGroups.size() == 0) {
            /*
             * vector3D.xCoord = neutralVector.xCoord; vector3D.yCoord = neutralVector.yCoord;
             * vector3D.zCoord = neutralVector.zCoord;
             */
            this.vector3D = this.neutralVector;
            return;
        }
        double weight = 0D;
        for (int i = 0; i < this.transformGroups.size(); i++)
            weight += this.transformGroups.get(i).getWeight();
        /*
         * vector3D.xCoord = 0; vector3D.yCoord = 0; vector3D.zCoord = 0;
         */
        this.vector3D = new Vec3d(0, 0, 0);
        for (int i = 0; i < this.transformGroups.size(); i++) {
            final TransformGroup group = this.transformGroups.get(i);
            final double cWeight = group.getWeight() / weight;
            final Vec3d vector = group.doTransformation(this);
            /*
             * vector3D.xCoord += cWeight * vector.xCoord; vector3D.yCoord += cWeight *
             * vector.yCoord; vector3D.zCoord += cWeight * vector.zCoord;
             */
            this.vector3D.addVector(cWeight * vector.xCoord, cWeight * vector.yCoord, cWeight * vector.zCoord);
        }
    }

    public void addGroup (TransformGroup group) {
        this.transformGroups.add(group);
    }

    public void removeGroup (TransformGroup group) {
        this.transformGroups.remove(group);
    }
}
