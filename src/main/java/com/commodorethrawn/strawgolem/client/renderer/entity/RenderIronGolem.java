package com.commodorethrawn.strawgolem.client.renderer.entity;

import com.commodorethrawn.strawgolem.client.renderer.entity.layers.IronGolemFlowerLayer;
import com.commodorethrawn.strawgolem.client.renderer.entity.model.ModelIronGolem;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ResourceLocation;

public class RenderIronGolem extends MobRenderer<IronGolemEntity, ModelIronGolem<IronGolemEntity>> {

    private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("textures/entity/iron_golem/iron_golem.png");

    public RenderIronGolem(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelIronGolem<>(0, -7), 0.5f);
        this.addLayer(new IronGolemFlowerLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(IronGolemEntity entity) {
        return IRON_GOLEM_TEXTURES;
    }

}
