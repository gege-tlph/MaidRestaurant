package com.mastermarisa.maid_restaurant.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

/**
 * Prepares item models during world extraction and submits the immutable
 * render state during the main render pass.
 */
public final class WorldItemRenderer {
    private WorldItemRenderer() {
    }

    public static PreparedItem prepare(Minecraft minecraft, Camera camera, Vec3 targetPos,
                                       ItemStack itemStack, float partialTick) {
        ItemStackRenderState renderState = new ItemStackRenderState();
        minecraft.getItemModelResolver().updateForTopItem(
                renderState,
                itemStack,
                ItemDisplayContext.FIXED,
                minecraft.level,
                null,
                0
        );

        Vec3 worldPos = targetPos.add(0.0D, 0.5D, 0.0D);
        Vec3 cameraPos = camera.position();
        Vec3 relativePos = worldPos.subtract(cameraPos);
        double dx = cameraPos.x - worldPos.x;
        double dz = cameraPos.z - worldPos.z;
        float yaw = (float) Math.atan2(dz, dx);
        float time = (minecraft.level.getGameTime() + partialTick) / 20.0F;
        float bob = (float) Math.sin(time) * 0.1F;
        return new PreparedItem(relativePos, yaw, bob, renderState);
    }

    public static void submit(PoseStack poseStack, SubmitNodeCollector collector, PreparedItem item) {
        poseStack.pushPose();
        poseStack.translate(item.relativePos.x, item.relativePos.y + item.bob, item.relativePos.z);
        poseStack.mulPose(Axis.YP.rotation(-item.yaw + (float) Math.PI / 2.0F));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        item.renderState.submit(
                poseStack,
                collector,
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }

    public record PreparedItem(Vec3 relativePos, float yaw, float bob,
                               ItemStackRenderState renderState) {
    }
}
