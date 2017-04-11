package com.mcmoddev.lib.client;

import com.mcmoddev.lib.client.matrix.Matrix4f;
import com.mcmoddev.lib.client.vector.Vector3f;

public class RotatedAxes {

    private float rotationYaw;
    private float rotationPitch;
    private float rotationRoll;
    private Matrix4f rotationMatrix;

    public RotatedAxes() {
        // Load identity
        this.rotationMatrix = new Matrix4f();
    }

    public RotatedAxes(Matrix4f mat) {
        this.rotationMatrix = mat;
        this.convertMatrixToAngles();
    }

    public RotatedAxes(float yaw, float pitch, float roll) {
        this.setAngles(yaw, pitch, roll);
    }

    @Override
    public RotatedAxes clone() {
        final RotatedAxes newAxis = new RotatedAxes();
        newAxis.rotationMatrix.load(this.getMatrix());
        newAxis.convertMatrixToAngles();
        return newAxis;
    }

    public void setAngles(float yaw, float pitch, float roll) {
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.rotationRoll = roll;
        this.convertAnglesToMatrix();
    }

    public float getYaw() {
        return this.rotationYaw;
    }

    public float getPitch() {
        return this.rotationPitch;
    }

    public float getRoll() {
        return this.rotationRoll;
    }

    public Vector3f getXAxis() {
        return new Vector3f(this.rotationMatrix.m00, this.rotationMatrix.m10, this.rotationMatrix.m20);
    }

    public Vector3f getYAxis() {
        return new Vector3f(this.rotationMatrix.m01, this.rotationMatrix.m11, this.rotationMatrix.m21);
    }

    public Vector3f getZAxis() {
        return new Vector3f(-this.rotationMatrix.m02, -this.rotationMatrix.m12, -this.rotationMatrix.m22);
    }

    public Matrix4f getMatrix() {
        return this.rotationMatrix;
    }

