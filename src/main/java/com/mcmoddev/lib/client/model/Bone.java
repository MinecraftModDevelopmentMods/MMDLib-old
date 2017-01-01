package com.mcmoddev.lib.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The Bone class makes it possible to create skeletons, which should help you out in animating
 * your mobs a little bit more easy. However, since you won't work with a graphical interface,
 * creating bones will be different from what you are probably used to. <br /><br /> First, you
 * will need to instantiate every Bone in the constructor of your model file. The default
 * orientation, when all angles are set to zero, will be in the vector (0, 0, length), meaning
 * it will always point backwards on a regular model. You can also set what its parent node is.
 * If a Bone does not have a parent node, it is assumed it is the root node. Each Bone can only
 * have one parent, but several children. Also, all children will inherit the offset position
 * of the root node. <br /><br /> The neutral position basically defines in what direction the
 * Bone normally faces when in rest. This will not affect the rotation of any model currently
 * attached to it or the rotation of the child nodes, but will affect the position of the child
 * nodes when recalculating their positions. The length always defines how far each child Bone
 * will be placed, since child Bones are always placed at the end of their parent Bone. <br
 * /><br /> Once you're ready to render, you can call the prepareDraw method. You only need to
 * apply it to one Bone, since it will always search for the root node to execute the code
 * there. It will then automatically rotate every child Bone and places them at the right
 * position. Finally, use the setAnglesToModels method to rotate each model and place them at
 * the correct spot. Note that if you also apply custom rotation for the individual models that
 * you should apply that after you've run setAnglesToModels, since this will override the
 * settings the model originally had. The best way to solve this is to make a separate method
 * to rotate the Bones. <br /><br /> The following would be an example of a biped with a
 * skeleton. It takes ModelBiped as an example and extends it with a skeleton. First, we have
 * the part that goes in the constructor.
 *
 * <pre>
 * // First, the origin will be placed. This is where the rest is attached to.
 * skeletonOrigin = new Bone(0, 0, 0, 0);
 * // Next, the entire skeleton is built up.
 * skeletonHead = new Bone(-3.141593F / 2, 0, 0, 0, skeletonOrigin);
 * skeletonBody = new Bone(3.141593F / 2, 0, 0, 12, skeletonOrigin);
 * skeletonShoulderRight = new Bone(0, -3.141593F / 2, 0, 5, skeletonOrigin);
 * skeletonShoulderLeft = new Bone(0, 3.141593F / 2, 0, 5, skeletonOrigin);
 * skeletonArmRight = new Bone(3.141593F / 2, 0, 0, 12, skeletonShoulderRight);
 * skeletonArmLeft = new Bone(3.141593F / 2, 0, 0, 12, skeletonShoulderLeft);
 * skeletonPelvisRight = new Bone(0, -3.141593F / 2, 0, 2, skeletonBody);
 * skeletonPelvisLeft = new Bone(0, 3.141593F / 2, 0, 2, skeletonBody);
 * skeletonLegRight = new Bone(3.141593F / 2, 0, 0, 12, skeletonPelvisRight);
 * skeletonLegLeft = new Bone(3.141593F / 2, 0, 0, 12, skeletonPelvisLeft);
 * // Finally, all models will be attached to the skeletons.
 * skeletonHead.addModel(bipedHead);
 * skeletonHead.addModel(bipedHeadwear);
 * skeletonBody.addModel(bipedBody);
 * skeletonArmRight.addModel(bipedRightArm);
 * skeletonArmLeft.addModel(bipedLeftArm);
 * skeletonLegRight.addModel(bipedRightLeg);
 * skeletonLegLeft.addModel(bipedRightLeg);
 * </pre>
 *
 * <br /><br /> After that, you could replace anything in the setRotationAngles method with the
 * following code. It's not a complete code, but you'll get the basics. <br /><br />
 *
 * <pre>
 * skeletonHead.relativeAngles.angleY = f3 / 57.29578F;
 * skeletonHead.relativeAngles.angleX = f4 / 57.29578F;
 * skeletonArmRight.relativeAngles.angleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
 * skeletonArmRight.relativeAngles.angleZ = 0.0F;
 * skeletonArmLeft.relativeAngles.angleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
 * skeletonArmLeft.relativeAngles.angleZ = 0.0F;
 * skeletonLegRight.relativeAngles.angleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
 * skeletonLegRight.relativeAngles.angleY = 0.0F;
 * skeletonLegLeft.relativeAngles.angleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
 * skeletonLegLeft.relativeAngles.angleY = 0.0F;
 * </pre>
 *
 * <br /><br /> Finally, in the render method, you could use the following code. <br /><br />
 *
 * <pre>
 * setRotationAngles(f, f1, f2, f3, f4, f5);
 * skeletonOrigin.prepareDraw();
 * skeletonOrigin.setAnglesToModels();
 * </pre>
 *
 * <br /><br /> This should generate the same animation of the regular biped. Don't forget to
 * add the individual render methods for each model though, as it won't automatically render
 * them. <br /><br />
 *
 * @author GaryCXJk
 */
