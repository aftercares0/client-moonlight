package com.moonlight.client.ui.hud.gui.comp;

import java.awt.*;

import com.moonlight.client.Client;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.ui.clickui.dropdown.components.settings.SettingComponent;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;
import com.moonlight.client.value.impl.ModeValue;

public class HudConfigMode extends SettingComponent {

	public ModeValue value;
	protected double width = 100, height = 20;
	
    public HudConfigMode(ModeValue value, int x, int y) {
        this.x = x;
        this.y = y;
        
        this.value = value;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
		RenderUtil.drawRect(x, y, width, height, new Color(0, 0, 0, 60).getRGB());

		Fonts.productSans18.drawString((value.getName() + ": " + value.getMode().getName()).toLowerCase(), x + 5, (float) (y + (height / 2)
                - (Fonts.productSans18.getHeight() / 2)), - 1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {    	
        if(GuiUtil.isHover(mouseX, mouseY, x, y, width, height) && button == 0) {
            int currentIndex = value.getModes().indexOf(value.getMode());

            if(currentIndex >= (value.getModes().size() - 1)) {
                value.setMode(0);
            }else if(currentIndex < (value.getModes().size() - 1)) {
                value.setMode(currentIndex + 1);
            }
        }else if(GuiUtil.isHover(mouseX, mouseY, x, y, width, height) && button == 1) {
            int currentIndex = value.getModes().indexOf(value.getMode());
            
            if(currentIndex <= 0) {
                value.setMode(value.getModes().size() - 1);
            }else if(currentIndex > 0) {
                value.setMode(currentIndex - 1);
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
