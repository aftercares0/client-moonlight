package com.moonlight.client.ui.hud;

import java.awt.Color;
import java.util.ArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.ui.hud.component.TextComponent;

public class HudManager extends ArrayList<HudComponent> {

	public HudManager() {
		TextComponent clientName = new TextComponent();
		
		//add(new TextComponent(Client.NAME + " " + Client.VERSION, 5, 5, Color.white));
	}
	
}
