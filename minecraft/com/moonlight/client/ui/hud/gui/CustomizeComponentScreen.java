package com.moonlight.client.ui.hud.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.ui.clickui.dropdown.components.settings.SettingComponent;
import com.moonlight.client.ui.hud.HudComponent;
import com.moonlight.client.ui.hud.gui.comp.HudConfigBoolean;
import com.moonlight.client.ui.hud.gui.comp.HudConfigMode;
import com.moonlight.client.ui.hud.gui.comp.HudConfigNumber;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;
import com.moonlight.client.value.Value;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

public class CustomizeComponentScreen extends GuiScreen {

	private HudComponent component;
	
	private CopyOnWriteArrayList<SettingComponent> list = new CopyOnWriteArrayList<SettingComponent>();
	
	private int finalX, finalY;
	
	public CustomizeComponentScreen(HudComponent component) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		this.component = component;
		
		int middleX = sr.getScaledWidth() / 2;
		int y = sr.getScaledHeight() / 2 + 25;
 		for(Value v : component.getValues()) {
			if(v.getClass() == BooleanValue.class) {
				list.add(new HudConfigBoolean((BooleanValue) v, middleX - (400 / 2) + 5, y));
			}
			if(v.getClass() == NumberValue.class) {
				list.add(new HudConfigNumber((NumberValue) v, middleX - (400 / 2) + 5, y));
			}
			if(v.getClass() == ModeValue.class) {
				list.add(new HudConfigMode((ModeValue) v, middleX - (400 / 2) + 5, y));
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);
		
		int middleX = sr.getScaledWidth() / 2;
		int middleY = sr.getScaledHeight() / 2;
		
		RenderUtil.drawBorderedRect(middleX - (400 / 2), 
				middleY - (250 / 2), 400, 250, 2, Client.MAIN_COLOR.getRGB(), new Color(20,20,20).getRGB());
		RenderUtil.drawBorderedRect(middleX - (400 / 2), 
				middleY - (250 / 2), 400, 20, 2, Client.MAIN_COLOR.getRGB(), new Color(20,20,20).getRGB());
		Fonts.productSans25.drawString("Customizing...", middleX - (400 / 2) + 5, middleY - (250 / 2) + 5, -1);		
		int y = middleY - (250 / 2) + 25;
		int x = middleX - (400 / 2) + 10;
		for(SettingComponent c: list) {
			c.setX(x);
			c.setY(y);
			
			c.drawScreen(mouseX, mouseY);
			
			if(y + 25 >= middleY - (250 / 2) + 250) {
				y = middleY - (250 / 2) + 25;
				x = middleX - (400 / 2) + 120;
			}else {
				y += 25;
			}
		}
		
		finalX = x;
		finalY = y;
		
		if(component.isDeletedable()) {
			Fonts.productSans25.drawString("Delete Element", finalX, finalY, -1);		
		}
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(SettingComponent c: list) {
			c.mouseReleased(mouseX, mouseY, state);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		ScaledResolution sr = new ScaledResolution(mc);
		int middleX = sr.getScaledWidth() / 2;
		int middleY = sr.getScaledHeight() / 2;
		int y = middleY - (250 / 2) + (list.size() * 25) + 25;
		
		if(component.isDeletedable()) {
			if(mouseButton == 0 && GuiUtil.isHover(mouseX, mouseY, finalX, finalY, 
					Fonts.productSans25.getStringWidth("Delete Element"), Fonts.productSans25.getHeight())) {
				Client.INSTANCE.hudManager.remove(component);
				mc.displayGuiScreen(new CustomizeScreen());
			}
		}
		
		for(SettingComponent c: list) {
			c.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
}
