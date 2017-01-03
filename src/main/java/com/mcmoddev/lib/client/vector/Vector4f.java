/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.mcmoddev.lib.client.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;

import com.mcmoddev.lib.common.vector.ReadableVector4f;
import com.mcmoddev.lib.common.vector.WritableVector4f;

/**
 * Holds a 4-tuple vector.
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$ $Id$
 */
public class Vector4f extends Vector implements Serializable, ReadableVector4f, WritableVector4f {

    private static final long serialVersionUID = 1L;
    public float x, y, z, w;

    /**
     * Constructor for Vector4f.
     */
    public Vector4f() {
        super();
    }

    /**
     * Constructor
     */
    public Vector4f(ReadableVector4f src) {
        this.set(src);
    }

    /**
     * Constructor
     */
    public Vector4f(float x, float y, float z, float w) {
        this.set(x, y, z, w);
    }

    /**
     * Add a vector to another vector and place the result in a destination vector.
     *
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be created
     *
     * @return the sum of left and right in dest
     */
    public static Vector4f add (Vector4f left, Vector4f right, Vector4f dest) {
        if (dest == null)
            return new Vector4f(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
        else {
            dest.set(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
            return dest;
        }
    }

    /**
     * Subtract a vector from another vector and place the result in a destination vector.
     *
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be created
     *
     * @return left minus right in dest
     */
    public static Vector4f sub (Vector4f left, Vector4f right, Vector4f dest) {
        if (dest == null)
            return new Vector4f(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
        else {
            dest.set(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
            return dest;
        }
    }

    /**
     * The dot product of two vectors is calculated as v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
     * + v1.w * v2.w
     *
     * @param left The LHS vector
     * @param right The RHS vector
     *
     * @return left dot right
     */
    public static float dot (Vector4f left, Vector4f right) {
        return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
    }

    /**
     * Calculate the angle between two vectors, in radians
     *
     * @param a A vector
     * @param b The other vector
     *
     * @return the angle between the two vectors, in radians
     */
    public static float angle (Vector4f a, Vector4f b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return (float) Math.acos(dls);
    }

    /*
     * (non-Javadoc)
     * @see org.lwjgl.util.vector.WritableVector2f#set(float, float)
     */
    @Override
    public void set (float x, float y) {
        this.x = x;
        this.y = y;
    }

    /*
     * (non-Javadoc)
     * @see org.lwjgl.util.vector.WritableVector3f#set(float, float, float)
     */
    @Override
    public void set (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /*
     * (non-Javadoc)
     * @see org.lwjgl.util.vector.WritableVector4f#set(float, float, float, float)
     */
    @Override
    public void set (float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Load from another Vector4f
     *
     * @param src The source vector
     *
     * @return this
     */
    public Vector4f set (ReadableVector4f src) {
        this.x = src.getX();
        this.y = src.getY();
        this.z = src.getZ();
        this.w = src.getW();
        return this;
    }

    /**
     * @return the length squared of the vector
     */
    @Override
    public float lengthSquared () {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    /**
     * Translate a vector
     *
     * @param x The translation in x
     * @param y the translation in y
     *
     * @return this
     */
    public Vector4f translate (float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    /**
     * Negate a vector
     *
     * @return this
     */
    @Override
    public Vector negate () {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
        return this;
    }

    /**
     * Negate a vector and place the result in a destination vector.
     *
     * @param dest The destination vector or null if a new vector is to be created
     *
     * @return the negated vector
     */
    public Vector4f negate (Vector4f dest) {
        if (dest == null)
            dest = new Vector4f();
        dest.x = -this.x;
        dest.y = -this.y;
        dest.z = -this.z;
        dest.w = -this.w;
        return dest;
    }

    /**
     * Normalise this vector and place the result in another vector.
     *
     * @param dest The destination vector, or null if a new vector is to be created
     *
     * @return the normalised vector
     */
    public Vector4f normalise (Vector4f dest) {
        final float l = this.length();
        if (dest == null)
            dest = new Vector4f(this.x / l, this.y / l, this.z / l, this.w / l);
        else
            dest.set(this.x / l, this.y / l, this.z / l, this.w / l);
        return dest;
    }

    /*
     * (non-Javadoc)
     * @see org.lwjgl.vector.Vector#load(FloatBuffer)
     */
    @Override
    public Vector load (FloatBuffer buf) {
        this.x = buf.get();
        this.y = buf.get();
        this.z = buf.get();
        this.w = buf.get();
        return this;
    }

    /*
     * (non-Javadoc)
     * @see org.lwjgl.vector.Vector#scale(float)
     */
    @Override
    public Vector scale (float scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        this.w *= scale;
        return this;
    }

    /*
     * (non-Javadoc)
     * @see org.lwjgl.vector.Vector#store(FloatBuffer)
     */
    @Override
    public Vector store (FloatBuffer buf) {
        buf.put(this.x);
        buf.put(this.y);
        buf.put(this.z);
        buf.put(this.w);
        return this;
    }

    @Override
    public String toString () {
        return "Vector4f: " + this.x + " " + this.y + " " + this.z + " " + this.w;
    }

    /**
     * @return x
     */
    @Override
    public final float getX () {
        return this.x;
    }

    /**
     * Set X
     *
     * @param x
     */
    @Override
    public final void setX (float x) {
        this.x = x;
    }

    /**
     * @return y
     */
    @Override
    public final float getY () {
        return this.y;
    }

    /**
     * Set Y
     *
     * @param y
     */
    @Override
    public final void setY (float y) {
        this.y = y;
    }

    /*
     * (Overrides)
     * @see org.lwjgl.vector.ReadableVector3f#getZ()
     */
    @Override
    public float getZ () {
        return this.z;
    }

    /**
     * Set Z
     *
     * @param z
     */
    @Override
    public void setZ (float z) {
        this.z = z;
    }

    /*
     * (Overrides)
     * @see org.lwjgl.vector.ReadableVector3f#getZ()
     */
    @Override
    public float getW () {
        return this.w;
    }

    /**
     * Set W
     *
     * @param w
     */
    @Override
    public void setW (float w) {
        this.w = w;
    }
}