package com.moonlight.client.ui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.moonlight.client.Client;
import com.moonlight.client.animation.fake.ColorAnimate;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.util.visuals.draw.RenderUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class MainMenuButton {

	private final String name;
	private int x,y;
	private final int width, height;
	
	private ColorAnimate animate;
			
	public MainMenuButton(String name, int x, int y, int width, int height) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		animate = new ColorAnimate(new Color(255,255,255), Color.gray, 0);
	}
	
	public String getName() {
		return name;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isHover(int mouseX, int mouseY) {
		if(mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
			return true;
		}
		
		return false;
	}
	
	public void drawButton(int mouseX, int mouseY) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution sr = new ScaledResolution(mc);
		
		animate.updateLastValue(isHover(mouseX, mouseY));
		
		RenderUtil.drawRoundedRect(x,
				y, width, height, 5, new Color(0,0,0,60).getRGB(), 0);
		
		for(int i = 0; i < 8; i++) {
			animate.update();
		}
		
        int fontWidth = (int) Fonts.productSans18.getStringWidth(name);
		int fontHeight = Fonts.productSans18.getHeight();
        
		Fonts.productSans18.drawString(name, x + ((width / 2) - (fontWidth / 2)), y + ((height / 2) - (fontHeight / 2)), animate.getColor().getRGB());
		
	}
	
}
