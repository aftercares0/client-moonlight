package com.moonlight.client.ui.clickui.dropdown;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.animation.fake.ColorAnimate;
import com.moonlight.client.animation.real.Animation;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.protection.LoginProtectionHandler;
import com.moonlight.client.ui.clickui.dropdown.components.PanelComponent;
import com.moonlight.client.ui.hud.gui.CustomizeScreen;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class DropdownClickUI extends GuiScreen {

	private ArrayList<PanelComponent> panels = new ArrayList<PanelComponent>();
	
	private ColorAnimate animate = new ColorAnimate(new Color(255,255,255), 
			new Color(Client.MAIN_COLOR.getRGB()), 2);

	public DropdownClickUI() {
		int y = 5;
		for(Category c : Category.values()) {
			panels.add(new PanelComponent(c, 5, y));
			y += 25;
		};
	}
	
	@Override
	public void initGui() {
		mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/antialias.json"));
	}
	
	@Override
	public void onGuiClosed() {
		mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/fxaa.json"));
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);
		mc.fontRendererObj.drawStringWithShadow(Client.NAME + " " + Client.VERSION, 
				sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(Client.NAME + " " + Client.VERSION), 
				sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT, -1);
		
		panels.forEach(p -> {
			LoginProtectionHandler.messedUpClickUI(p);
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
