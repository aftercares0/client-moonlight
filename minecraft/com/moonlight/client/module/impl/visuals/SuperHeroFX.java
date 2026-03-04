package com.moonlight.client.module.impl.visuals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.render.Render3DEvent;
import com.moonlight.client.event.impl.world.WorldChangeEvent;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.util.timer.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;

import net.minecraft.entity.Entity;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "SuperHeroFX", description = "hero fx effect" , category = Category.VISUALS)
public class SuperHeroFX extends Module {

    private CopyOnWriteArrayList<FXParticle> particles = new CopyOnWriteArrayList();
    private TimerHelper timer = new TimerHelper();

    @EventLink
    public void onWorldChange(WorldChangeEvent event) {
        particles.clear();
    }

    @Override
    public void onDisable() {
        if(particles == null) return;
        particles.clear();
    }

    @EventLink
    public void onPreAttack(PreAttackEvent event) {
        Entity entity = event.getEntity();
        if (mc.theWorld.loadedEntityList.contains(entity) && timer.hasTimeElapsed(true, 500L)) {
            Random random = new Random();
            double dirX = random.nextDouble() - 0.5;
            double dirZ = random.nextDouble() - 0.5;
            particles.add(
                    new FXParticle(
                            entity.posX + dirX,
                            entity.getEntityBoundingBox().minY + (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) / 2.0,
                            entity.posZ + dirZ,
                            dirX, dirZ
                    )
            );
            timer.reset();
        }
    }

    @EventLink
    public void onRender3D(Render3DEvent event) {
        CopyOnWriteArrayList<FXParticle> removeList = new CopyOnWriteArrayList();
        for (FXParticle particle : particles) {
            if (particle.canRemove) {
                removeList.add(particle);
                continue;
            }
            particle.draw();
        }
        particles.removeAll(removeList);
    }

    public class FXParticle {
        private final double posX;
        private final double posY;
        private final double posZ;
        private final double animHDir;
        private final double animVDir;

        private final String messageString;
        private final Color color;

        private final TimerHelper fadeTimer;
        private final double stringLength;
        private final double fontHeight;

        private boolean canRemove;
        private boolean firstDraw;

        public FXParticle(double posX, double posY, double posZ, double animHDir, double animVDir) {
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.animHDir = animHDir;
            this.animVDir = animVDir;

            messageString = Arrays.asList("kaboom", "bam", "zap", "smash", "fatality", "kapow", "wham", "trash", "critical", "fire!").get((int) (Math.random() * 7));
            color = Arrays.asList(Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW).get((int) (Math.random() * 8));

            fadeTimer = new TimerHelper();
            stringLength = Fonts.bangers30.getStringWidth(messageString);
            fontHeight = Fonts.bangers30.getHeight();

            canRemove = false;
            firstDraw = true;
        }

        public void draw() {
            RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
            if (firstDraw) {
                fadeTimer.reset();
                firstDraw = false;
            }

            float alpha = (fadeTimer.hasTimeElapsed(false, 250L) ? fadeTimer.hasTimeLeft(500L) : 250L - fadeTimer.hasTimeLeft(250L)) / 250F;
            float progress = (fadeTimer.hasTimeElapsed(false, 250L) ? Math.abs(fadeTimer.hasTimeLeft(250L) - 250L) : 250L - fadeTimer.hasTimeLeft(250L)) / 250F;
            float textY = mc.gameSettings.showDebugInfo != 2 ? -1.0f : 1.0f;
            float offsetX = (float) ((stringLength / 2.0f) * 0.02f * progress);
            float offsetY = (float) ((fontHeight / 2.0f) * 0.02f * progress);


            if (progress >= 2F) {
                canRemove = true;
                return;
            }

            GlStateManager.pushMatrix();
            GlStateManager.enablePolygonOffset();
            GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
            GL11.glTranslated(posX + animHDir * progress - offsetX - renderManager.renderPosX, posY + animVDir * progress - offsetY - renderManager.renderPosY, posZ - renderManager.renderPosZ);
            GlStateManager.rotate(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(progress * -0.02F, progress * -0.02F, progress * 0.02F);
            GlStateManager.rotate(textY * renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glDepthMask(false);
            Fonts.bangers30.drawStringFor3D(messageString, 0F, 0F, new Color(color.getRed(), color.getGreen(), color.getBlue(), coerceIn((int) alpha, 0, 255)).getRGB(), false, 8.3f);
            GL11.glColor4f(187.0f, 255.0f, 255.0f, 1.0f);
            GL11.glDepthMask(true);
            GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
            GlStateManager.disablePolygonOffset();
            GlStateManager.popMatrix();
        }

        public Integer coerceIn(Integer value, Integer min, Integer max) {
            if (value.compareTo(min) < 0) {
                return min;
            } else if (value.compareTo(max) > 0) {
                return max;
            } else {
                return value;
            }
        }
    }

}
