package com.moonlight.client.ui.clickui.normal;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.animation.fake.ColorAnimate;
import com.moonlight.client.animation.real.Animation;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.protection.LoginProtectionHandler;
import com.moonlight.client.ui.clickui.normal.components.PanelComponent;
import com.moonlight.client.ui.hud.gui.CustomizeScreen;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class NormalClickUI extends GuiScreen {

	private ArrayList<PanelComponent> panels = new ArrayList<PanelComponent>();
	
	private ColorAnimate animate = new ColorAnimate(new Color(255,255,255), 
			new Color(204, 174, 136), 2);

	public NormalClickUI() {
		int y = 5;
		for(Category c : Category.values()) {
			panels.add(new PanelComponent(c, 5, y));
			y += 25;
		};
	}
	
	@Override
	public void initGui() {
	}
	
	@Override
	public void onGuiClosed() {
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);
		RenderUtil.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0,0,0,30).getRGB());
		panels.forEach(p -> {
//			LoginProtectionHandler.messedUpClickUI(p);
			p.drawScreen(mouseX, mouseY);
		});
		
		boolean isHover = GuiUtil.isHover(mouseX, mouseY, 5, sr.getScaledHeight() - 25, 20, 20);
		
		animate.updateLastValue(isHover);
		
		animate.update();
		
		RenderUtil.drawPicture(5, sr.getScaledHeight() - 25, 20, 20, animate.getColor().getRGB(), "hud-editor.png");
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		panels.forEach(p -> {
			p.mouseClicked(mouseX, mouseY, mouseButton);
		});
		
		ScaledResolution sr = new ScaledResolution(mc);
		
		boolean isHover = GuiUtil.isHover(mouseX, mouseY, 5, sr.getScaledHeight() - 25, 20, 20);
		if(isHover && mouseButton == 0) {
			mc.displayGuiScreen(new CustomizeScreen());
		}
	}
	
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		panels.forEach(p -> {
			p.mouseReleased(mouseX, mouseY, state);
		});
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		panels.forEach(p -> {
			p.keyTyped(typedChar, keyCode);
		});
		super.keyTyped(typedChar, keyCode);
	}
	
}
