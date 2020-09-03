package com.resourcefulbees.resourcefulbees.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.resourcefulbees.resourcefulbees.ResourcefulBees;
import com.resourcefulbees.resourcefulbees.api.CustomBee;
import com.resourcefulbees.resourcefulbees.entity.passive.CustomBeeEntity;
import com.resourcefulbees.resourcefulbees.lib.BeeConstants;
import com.resourcefulbees.resourcefulbees.registry.BeeRegistry;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class SecondaryColorLayer extends LayerRenderer<CustomBeeEntity, CustomBeeModel<CustomBeeEntity>> {

    public SecondaryColorLayer(IEntityRenderer<CustomBeeEntity, CustomBeeModel<CustomBeeEntity>> rendererIn) {
        super(rendererIn);
    }

    public void render(@Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn, CustomBeeEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        CustomBee bee = entitylivingbaseIn.getBeeInfo();
        if (bee.ColorData.isBeeColored() && bee.ColorData.getSecondaryColor() != null && !bee.ColorData.getSecondaryColor().isEmpty()) {
            float[] secondaryColor = BeeRegistry.getColorFloats(bee.ColorData.getSecondaryColor());
            ResourceLocation location = new ResourceLocation(ResourcefulBees.MOD_ID, BeeConstants.ENTITY_TEXTURES_DIR + bee.ColorData.getSecondaryLayerTexture() + ".png");
            renderModel(this.getEntityModel(), location, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, secondaryColor[0], secondaryColor[1], secondaryColor[2]);
        }
    }
}
