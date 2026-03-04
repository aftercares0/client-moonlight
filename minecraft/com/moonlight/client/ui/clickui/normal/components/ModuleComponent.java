package com.moonlight.client.ui.clickui.normal.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.client.ValueUpdateEvent;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.ui.clickui.normal.components.settings.BooleanComponent;
import com.moonlight.client.ui.clickui.normal.components.settings.ModeComponent;
import com.moonlight.client.ui.clickui.normal.components.settings.NumberComponent;
import com.moonlight.client.ui.clickui.normal.components.settings.SettingComponent;
import com.moonlight.client.ui.component.Component;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;
import com.moonlight.client.value.Value;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

public class ModuleComponent extends Component {

	public Module module;
	
	private double width = 100, height = 20;
	private int x, y;
	private boolean expanded;
	
	private CopyOnWriteArrayList<SettingComponent> settings = new CopyOnWriteArrayList<SettingComponent>();
		
	public ModuleComponent(Module module, double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
		
		this.module = module;
				
		int yL = (int) (y + height);
		for(Value v : module.getValues()) {
			if(v instanceof BooleanValue) {
				settings.add(new BooleanComponent((BooleanValue) v, x, yL));
			}
			if(v instanceof ModeValue) {
				settings.add(new ModeComponent((ModeValue) v, (int) x, yL));
				
				for(Value mv : ((ModeValue)v).getMode().getValues()) {
					if(mv instanceof BooleanValue) {
						settings.add(new BooleanComponent((BooleanValue) mv, x, yL));
					}
					if(mv instanceof ModeValue) {
						settings.add(new ModeComponent((ModeValue) mv, (int) x, yL));
					}
					if(mv instanceof NumberValue) {
						settings.add(new NumberComponent((NumberValue) mv, (int) x, yL));
					}
					
					yL += height;
				}
			}
			if(v instanceof NumberValue) {
				settings.add(new NumberComponent((NumberValue) v, (int) x, yL));
			}
			
			yL += height;
		}
		
		Client.EVENT_BUS.register(this);
	}
	
	@EventLink
	public void onUpdateValue(ValueUpdateEvent event) {
		if(!(event.getValue() instanceof ModeValue)) return;
				
		settings.clear();
						
		int yL = (int) (y + height);
		for(Value v : module.getValues()) {
			if(v instanceof BooleanValue) {
				settings.add(new BooleanComponent((BooleanValue) v, x, yL));
			}
			if(v instanceof ModeValue) {
				settings.add(new ModeComponent((ModeValue) v, (int) x, yL));
				
				for(Value mv : ((ModeValue)v).getMode().getValues()) {
					if(mv instanceof BooleanValue) {
						settings.add(new BooleanComponent((BooleanValue) mv, x, yL));
					}
					if(mv instanceof ModeValue) {
						settings.add(new ModeComponent((ModeValue) mv, (int) x, yL));
					}
					if(mv instanceof NumberValue) {
						settings.add(new NumberComponent((NumberValue) mv, (int) x, yL));
					}
					
					yL += height;
				}
			}
			if(v instanceof NumberValue) {
				settings.add(new NumberComponent((NumberValue) v, (int) x, yL));
			}
			
			yL += height;
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		int yL = (int) (y + height);
		for(SettingComponent c : settings) {
			c.setX(x);
			c.setY(yL);
			
			yL += height;
		}
		
		Color color = new Color(204, 174, 136).darker().darker();
		
		RenderUtil.drawRect(x, y, width, height, color.getRGB());
		Fonts.productSans18.drawString(module.getName(), x + 4, (int) (y + (height / 2) -
				(Fonts.productSans18.getHeight() / 2)), module.isEnabled() ? color.darker().getRGB() : Color.lightGray.getRGB());
		Fonts.productSans18.drawString(expanded ? ">" : "<", (int) (x + width - 9), (int) (y + (height / 2) - 
				(Fonts.productSans18.getHeight() / 2)), Color.lightGray.getRGB());
		
		if(expanded) {
			settings.forEach(s -> {
				s.drawScreen(mouseX, mouseY);
			});
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(GuiUtil.isHover(mouseX, mouseY, x, y, width, height)) {
			if(button == 1) {
				expanded = !expanded;
			}			
			if(button == 0) {
				module.toggle();
			}
		}
		
		if(expanded) {
			settings.forEach(s -> {
				s.mouseClicked(mouseX, mouseY, button);
			});
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if(expanded) {
			settings.forEach(s -> {
				s.mouseReleased(mouseX, mouseY, state);
			});
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		if(expanded) {
			settings.forEach(s -> {
				s.keyTyped(typedChar, keyCode);
			});
		}
	}
	
	
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getWidth() {
		return width;
	}
	
	public boolean isExpanded() {
		return expanded;
	}

}
