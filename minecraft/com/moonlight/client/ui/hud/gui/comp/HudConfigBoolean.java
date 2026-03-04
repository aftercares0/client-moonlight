package com.moonlight.client.ui.hud.gui.comp;

import java.awt.Color;

import com.moonlight.client.Client;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.ui.clickui.dropdown.components.settings.SettingComponent;
import com.moonlight.client.ui.component.Component;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;
import com.moonlight.client.value.impl.BooleanValue;

public class HudConfigBoolean extends SettingComponent {

	private BooleanValue value;
	
	protected double width = 10, height = 10;
	
	public HudConfigBoolean(BooleanValue value, double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
		
		this.value = value;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RenderUtil.drawRect(x, y, 100, 20, new Color(0, 0, 0, 60).getRGB());
		RenderUtil.drawRect(x + 5, y + (10) - (height / 2), width, height, value.getState() ? 
				Client.MAIN_COLOR.getRGB() : new Color(0, 0, 0, 209).getRGB());
		
		Fonts.productSans18.drawString(value.getName().toLowerCase(), x + 18, (int) (y + (20 / 2) - 
				(Fonts.productSans18.getHeight() / 2)) + 0.5f, -1);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(GuiUtil.isHover(mouseX, mouseY, x + 5, y + (10) - (height / 2), width, height)) {			
			if(button == 0) {
				value.setState(!value.getState());
			}
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		
	}

}
