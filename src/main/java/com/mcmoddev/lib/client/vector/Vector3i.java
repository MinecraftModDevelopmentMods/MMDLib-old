package com.mcmoddev.lib.client.vector;

import java.nio.FloatBuffer;

import net.minecraft.util.math.Vec3d;

public class Vector3i extends Vector {

    private static final long serialVersionUID = 1L;
    public int x, y, z;

    public Vector3i() {
        super();
    }

    public Vector3i(int x, int y, int z) {
        this.set(x, y, z);
    }

    public Vector3i(Vec3d vec) {
        this((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
    }

    public Vector3i(double x, double y, double z) {
        this((int) x, (int) y, (int) z);
    }

    public Vector3i(Vector3i v) {
        this(v.x, v.y, v.z);
    }

    public static Vector3i add (Vector3i left, Vector3i right, Vector3i dest) {
        if (dest == null)
            return new Vector3i(left.x + right.x, left.y + right.y, left.z + right.z);
        else {
            dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
            return dest;
        }
    }

    public static Vector3i sub (Vector3i left, Vector3i right, Vector3i dest) {
        if (dest == null)
            return new Vector3i(left.x - right.x, left.y - right.y, left.z - right.z);
        else {
            dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
            return dest;
        }
    }

    public static Vector3i cross (Vector3i left, Vector3i right, Vector3i dest) {
        if (dest == null)
            dest = new Vector3i();
        dest.set(left.y * right.z - left.z * right.y, right.x * left.z - right.z * left.x, left.x * right.y - left.y * right.x);
        return dest;
    }

    public static float dot (Vector3i left, Vector3i right) {
        return left.x * right.x + left.y * right.y + left.z * right.z;
    }

    public static float angle (Vector3i a, Vector3i b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return (float) Math.acos(dls);
    }

    public Vec3d toVec3 () {
        return new Vec3d(this.x, this.y, this.z);
    }

    public void set (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float lengthSquared () {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public Vector3i translate (int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector negate () {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public Vector3i negate (Vector3i dest) {
        if (dest == null)
            dest = new Vector3i();
        dest.x = -this.x;
        dest.y = -this.y;
        dest.z = -this.z;
        return dest;
    }

    public Vector3i normalise (Vector3i dest) {
        final float l = this.length();
        if (dest == null)
            dest = new Vector3i(this.x / l, this.y / l, this.z / l);
        else
            dest.set((int) (this.x / l), (int) (this.y / l), (int) (this.z / l));
        return dest;
    }

    @Override
    public Vector load (FloatBuffer buf) {
        this.x = (int) buf.get();
        this.y = (int) buf.get();
        this.z = (int) buf.get();
        return this;
    }

    @Override
    public Vector scale (float scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        return this;
    }

    @Override
    public Vector store (FloatBuffer buf) {
        buf.put(this.x);
        buf.put(this.y);
        buf.put(this.z);
        return this;
    }

    @Override
    public String toString () {
        return "Vector3i[" + this.x + ", " + this.y + ", " + this.z + ']';
    }

    public final int getX () {
        return this.x;
    }

    public final void setX (int x) {
        this.x = x;
    }

    public final int getY () {
        return this.y;
    }

    public final void setY (int y) {
        this.y = y;
    }

    public int getZ () {
        return this.z;
    }

    public void setZ (int z) {
        this.z = z;
    }
}
