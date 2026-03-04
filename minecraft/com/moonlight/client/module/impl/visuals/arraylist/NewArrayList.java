package com.moonlight.client.module.impl.visuals.arraylist;

import java.awt.Color;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.font.basic.MinecraftFontRenderer;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.impl.visuals.Interface;
import com.moonlight.client.util.visuals.draw.ColorUtil;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class NewArrayList extends Mode {

	public NewArrayList(Module parent, String name) {
		super(parent, name);
	}

	@Override
	public void onEnabled() {
		System.out.println("xd");
	}
	
	@EventLink
	public void onRender2D(Render2DEvent event) {
		ScaledResolution sr = new ScaledResolution(mc);
		
		int count = 1;

		GlStateManager.color(1f, 1f, 1f, 1f);

		MinecraftFontRenderer font = Fonts.comfortaaNew18;
		
		int x = sr.getScaledWidth();
		int y = 5;
		for(com.moonlight.client.module.api.Module m : Client.INSTANCE.moduleManager.getSortedAnother(font)) {
			int color = -1;

			color = ColorUtil.interpolateColorsBackAndForth(30, count * 30 , new Color(46, 67, 116).brighter().brighter(), new Color(91, 8, 136).brighter()
					, true).getRGB();
			
			int width = font.getStringWidth(m.getDisplayName());
			
			int actualX = x - width;
			
			if(!m.isEnabled() || m.getClass() == Interface.class) continue;
			if(!m.isEnabled() || m.getClass() == getParent().getClass()) continue;
			
			RenderUtil.drawRect(actualX - 10, y, width + 5, 10, new Color(0,0,0,80).getRGB());
			
			
			font.drawString(m.getDisplayName(), actualX - 8 + 0.2f, y + 2 + 0.3f, new Color(0,0,0,80).getRGB());
			font.drawString(m.getDisplayName(), actualX - 8, y + 2, color);
			
			y += 10;
			GlStateManager.color(1f, 1f, 1f, 1f);

			
			count++;
		}
	}
	
}
