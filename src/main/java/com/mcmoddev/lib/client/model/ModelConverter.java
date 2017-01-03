package com.mcmoddev.lib.client.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @Author Ferdinand (FEX___96) Converter to use Flansmod-Type vehicle models.
 */
@SideOnly(Side.CLIENT)
public class ModelConverter extends ModelBase {

    public ModelRendererTurbo bodyModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo model[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo bodyDoorOpenModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo bodyDoorCloseModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo turretModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo barrelModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo frontWheelModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo backWheelModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo leftFrontWheelModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo rightFrontWheelModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo leftBackWheelModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo rightBackWheelModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo rightTrackModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo leftTrackModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo rightTrackWheelModels[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo leftTrackWheelModels[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo trailerModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo steeringWheelModel[] = new ModelRendererTurbo[0];

    @Override
    public void render () {
        this.render(this.bodyModel);
        this.render(this.model);
        this.render(this.bodyDoorCloseModel);
        this.render(this.turretModel);
        this.render(this.barrelModel);
        this.render(this.frontWheelModel);
        this.render(this.backWheelModel);
        this.render(this.leftFrontWheelModel);
        this.render(this.rightFrontWheelModel);
        this.render(this.leftBackWheelModel);
        this.render(this.rightBackWheelModel);
        this.render(this.rightTrackModel);
        this.render(this.leftTrackModel);
        this.render(this.rightTrackWheelModels);
        this.render(this.leftTrackWheelModels);
        this.render(this.trailerModel);
        this.render(this.steeringWheelModel);
    }

    public void flipAll () {
        this.flip(this.bodyModel);
        this.flip(this.model);
        this.flip(this.bodyDoorOpenModel);
        this.flip(this.bodyDoorCloseModel);
        this.flip(this.turretModel);
        this.flip(this.barrelModel);
        this.flip(this.frontWheelModel);
        this.flip(this.backWheelModel);
        this.flip(this.leftFrontWheelModel);
        this.flip(this.rightFrontWheelModel);
        this.flip(this.leftBackWheelModel);
        this.flip(this.rightBackWheelModel);
        this.flip(this.rightTrackModel);
        this.flip(this.leftTrackModel);
        this.flip(this.rightTrackWheelModels);
        this.flip(this.leftTrackWheelModels);
        this.flip(this.trailerModel);
        this.flip(this.steeringWheelModel);
    }

    private void flip (ModelRendererTurbo[] bodyModel2) {
        // TODO Auto-generated method stub
    }

    @Override
    public void translateAll (float x, float y, float z) {
        this.translate(this.bodyModel, x, y, z);
        this.translate(this.model, x, y, z);
        this.translate(this.bodyDoorOpenModel, x, y, z);
        this.translate(this.bodyDoorCloseModel, x, y, z);
        this.translate(this.turretModel, x, y, z);
        this.translate(this.barrelModel, x, y, z);
        this.translate(this.frontWheelModel, x, y, z);
        this.translate(this.backWheelModel, x, y, z);
        this.translate(this.leftFrontWheelModel, x, y, z);
        this.translate(this.rightFrontWheelModel, x, y, z);
        this.translate(this.leftBackWheelModel, x, y, z);
        this.translate(this.rightBackWheelModel, x, y, z);
        this.translate(this.rightTrackModel, x, y, z);
        this.translate(this.leftTrackModel, x, y, z);
        this.translate(this.rightTrackWheelModels, x, y, z);
        this.translate(this.leftTrackWheelModels, x, y, z);
        this.translate(this.trailerModel, x, y, z);
        this.translate(this.steeringWheelModel, x, y, z);
    }
}
