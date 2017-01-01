package com.mcmoddev.lib.client.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @Author Ferdinand (FEX___96) Similar to 'FlansMod'-type Models, for a fast convert.
 */
@SideOnly(Side.CLIENT)
public class ModelBase extends net.minecraft.client.model.ModelBase {

    public ModelRendererTurbo base[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo open[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo closed[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r1[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r2[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r3[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r4[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r5[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r6[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r7[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r8[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r9[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo r0[] = new ModelRendererTurbo[0];

    public void render () {
        this.render(this.base);
        this.render(this.open);
        this.render(this.closed);
        this.render(this.r0);
        this.render(this.r1);
        this.render(this.r2);
        this.render(this.r3);
        this.render(this.r4);
        this.render(this.r5);
        this.render(this.r6);
        this.render(this.r7);
        this.render(this.r8);
        this.render(this.r9);
    }

    public void render (ModelRendererTurbo[] part) {
        for (final ModelRendererTurbo mrt : part)
            mrt.render();
    }

    protected void translate (ModelRendererTurbo[] model, float x, float y, float z) {
        for (final ModelRendererTurbo mod : model) {
            mod.rotationPointX += x;
            mod.rotationPointY += y;
            mod.rotationPointZ += z;
        }
    }

    public void translateAll (float x, float y, float z) {
        this.translate(this.base, x, y, z);
        this.translate(this.open, x, y, z);
        this.translate(this.closed, x, y, z);
        this.translate(this.r0, x, y, z);
        this.translate(this.r1, x, y, z);
        this.translate(this.r2, x, y, z);
        this.translate(this.r3, x, y, z);
        this.translate(this.r4, x, y, z);
        this.translate(this.r5, x, y, z);
        this.translate(this.r6, x, y, z);
        this.translate(this.r7, x, y, z);
        this.translate(this.r8, x, y, z);
        this.translate(this.r9, x, y, z);
    }
}
