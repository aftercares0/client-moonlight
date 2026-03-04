package com.moonlight.client.util.visuals.gui;

public class GuiUtil {

	public static boolean isHover(int mouseX, int mouseY, double x, double y, double width, double height) {
		if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) return true;
		return false;
	}
	
}
