package com.moonlight.client.ui.clickui.dropdown.components.settings;

import java.awt.*;
import java.text.DecimalFormat;

import com.moonlight.client.Client;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;
import com.moonlight.client.value.impl.NumberValue;

public class NumberComponent extends SettingComponent {

    public NumberValue value;

    private boolean isDrag;
    
	protected double width = 100, height = 20;
    
    public NumberComponent(NumberValue value, int x, int y) {
        this.x = x;
        this.y = y;

        this.value = value;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        Number currentValue = (Number) value.getValue();
        Number max = (Number) value.getMax();
        Number min = (Number) value.getMin();
        double percentage = 0;
        if(value.getValue() instanceof Float) {
            percentage = (currentValue.floatValue() / max.floatValue());
        }else if(value.getValue() instanceof Double) {
            percentage = (currentValue.doubleValue() / max.doubleValue());
        }else if(value.getValue() instanceof Integer) {
            percentage = (Double.valueOf(currentValue.intValue()) / max.intValue());
        }
        
		RenderUtil.drawRect(x, y, width, height, new Color(0, 0, 0, 200).getRGB());
        
        if(isDrag) {
            double percentage1 = (Double.valueOf(mouseX) - (x + 5)) / (width - 10);

            if(value.getValue() instanceof Float) {
                float newValue = (float) Math.max(min.floatValue(), Math.min(max.floatValue(), max.floatValue() * percentage1));
                value.setValue(newValue);
            }else if(value.getValue() instanceof Double) {
                double newValue = (double) Math.max(min.doubleValue(), Math.min(max.doubleValue(), max.doubleValue() * percentage1));
                value.setValue(newValue);
            }else if(value.getValue() instanceof Integer) {
                int newValue = (int) Math.max(min.intValue(), Math.min(max.intValue(), max.intValue() * percentage1));
                value.setValue(newValue);
            }
        }

        RenderUtil.drawRect(x + 5, y + 2, width - 10, height - 4, new Color(0,0,0,80).darker().getRGB());
        RenderUtil.drawRect(x + 5, y + 2, (int) ((width - 10) * (percentage)), height - 4, Client.MAIN_COLOR.getRGB());

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String s = value.getName() + ": " + (value.getValue() instanceof Integer ? value.getValue() : decimalFormat.format(value.getValue()));
        
        Fonts.productSans18.drawString(s.toLowerCase(), x + 10, (int) (y + (height / 2)
                        - (Fonts.productSans18.getHeight() / 2)), -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0 && GuiUtil.isHover(mouseX, mouseY, x + 5, y + 2, width - 10, height - 4)) {
            isDrag = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        isDrag = false;
    }


	@Override
	public void keyTyped(char typedChar, int keyCode) {
	}
}
