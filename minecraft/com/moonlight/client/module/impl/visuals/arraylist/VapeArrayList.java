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

public class VapeArrayList extends Mode {

	private NumberValue<Integer> red = new NumberValue("Red", 255, 0, 255);
	private NumberValue<Integer> green = new NumberValue("Green", 255, 0, 255);
	private NumberValue<Integer> blue = new NumberValue("Blue", 255, 0, 255);
	
	private BooleanValue smooth = new BooleanValue("Smooth Font", true);
	
	private BooleanValue rainbow = new BooleanValue("Rainbow", false);
	
	public VapeArrayList(Module parent, String name) {
		super(parent, name);
	}

	@Override
	public void onEnabled() {
	}
	
	@EventLink
	public void onRender2D(Render2DEvent event) {
		ScaledResolution sr = new ScaledResolution(mc);
		
		int index = 120;

		GlStateManager.color(1f, 1f, 1f, 1f);

		MinecraftFontRenderer font = Fonts.comfortaaNew18;
		
		GlStateManager.color(1f, 1f, 1f, 1f);
		
		int x = 5;
		int y = 22;
		
		int color = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
		
		if(rainbow.getState()) {
			color = ColorUtil.getRainbowWave(3000, index, 0.6f, 0.8f);
		}
		
		RenderUtil.drawPicture(3 + 0, 5, 50, 15, color, "vape/vapelogo.png");
		GlStateManager.color(1f, 1f, 1f, 1f);
		RenderUtil.drawPicture(3 + 49, 5.5f, 20, 14, 0, "vape/v4.png");
		
		if(!smooth.getState()) {
			for(com.moonlight.client.module.api.Module m : Client.INSTANCE.moduleManager.getSortedAnother(font)) {
				color = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
				if(rainbow.getState()) {
					color = ColorUtil.getRainbowWave(3000, index, 0.6f, 0.6f);
				}				
				
				if(!m.isEnabled() || m.getClass() == Interface.class) continue;
				if(!m.isEnabled() || m.getClass() == getParent().getClass()) continue;
							
				mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), x, y, color);
				
				y += 11;
				GlStateManager.color(1f, 1f, 1f, 1f);

				index += 120;
			}
		}else {
			y = 24;
			for(com.moonlight.client.module.api.Module m : Client.INSTANCE.moduleManager.getSortedAnother(font)) {
				color = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
				if(rainbow.getState()) {
					color = ColorUtil.getRainbowWave(3000, index, 0.6f, 0.8f);
				}				
				
				if(!m.isEnabled() || m.getClass() == Interface.class) continue;
				if(!m.isEnabled() || m.getClass() == getParent().getClass()) continue;
							
				Fonts.proxima18.drawString(m.getDisplayName(), x, y, color);
				
				y += 9;
				GlStateManager.color(1f, 1f, 1f, 1f);

				index += 120;
			}
		}
		
	}
	
}
