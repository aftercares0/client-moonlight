package com.moonlight.client.ui.hud.gui;

import java.awt.Color;
import java.io.IOException;

import com.moonlight.client.Client;
import com.moonlight.client.animation.fake.ColorAnimate;
import com.moonlight.client.ui.hud.HudComponent;
import com.moonlight.client.ui.hud.component.TextComponent;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class CustomizeScreen extends GuiScreen {

	private ColorAnimate animate = new ColorAnimate(new Color(255,255,255), 
			new Color(Client.MAIN_COLOR.getRGB()), 2);
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);
		boolean isHover = GuiUtil.isHover(mouseX, mouseY, 5, sr.getScaledHeight() - 25, 20, 20);
		
		animate.update();
		
		animate.updateLastValue(isHover);
		RenderUtil.drawPicture(5, sr.getScaledHeight() - 25, 20, 20, animate.getColor().getRGB(), "add.png");
		
		for(HudComponent c : Client.INSTANCE.hudManager) {
			c.render(mouseX, mouseY);
			
			if(c.isDrag()) {				
				c.setX(mouseX - c.getDragX());
				c.setY(mouseY - c.getDragY());
			}
		}
		
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		ScaledResolution sr = new ScaledResolution(mc);
		for(HudComponent c : Client.INSTANCE.hudManager) {
			c.click(mouseX, mouseY, mouseButton);
			
			if(GuiUtil.isHover(mouseX, mouseY, c.getX(), c.getY(), c.getWidth(), c.getHeight())) {
				c.setDrag(true);
				c.setDragX(mouseX - c.getX());
				c.setDragY(mouseY - c.getY());
			}
		}
		
		boolean isHover = GuiUtil.isHover(mouseX, mouseY, 5, sr.getScaledHeight() - 25, 20, 20);
		if(isHover && mouseButton == 0) {
			Client.INSTANCE.hudManager.add(new TextComponent("Hello world!", 50, 50, Color.white));
		}
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(HudComponent c : Client.INSTANCE.hudManager) {
			c.setDrag(false);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		for(HudComponent c : Client.INSTANCE.hudManager) {
			c.key(typedChar, keyCode);
		}
		super.keyTyped(typedChar, keyCode);
	}
	
}
