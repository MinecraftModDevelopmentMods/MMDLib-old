package com.mcmoddev.lib.client.matrix;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Matrix3f {

    float[][] matrix = new float[3][3];

    public Matrix3f(float[][] Matrix) {
        for (int i = 0; i < 3; i++)
            System.arraycopy(Matrix[i], 0, this.matrix[i], 0, 3);
    }

    public Matrix3f(float m11, float m12, float m13, float m21, float m22, float m23, float m31, float m32, float m33) {
        this.matrix[0][0] = m11;
        this.matrix[0][1] = m12;
        this.matrix[0][2] = m13;
        this.matrix[1][0] = m21;
        this.matrix[1][1] = m22;
        this.matrix[1][2] = m23;
        this.matrix[2][0] = m31;
        this.matrix[2][1] = m32;
        this.matrix[2][2] = m33;
    }

    public static Matrix3f getMatrixRotX (float r) {
        final float sn = MathHelper.sin(r);
        final float cs = MathHelper.cos(r);
        return new Matrix3f(new float[][] { { 1, 0, 0 }, { 0, cs, -sn }, { 0, sn, cs } });
    }

    public static Matrix3f getMatrixRotY (float r) {
        final float sn = MathHelper.sin(r);
        final float cs = MathHelper.cos(r);
        return new Matrix3f(new float[][] { { cs, 0, sn }, { 0, 1, 0 }, { -sn, 0, cs } });
    }

    public static Matrix3f getMatrixRotZ (float r) {
        final float sn = MathHelper.sin(r);
        final float cs = MathHelper.cos(r);
        return new Matrix3f(new float[][] { { cs, -sn, 0 }, { sn, cs, 0 }, { 0, 0, 1 } });
    }

    public static Vec3d multVec (Matrix3f m, Vec3d vec) {
        // float[][] retMat = new float[3][3];
        final float[] retVec = new float[3];
        for (int i = 0; i < 3; i++) {
            final float[] row = { m.matrix[i][0], m.matrix[i][1], m.matrix[i][2] };
            final float[] column = { (float) vec.xCoord, (float) vec.yCoord, (float) vec.zCoord };
            for (int sm = 0; sm < 3; sm++)
                retVec[i] += row[sm] * column[sm];
        }
        return new Vec3d(retVec[0], retVec[1], retVec[2]);
    }

    public static Matrix3f multMatrix (Matrix3f m1, Matrix3f m2) {
        final Matrix3f retMat = new Matrix3f(new float[3][3]);
        for (int i = 0; i < 3; i++) {
            final float[] row = { m1.matrix[i][0], m1.matrix[i][1], m1.matrix[i][2] };
            for (int j = 0; j < 3; j++) {
                final float[] column = { m2.matrix[0][j], m2.matrix[1][j], m2.matrix[2][j] };
                for (int sm = 0; sm < 3; sm++)
                    retMat.matrix[i][j] += row[sm] * column[sm];
            }
        }
        return retMat;
    }

    public Matrix3f mult (Matrix3f m) {
        return Matrix3f.multMatrix(this, m);
    }

    public Vec3d mult (Vec3d v) {
        return Matrix3f.multVec(this, v);
    }
}