@SideOnly(Side.CLIENT)
public class Bone {

    public Angle3D relativeAngles;
    protected Angle3D neutralAngles;
    protected Angle3D absoluteAngles;
    protected ArrayList<Bone> childNodes;
    private Vec3d positionVector;
    private final float length;
    private Bone parentNode;
    private final ArrayList<ModelRenderer> models;
    private final Map<ModelRenderer, Angle3D> modelBaseRot;
    private float offsetX;
    private float offsetY;
    private float offsetZ;

    /**
     * Constructor to create a bone.
     *
     * @param x the x-rotation of the bone
     * @param y the y-rotation of the bone
     * @param z the z-rotation of the bone
     * @param l the length of the bone
     */
    public Bone(float x, float y, float z, float l) {
        this.neutralAngles = new Angle3D(x, y, z);
        this.relativeAngles = new Angle3D(0, 0, 0);
        this.absoluteAngles = new Angle3D(0, 0, 0);
        this.positionVector = new Vec3d(0, 0, 0);
        this.length = l;
        this.childNodes = new ArrayList<>();
        this.models = new ArrayList<>();
        this.modelBaseRot = new HashMap<>();
        this.parentNode = null;
        this.offsetX = 0;
        this.offsetY = 0;
        this.offsetZ = 0;
        this.positionVector = new Vec3d(0, 0, 0);
    }

    /**
     * Constructor to create a bone.
     *
     * @param xOrig the x-offset of the origin
     * @param yOrig the y-offset of the origin
     * @param zOrig the z-offset of the origin
     * @param xRot the x-rotation of the bone
     * @param yRot the y-rotation of the bone
     * @param zRot the z-rotation of the bone
     * @param l the length of the bone
     */
    public Bone(float xOrig, float yOrig, float zOrig, float xRot, float yRot, float zRot, float l) {
        this(xRot, yRot, zRot, l);
        this.positionVector = this.setOffset(xOrig, yOrig, zOrig);
    }

    /**
     * Constructor to create a bone. This attaches the bone to a parent bone, and will
     * calculate its current position relative to the origin.
     *
     * @param x the x-rotation of the bone
     * @param y the y-rotation of the bone
     * @param z the z-rotation of the bone
     * @param l the length of the bone
     * @param parent the parent Bone node this Bone is attached to
     */
    public Bone(float x, float y, float z, float l, Bone parent) {
        this(x, y, z, l);
        this.attachBone(parent);
    }

    /**
     * Detaches the bone from its parent.
     */
    public void detachBone () {
        this.parentNode.childNodes.remove(this);
        this.parentNode = null;
    }

    /**
     * Attaches the bone to a parent. If the parent is already set, detaches the bone from the
     * previous parent.
     *
     * @param parent the parent Bone node this Bone is attached to
     */
    public void attachBone (Bone parent) {
        if (this.parentNode != null)
            this.detachBone();
        this.parentNode = parent;
        parent.addChildBone(this);
        this.offsetX = parent.offsetX;
        this.offsetY = parent.offsetY;
        this.offsetZ = parent.offsetZ;
        this.resetOffset();
    }

    /**
     * Sets the current offset of the parent root Bone. Note that this will always set the
     * parent root Bone, not the current Bone, as its offset is determined by the offset,
     * rotation and length of its parent.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     *
     * @return a Vec3d with the new coordinates of the current bone
     */
    public Vec3d setOffset (float x, float y, float z) {
        if (this.parentNode != null) {
            final Vec3d vector = this.parentNode.setOffset(x, y, z);
            this.offsetX = (float) vector.xCoord;
            this.offsetY = (float) vector.yCoord;
            this.offsetZ = (float) vector.zCoord;
            return vector;
        }
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        this.resetOffset(true);
        return new Vec3d(x, y, z);
    }

    /**
     * Resets the offset.
     */
    public void resetOffset () {
        this.resetOffset(false);
    }