    // Rotate locally by some angle about the yaw axis
    public void rotateLocalYaw(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * 3.14159265F / 180F, this.getYAxis().normalise(null));
        this.convertMatrixToAngles();
    }

    // Rotate locally by some angle about the pitch axis
    public void rotateLocalPitch(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * 3.14159265F / 180F, this.getZAxis().normalise(null));
        this.convertMatrixToAngles();
    }

    // Rotate locally by some angle about the roll axis
    public void rotateLocalRoll(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * 3.14159265F / 180F, this.getXAxis().normalise(null));
        this.convertMatrixToAngles();
    }

    // Rotate globally by some angle about the yaw axis
    public RotatedAxes rotateGlobalYaw(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * 3.14159265F / 180F, new Vector3f(0F, 1F, 0F));
        this.convertMatrixToAngles();
        return this;
    }

    // Rotate globally by some angle about the pitch axis
    public RotatedAxes rotateGlobalPitch(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * 3.14159265F / 180F, new Vector3f(0F, 0F, 1F));
        this.convertMatrixToAngles();
        return this;
    }

    // Rotate globally by some angle about the roll axis
    public RotatedAxes rotateGlobalRoll(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy * 3.14159265F / 180F, new Vector3f(1F, 0F, 0F));
        this.convertMatrixToAngles();
        return this;
    }

    // Rotate globally by some angle about the yaw axis
    public RotatedAxes rotateGlobalYawInRads(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy, new Vector3f(0F, 1F, 0F));
        this.convertMatrixToAngles();
        return this;
    }

    // Rotate globally by some angle about the pitch axis
    public RotatedAxes rotateGlobalPitchInRads(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy, new Vector3f(0F, 0F, 1F));
        this.convertMatrixToAngles();
        return this;
    }

    // Rotate globally by some angle about the roll axis
    public RotatedAxes rotateGlobalRollInRads(float rotateBy) {
        this.rotationMatrix.rotate(rotateBy, new Vector3f(1F, 0F, 0F));
        this.convertMatrixToAngles();
        return this;
    }

    // Rotate by some angle around some axis
    public void rotateLocal(float rotateBy, Vector3f rotateAround) {
        this.rotationMatrix.rotate(rotateBy * 3.14159265F / 180F, this.findLocalVectorGlobally(rotateAround));
        this.convertMatrixToAngles();
    }

    // Rotate by some angle around some axis
    public void rotateGlobal(float rotateBy, Vector3f rotateAround) {
        this.rotationMatrix.rotate(rotateBy * 3.14159265F / 180F, rotateAround);
        this.convertMatrixToAngles();
    }

    // Find a global vector in terms of this basis.
    public Vector3f findGlobalVectorLocally(Vector3f in) {
        // Create a new matrix and use the first column to store the vector we are rotating
        final Matrix4f mat = new Matrix4f();
        mat.m00 = in.x;
        mat.m10 = in.y;
        mat.m20 = in.z;
        // Do the rotations used to obtain this basis in reverse
        mat.rotate(-this.rotationYaw * 3.14159265F / 180F, new Vector3f(0F, 1F, 0F));
        mat.rotate(-this.rotationPitch * 3.14159265F / 180F, new Vector3f(0F, 0F, 1F));
        mat.rotate(-this.rotationRoll * 3.14159265F / 180F, new Vector3f(1F, 0F, 0F));
        return new Vector3f(mat.m00, mat.m10, mat.m20);
    }

    // Find a local vector in terms of the global axes.
    public Vector3f findLocalVectorGlobally(Vector3f in) {
        // Create a new matrix and use the first column to store the vector we are rotating
        final Matrix4f mat = new Matrix4f();
        mat.m00 = in.x;
        mat.m10 = in.y;
        mat.m20 = in.z;
        // Do the rotations used to obtain this basis
        mat.rotate(this.rotationRoll * 3.14159265F / 180F, new Vector3f(1F, 0F, 0F));
        mat.rotate(this.rotationPitch * 3.14159265F / 180F, new Vector3f(0F, 0F, 1F));
        mat.rotate(this.rotationYaw * 3.14159265F / 180F, new Vector3f(0F, 1F, 0F));
        return new Vector3f(mat.m00, mat.m10, mat.m20);
    }

    private void convertAnglesToMatrix() {
        // Re-load the identity
        this.rotationMatrix = new Matrix4f();
        this.rotationMatrix.rotate(this.rotationRoll * 3.14159265F / 180F, new Vector3f(1F, 0F, 0F));
        this.rotationMatrix.rotate(this.rotationPitch * 3.14159265F / 180F, new Vector3f(0F, 0F, 1F));
        this.rotationMatrix.rotate(this.rotationYaw * 3.14159265F / 180F, new Vector3f(0F, 1F, 0F));
        this.convertMatrixToAngles();
    }

    private void convertMatrixToAngles() {
        this.rotationYaw = (float) Math.atan2(this.rotationMatrix.m20, this.rotationMatrix.m00) * 180F / 3.14159265F;
        this.rotationPitch = (float) Math.atan2(-this.rotationMatrix.m10, Math.sqrt(this.rotationMatrix.m12 * this.rotationMatrix.m12 + this.rotationMatrix.m11 * this.rotationMatrix.m11)) * 180F / 3.14159265F;
        this.rotationRoll = (float) Math.atan2(this.rotationMatrix.m12, this.rotationMatrix.m11) * 180F / 3.14159265F;
    }

    public RotatedAxes findLocalAxesGlobally(RotatedAxes in) {
        // Take the input matrix
        final Matrix4f mat = new Matrix4f();
        mat.load(in.getMatrix());
        // Perform the rotations to convert from this local set of axes to the global axes
        mat.rotate(this.rotationRoll * 3.14159265F / 180F, new Vector3f(1F, 0F, 0F));
        mat.rotate(this.rotationPitch * 3.14159265F / 180F, new Vector3f(0F, 0F, 1F));
        mat.rotate(this.rotationYaw * 3.14159265F / 180F, new Vector3f(0F, 1F, 0F));
        // Return the globalised matrix
        return new RotatedAxes(mat);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RotatedAxes{");
        sb.append("rotationYaw=").append(rotationYaw);
        sb.append(", rotationPitch=").append(rotationPitch);
        sb.append(", rotationRoll=").append(rotationRoll);
        sb.append(", rotationMatrix=").append(rotationMatrix);
        sb.append('}');
        return sb.toString();
    }
}