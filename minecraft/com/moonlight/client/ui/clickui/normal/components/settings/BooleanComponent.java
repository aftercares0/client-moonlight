package com.moonlight.client.ui.clickui.normal.components.settings;

import java.awt.Color;

import com.moonlight.client.Client;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.ui.component.Component;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;
import com.moonlight.client.value.impl.BooleanValue;

public class BooleanComponent extends SettingComponent {

	private BooleanValue value;
	
	protected double width = 10, height = 10;
	
	public BooleanComponent(BooleanValue value, double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
		
		this.value = value;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		Color color = new Color(204, 174, 136).darker().darker().darker();
		
		RenderUtil.drawRect(x, y, 100, 20, color.getRGB());
		RenderUtil.drawRect(x + 5, y + (10) - (height / 2), width, height, value.getState() ? 
				color.brighter().getRGB() : color.darker().getRGB());
		
		Fonts.productSans18.drawString(value.getName(), x + 18, (int) ((int) (y + (20 / 2) -
                        (Fonts.productSans18.getHeight() / 2)) + 0.5f), -1);
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
