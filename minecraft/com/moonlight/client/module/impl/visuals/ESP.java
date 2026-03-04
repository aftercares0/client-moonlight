package com.moonlight.client.module.impl.visuals;

import java.awt.Color;

import com.moonlight.client.event.impl.render.Render3DEvent;
import com.moonlight.client.util.visuals.draw.EntityRenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.shader.ShaderInstance;
import com.moonlight.client.ui.hud.HudComponent;
import com.moonlight.client.ui.hud.gui.CustomizeScreen;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.TextFormatting;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "ESP", description = "show target even behind walls" , category = Category.VISUALS)
public class ESP extends Module {

    private ModeValue mode = new ModeValue("Mode", this, "Box");

    @EventLink
    public void onRender3D(Render3DEvent event) {
        if(mode.getMode().getName().equalsIgnoreCase("Box")) {
            for(EntityPlayer p : mc.theWorld.playerEntities) {
                if(p == mc.thePlayer) continue;
                EntityRenderUtil.drawEntityBox(p, Color.white, false);
            }
        }
    }

}
