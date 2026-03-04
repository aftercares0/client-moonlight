package com.moonlight.client.ui.component;

import java.io.IOException;

public abstract class Component {

	public abstract void drawScreen(int mouseX, int mouseY);
	public abstract void mouseClicked(int mouseX, int mouseY, int button);
	public abstract void mouseReleased(int mouseX, int mouseY, int state);
	public abstract void keyTyped(char typedChar, int keyCode);
	
}
