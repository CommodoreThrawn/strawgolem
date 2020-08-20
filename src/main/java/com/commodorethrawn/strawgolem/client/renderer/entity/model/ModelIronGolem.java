package com.commodorethrawn.strawgolem.client.renderer.entity.model;

import com.commodorethrawn.strawgolem.entity.EntityStrawGolem;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.passive.IronGolemEntity;

/**
 * Replacement for vanilla golem model
 *
 * @param <T>
 */
public class ModelIronGolem<T extends IronGolemEntity> extends EntityModel<T> {
    private final RendererModel ironGolemHead;
    private final RendererModel ironGolemBody;
    private final RendererModel ironGolemRightArm;
    private final RendererModel ironGolemLeftArm;
    private final RendererModel ironGolemLeftLeg;
    private final RendererModel ironGolemRightLeg;

    public ModelIronGolem(float f1, float f2) {
        this.ironGolemHead = (new RendererModel(this)).setTextureSize(128, 128);
        this.ironGolemHead.setRotationPoint(0.0F, f2, -2.0F);
        this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, f1);
        this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, f1);
        this.ironGolemBody = (new RendererModel(this)).setTextureSize(128, 128);
        this.ironGolemBody.setRotationPoint(0.0F, f2, 0.0F);
        this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, f1);
        this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, f1 + 0.5F);
        this.ironGolemRightArm = (new RendererModel(this)).setTextureSize(128, 128);
        this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, f1);
        this.ironGolemLeftArm = (new RendererModel(this)).setTextureSize(128, 128);
        this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, f1);
        this.ironGolemLeftLeg = (new RendererModel(this, 0, 22)).setTextureSize(128, 128);
        this.ironGolemLeftLeg.setRotationPoint(-4.0F, 18 + f2, 0.0F);
        this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f1);
        this.ironGolemRightLeg = (new RendererModel(this, 0, 22)).setTextureSize(128, 128);
        this.ironGolemRightLeg.mirror = true;
        this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18 + f2, 0.0F);
        this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f1);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.ironGolemHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.ironGolemHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.ironGolemLeftLeg.rotateAngleX = -1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.ironGolemRightLeg.rotateAngleX = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.ironGolemLeftLeg.rotateAngleY = 0.0F;
        this.ironGolemRightLeg.rotateAngleY = 0.0F;
    }

    /**
     * Same as original, except when holding a straw golem
     */
    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackTimer();
        if (i > 0) {
            this.ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float) i - partialTick, 10.0F);
            this.ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float) i - partialTick, 10.0F);
        } else {
            int j = entityIn.getHoldRoseTick();
            if (j > 0) {
                this.ironGolemRightArm.rotateAngleX = -0.8F + 0.025F * this.triangleWave((float) j, 70.0F);
                this.ironGolemLeftArm.rotateAngleX = 0.0F;
            } else if (entityIn.getPassengers().size() == 1 && entityIn.getPassengers().get(0) instanceof EntityStrawGolem) {
                ironGolemLeftArm.rotateAngleX = -0.45F * (float) Math.PI;
                ironGolemRightArm.rotateAngleX = -0.45F * (float) Math.PI;
                ironGolemLeftArm.rotateAngleY = 0.18F;
                ironGolemRightArm.rotateAngleY = -0.18F;
            } else {
                this.ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
                this.ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
            }
        }

    }

    private float triangleWave(float f1, float f2) {
        return (Math.abs(f1 % f2 - f2 * 0.5F) - f2 * 0.25F) / (f2 * 0.25F);
    }

    public RendererModel getArmHoldingRose() {
        return this.ironGolemRightArm;
    }
}
