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
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class OldCustomizeableArrayList extends Mode {

	private ModeValue color = new ModeValue("Color", getParent(), "Static", "Fade", "ReversedFade" , "Rainbow", "Tenacity");

	private NumberValue<Integer> red = new NumberValue("Red", Client.MAIN_COLOR.getRed(), 0, 255);
	private NumberValue<Integer> green = new NumberValue("Green", Client.MAIN_COLOR.getGreen(), 0, 255);
	private NumberValue<Integer> blue = new NumberValue("Blue", Client.MAIN_COLOR.getBlue(), 0, 255);
	
	private BooleanValue isShadow = new BooleanValue("Shadow", false);
	
	private BooleanValue background = new BooleanValue("Background", false);
	private NumberValue<Integer> bred = new NumberValue("Back R", 0, 0, 255);
	private NumberValue<Integer> bgreen = new NumberValue("Back G", 0, 0, 255);
	private NumberValue<Integer> bblue = new NumberValue("Back B", 0, 0, 255);
	private NumberValue<Integer> balpha = new NumberValue("Back B", 80, 0, 255);
	
	private ModeValue fontMode = new ModeValue("Font", getParent(), "Minecraft","ProdSans 16", 
			"ProdSans 18", "ProdSans 25", "ProdSans 30", "ProdSans 40", "SF UI 16", "SF UI 18",
			"SF UI 25", "SF UI 30", "SF UI 40", "Comfortaa 16", "Comfortaa 18", "Comfortaa 25", "Comfortaa 30", "Comfortaa 40");
	
	public OldCustomizeableArrayList(Module parent, String name) {
		super(parent, name);
	}

	@EventLink
	public void onRender2D(Render2DEvent event) {
		ScaledResolution sr = new ScaledResolution(mc);
		
		int index = 1;

		GlStateManager.color(0f, 0f, 0f, 0f);

		int count = 0;

		if(fontMode.getMode().getName().equalsIgnoreCase("Minecraft")) {
			int x = sr.getScaledWidth();
			int y = 5;
			for(com.moonlight.client.module.api.Module m : Client.INSTANCE.moduleManager.getSorted()) {
				int color = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();

				if(this.color.getMode().getName().equalsIgnoreCase("Fade")) {
					color = ColorUtil.getColorWave(3000, index, new Color(red.getValue(), green.getValue(), blue.getValue()));
				}else if(this.color.getMode().getName().equalsIgnoreCase("Rainbow")) {
					color = ColorUtil.getRainbowWave(3000, index, 0.6f, 0.8f);
				}else if(this.color.getMode().getName().equalsIgnoreCase("Tenacity")) {
					color = ColorUtil.interpolateColorsBackAndForth(15, count * 30 , new Color(236, 133, 209), new Color(28, 167, 222), false).getRGB();
				}else if(this.color.getMode().getName().equalsIgnoreCase("ReversedFade")) {
					color = ColorUtil.interpolateColorsBackAndForth(15, count * 80 , new Color(red.getValue(), green.getValue(), blue.getValue()), Color.white , false).getRGB();
				}

				int width = mc.fontRendererObj.getStringWidth(m.getDisplayName());
				
				int actualX = x - width;
				if(!m.isEnabled() || m.getClass() == Interface.class) continue;
				if(!m.isEnabled() || m.getClass() == getParent().getClass()) continue;
				
				mc.fontRendererObj.drawString(m.getDisplayName(), actualX - 5, y, color, isShadow.getState());
				
				y += 9;
				index += 120;

				count++;
			}
		}else {
			MinecraftFontRenderer font = Fonts.getFontByName(fontMode.getMode().getName());
			
			int x = sr.getScaledWidth();
			int y = 5;
			for(com.moonlight.client.module.api.Module m : Client.INSTANCE.moduleManager.getSortedAnother(font)) {
				int color = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();

				if(this.color.getMode().getName().equalsIgnoreCase("Fade")) {
					color = ColorUtil.getColorWave(3000, index, new Color(red.getValue(), green.getValue(), blue.getValue()));
				}else if(this.color.getMode().getName().equalsIgnoreCase("Rainbow")) {
					color = ColorUtil.getRainbowWave(3000, index, 0.6f, 0.8f);
				}else if(this.color.getMode().getName().equalsIgnoreCase("Tenacity")) {
					color = ColorUtil.interpolateColorsBackAndForth(15, count * 20 , new Color(236, 133, 209), new Color(28, 167, 222), false).getRGB();
				}else if(this.color.getMode().getName().equalsIgnoreCase("ReversedFade")) {
					color = ColorUtil.interpolateColorsBackAndForth(15, count * 80 , new Color(red.getValue(), green.getValue(), blue.getValue()),
							Color.white , false).getRGB();
				}
				
				int width = font.getStringWidth(m.getDisplayName());
				
				int actualX = x - width;
				if(!m.isEnabled() || m.getClass() == Interface.class) continue;
				if(!m.isEnabled() || m.getClass() == getParent().getClass()) continue;
				
				if(isShadow.getState()) font.drawStringWithShadow(m.getDisplayName(), actualX - 5, y, color);
				else font.drawString(m.getDisplayName(), actualX - 5, y, color);
				
				y += font.getHeight() + 1;
				index += 120;
				GlStateManager.color(0f, 0f, 0f, 0f);

				count++;
			}
		}
	}
	
}