    /**
     * Resets the offset.
     *
     * @param doRecursive
     */
    public void resetOffset (boolean doRecursive) {
        if (this.parentNode != null) {
            this.positionVector = new Vec3d(0, 0, this.parentNode.length);
            this.parentNode.setVectorRotations(this.positionVector);
            /*
             * positionVector.xCoord += parentNode.positionVector.xCoord; positionVector.yCoord
             * += parentNode.positionVector.yCoord; positionVector.zCoord +=
             * parentNode.positionVector.zCoord;
             */
            this.positionVector.add(this.parentNode.positionVector);
        }
        if (doRecursive && !this.childNodes.isEmpty())
            for (int index = 0; index < this.childNodes.size(); index++)
                this.childNodes.get(index).resetOffset(doRecursive);
    }

    /**
     * Sets the current neutral rotation of the bone. This is the same rotation as in the
     * constructor.
     *
     * @param x the x-rotation of the bone
     * @param y the y-rotation of the bone
     * @param z the z-rotation of the bone
     */
    public void setNeutralRotation (float x, float y, float z) {
        this.neutralAngles.angleX = x;
        this.neutralAngles.angleY = y;
        this.neutralAngles.angleZ = z;
    }

    /**
     * Gets the root parent bone.
     *
     * @return the root parent Bone.
     */
    public Bone getRootParent () {
        if (this.parentNode == null)
            return this;
        else
            return this.parentNode.getRootParent();
    }

    /**
     * Attaches a model to the bone. Its base rotation will be set to the neutral rotation of
     * the model.
     *
     * @param model the model to attach
     */
    public void addModel (ModelRenderer model) {
        this.addModel(model, false);
    }

    /**
     * Attaches a model to the bone. If inherit is true, it sets the base rotation to the
     * neutral rotation of the Bone, otherwise it's set to the neutral rotation of the model.
     *
     * @param model the model to attach
     * @param inherit whether the model should inherit the Bone's base rotations
     */
    public void addModel (ModelRenderer model, boolean inherit) {
        this.addModel(model, 0F, 0F, 0F, inherit);
    }

    /**
     * Attaches a model to the bone. If inherit is true, it sets the base rotation to the
     * neutral rotation of the Bone, otherwise it's set to the neutral rotation of the model.
     * When isUpright is set, the model will be rotated (-PI / 2, 0, 0).
     *
     * @param model the model to attach
     * @param inherit whether the model should inherit the Bone's base rotations
     * @param isUpright whether the model is modeled in the upright position
     */
    public void addModel (ModelRenderer model, boolean inherit, boolean isUpright) {
        this.addModel(model, 0F, 0F, 0F, inherit, isUpright);
    }

    /**
     * Attaches a model to the bone with a given base rotation.
     *
     * @param model the model to attach
     * @param x the base x-rotation
     * @param y the base y-rotation
     * @param z the base z-rotation
     */
    public void addModel (ModelRenderer model, float x, float y, float z) {
        this.addModel(model, x, y, z, false);
    }

    /**
     * Attaches a model to the bone with a given base rotation. When inherit is true, it will
     * add the Bone's neutral rotation to the given angles.
     *
     * @param model the model to attach
     * @param x the base x-rotation
     * @param y the base y-rotation
     * @param z the base z-rotation
     * @param inherit whether the model should inherit the Bone's base rotations
     */
    public void addModel (ModelRenderer model, float x, float y, float z, boolean inherit) {
        this.addModel(model, x, y, z, inherit, false);
    }

    /**
     * Attaches a model to the bone with a given base rotation. When inherit is true, it will
     * add the Bone's neutral rotation to the given angles. When isUpright is set, the model
     * will be rotated (-PI / 2, 0, 0).
     *
     * @param model the model to attach
     * @param x the base x-rotation
     * @param y the base y-rotation
     * @param z the base z-rotation
     * @param inherit whether the model should inherit the Bone's base rotations
     * @param isUpright whether the model is modeled in the upright position
     */
    public void addModel (ModelRenderer model, float x, float y, float z, boolean inherit, boolean isUpright) {
        if (inherit) {
            x += this.neutralAngles.angleX + (isUpright ? (float) Math.PI / 2 : 0);
            y += this.neutralAngles.angleY;
            z += this.neutralAngles.angleZ;
        }
        this.models.add(model);
        this.modelBaseRot.put(model, new Angle3D(x, y, z));
    }

    /**
     * Removes the given model from the Bone. Always detach the model before adding it to
     * another Bone. The best thing however is to just keep the model to one bone.
     *
     * @param model the model to remove from the bone
     */
    public void removeModel (ModelRenderer model) {
        this.models.remove(model);
        this.modelBaseRot.remove(model);
    }

    /**
     * Gets the current absolute angles. The absolute angle is calculated by getting the sum of
     * all parent Bones' relative angles plus the current relative angle. This must be called
     * after using the prepareDraw method.
     *
     * @return an Angle3D object which holds the current angles of the current node.
     */
    public Angle3D getAbsoluteAngle () {
        return new Angle3D(this.absoluteAngles.angleX, this.absoluteAngles.angleY, this.absoluteAngles.angleZ);
    }

