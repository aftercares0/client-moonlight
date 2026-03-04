package com.moonlight.client.module.impl.visuals.targethud;

import java.awt.Color;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.TextFormatting;

public class MinecraftTargetHUD extends Mode {

	public MinecraftTargetHUD(Module parent, String name) {
		super(parent, name);
	}
	
	private int ticksSinceLast;
	private EntityPlayer target;
			
	@Override
	public void onEnabled() {
		ticksSinceLast = 0;
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {
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
		
		int nameWith = mc.fontRendererObj.getStringWidth(target.getGameProfile().getName());

		double percentage = (Math.abs(target.getHealth()) / 20);
		if(String.valueOf(target.getHealth()).equalsIgnoreCase("0")) {
			percentage = 0;
		}

		RenderUtil.drawRect(x, y, nameWith + 20, 25, new Color(0,0,0,80).getRGB());
		RenderUtil.drawRect(x, y, 1, 25, new Color(0,0,0,120).getRGB());
		RenderUtil.drawRect(x, y, 1, 25 * percentage, Color.red.getRGB());
		
		mc.fontRendererObj.drawStringWithShadow(target.getName() + " " + TextFormatting.RED + Math.round(target.getHealth()), x + 3, y + 2, -1);
		mc.fontRendererObj.drawStringWithShadow(
				target.getHealth() <= mc.thePlayer.getHealth() ? TextFormatting.GREEN + "Winning" : TextFormatting.RED + "Losing", x + 3, y + 12, -1);
	}

}
