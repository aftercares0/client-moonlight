package com.moonlight.client.ui.clickui.dropdown.components;

import java.awt.Color;
import java.util.ArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.manager.ModuleManager;
import com.moonlight.client.ui.component.Component;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;

public class PanelComponent extends Component {

	private ArrayList<ModuleComponent> modules = new ArrayList<ModuleComponent>();
	
	private Category category;
	
	private double width = 100, height = 20;
	public int x, y, dragX, dragY;
	private boolean drag, expanded;
	
	public PanelComponent(Category category, double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
		
		this.category = category;
		
		int yL = (int) (y + height);
		for(com.moonlight.client.module.api.Module m : Client.INSTANCE.moduleManager.get(category)) {
			modules.add(new ModuleComponent(m, x, yL));
			
			yL += height;
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		if(drag) {
			int y = (int) ((mouseY - dragY) + height);
			for(ModuleComponent mc : modules) {
				mc.setX(mouseX - dragX);
				mc.setY(y);
				
				y += mc.getHeight() + (mc.isExpanded() ? (20 * (mc.module.getValuesAll().size())) : 0);
			}
			
			this.x = mouseX - dragX;
			this.y = mouseY - dragY;
		}
		
		int yL = (int) (y + height);
		for(ModuleComponent mc : modules) {
			mc.setX(x);
			mc.setY(yL);
			
			yL += mc.getHeight() + (mc.isExpanded() ? (20 * (mc.module.getValuesAll().size())) : 0);
		}
		
		RenderUtil.drawBorderedRect(x, y, width, height, 1, Client.MAIN_COLOR.getRGB(), new Color(0, 0, 0, 190).getRGB());
		Fonts.productSans18.drawString(category.getName().toLowerCase(), x + 4, (int) (y + (height / 2) - 
				(Fonts.productSans18.getHeight() / 2)), -1);
		Fonts.productSans18.drawString(expanded ? "-" : "+", (int) (x + width - 9), (int) (y + (height / 2) - 
				(Fonts.productSans18.getHeight() / 2)), Color.lightGray.getRGB());
		
		
		if(expanded) {
			for(ModuleComponent mc : modules) {
				mc.drawScreen(mouseX, mouseY);
			}
		}
		
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(GuiUtil.isHover(mouseX, mouseY, x, y, width, height)) {
			if(button == 0) {
				drag = true;
				dragX = mouseX - x;
				dragY = mouseY - y;
			}
			expanded = button == 1 ? !expanded : expanded;
		}
		
		if(expanded) {
			modules.forEach(m -> {
				m.mouseClicked(mouseX, mouseY, button);
			});
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		drag = false;
		
		if(expanded) {
			modules.forEach(m -> {
				m.mouseReleased(mouseX, mouseY, state);
			});
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		if(expanded) {
			modules.forEach(m -> {
				m.keyTyped(typedChar, keyCode);
			});
		}
	}

}
