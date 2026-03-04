package com.moonlight.client.module.impl.visuals;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.visuals.targethud.*;
import com.moonlight.client.shader.ShaderInstance;
import com.moonlight.client.ui.hud.HudComponent;
import com.moonlight.client.ui.hud.gui.CustomizeScreen;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.TextFormatting;

import static java.lang.Double.NaN;

@ModuleInfo(name = "TargetHUD", description = "display info about current target" , category = Category.VISUALS)
public class TargetHUD extends Module {
		
	private ModeValue mode = new ModeValue("Mode", this, new MoonlightTargetHUD(this, "Moonlight"), new MinecraftTargetHUD(this, "Minecraft"));
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + mode.getMode().getName());
	}
	
}