    /**
     * Gets the current position of the bone. You should call this after all rotations and
     * positions are applied, e.g. after prepareDraw has been called.
     *
     * @return a vector containing the current position relative to the origin.
     */
    public Vec3d getPosition () {
        return new Vec3d(this.positionVector.xCoord, this.positionVector.yCoord, this.positionVector.zCoord);
    }

    protected void addChildBone (Bone bone) {
        this.childNodes.add(bone);
    }

    /**
     * Prepares the bones for rendering. This will automatically take the root Bone if it
     * isn't.
     */
    public void prepareDraw () {
        if (this.parentNode != null)
            this.parentNode.prepareDraw();
        else {
            this.setAbsoluteRotations();
            this.setVectors();
        }
    }

    /**
     * Sets the current rotation of the Bone, not calculating any parent bones in.
     *
     * @param x
     * @param y
     * @param z
     */
    public void setRotations (float x, float y, float z) {
        this.relativeAngles.angleX = x;
        this.relativeAngles.angleY = y;
        this.relativeAngles.angleZ = z;
    }

    protected void setAbsoluteRotations () {
        this.absoluteAngles.angleX = this.relativeAngles.angleX;
        this.absoluteAngles.angleY = this.relativeAngles.angleY;
        this.absoluteAngles.angleZ = this.relativeAngles.angleZ;
        for (int i = 0; i < this.childNodes.size(); i++)
            this.childNodes.get(i).setAbsoluteRotations(this.absoluteAngles.angleX, this.absoluteAngles.angleY, this.absoluteAngles.angleZ);
    }

    protected void setAbsoluteRotations (float x, float y, float z) {
        this.absoluteAngles.angleX = this.relativeAngles.angleX + x;
        this.absoluteAngles.angleY = this.relativeAngles.angleY + y;
        this.absoluteAngles.angleZ = this.relativeAngles.angleZ + z;
        for (int i = 0; i < this.childNodes.size(); i++)
            this.childNodes.get(i).setAbsoluteRotations(this.absoluteAngles.angleX, this.absoluteAngles.angleY, this.absoluteAngles.angleZ);
    }

    protected void setVectorRotations (Vec3d vector) {
        final float x = this.neutralAngles.angleX + this.absoluteAngles.angleX;
        final float y = this.neutralAngles.angleY + this.absoluteAngles.angleY;
        final float z = this.neutralAngles.angleZ + this.absoluteAngles.angleZ;
        this.setVectorRotations(vector, x, y, z);
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

    protected void addVector (Vec3d destVec, Vec3d srcVec) {
        destVec.add(srcVec);
        /*
         * destVec.xCoord += srcVec.xCoord; destVec.yCoord += srcVec.yCoord; destVec.zCoord +=
         * srcVec.zCoord;
         */
    }

    protected void setVectors () {
        final Vec3d tempVec = new Vec3d(0, 0, this.length);
        this.positionVector = new Vec3d(this.offsetX, this.offsetY, this.offsetZ);
        this.addVector(tempVec, this.positionVector);
        this.setVectorRotations(tempVec);
        for (int i = 0; i < this.childNodes.size(); i++)
            this.childNodes.get(i).setVectors(tempVec);
    }

    protected void setVectors (Vec3d vector) {
        this.positionVector = vector;
        final Vec3d tempVec = new Vec3d(0, 0, this.length);
        this.setVectorRotations(tempVec);
        this.addVector(tempVec, vector);
        for (int i = 0; i < this.childNodes.size(); i++)
            this.childNodes.get(i).setVectors(tempVec);
    }

    /**
     * Sets the current angles of the Bone to the models attached to it.
     */
    public void setAnglesToModels () {
        for (int i = 0; i < this.models.size(); i++) {
            final ModelRenderer currentModel = this.models.get(i);
            final Angle3D baseAngles = this.modelBaseRot.get(currentModel);
            currentModel.rotateAngleX = baseAngles.angleX + this.absoluteAngles.angleX;
            currentModel.rotateAngleY = baseAngles.angleY + this.absoluteAngles.angleY;
            currentModel.rotateAngleZ = baseAngles.angleZ + this.absoluteAngles.angleZ;
            currentModel.rotationPointX = (float) this.positionVector.xCoord;
            currentModel.rotationPointY = (float) this.positionVector.yCoord;
            currentModel.rotationPointZ = (float) this.positionVector.zCoord;
        }
        for (int i = 0; i < this.childNodes.size(); i++)
            this.childNodes.get(i).setAnglesToModels();
    }
}