package com.moonlight.client.module.impl.visuals.targethud;

import java.awt.Color;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PostAttackEvent;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.TextFormatting;

public class MoonlightTargetHUD extends Mode {

	public MoonlightTargetHUD(Module parent, String name) {
		super(parent, name);
	}
		
	private int ticksSinceLast;
	private EntityPlayer target;
			
	@Override
	public void onEnabled() {
		ticksSinceLast = 0;
	}
	
	@EventLink
	public void onPostAttack(PostAttackEvent event) {
		if(!(event.getEntity() instanceof EntityPlayer)) return;
		ticksSinceLast = 0;
		
		target = (EntityPlayer) event.getEntity();
	}
	
	@EventLink
	public void onRender2D(Render2DEvent event) {
		if (target == null) return;
		if (ticksSinceLast > 700) return;
				
		ticksSinceLast++;
		
		ScaledResolution sr = new ScaledResolution(mc);
		int x = sr.getScaledWidth() / 2 + 10, y = sr.getScaledHeight() / 2 + 10;
		
		int nameWith = Fonts.rubix18.getStringWidth(target.getGameProfile().getName());
		
		RenderUtil.drawRect(x, y, nameWith + 40, 31, new Color(12,12,12, 120).getRGB());
		GlStateManager.color(1, 1, 1, 1);
		RenderUtil.drawHead(x + 2, y + 3, 25, 25, 0, (AbstractClientPlayer) target);
		
		int targethealth = 0;
		targethealth = (int) target.getHealth();
		
		Fonts.rubix18.drawString(target.getName(), x + 29, y + 5, -1);
		Fonts.rubix16.drawString("HP: " + targethealth, x + 29, y + 14, -1);
		
		int maxWidth = nameWith + 40 - 31;
		double percentage = targethealth / 20.0D;
		
		if(percentage >= 1) percentage = 1;
		if(percentage <= 0) percentage = 0;
		
		RenderUtil.drawRect(x + 29, y + 21, maxWidth * percentage, 8, new Color(46, 67, 116).brighter().brighter().getRGB());
	}

}
