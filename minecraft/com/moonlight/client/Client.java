package com.moonlight.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import com.moonlight.client.command.CommandManager;
import com.moonlight.client.component.ComponentManager;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.bus.EventBus;
import com.moonlight.client.event.impl.KeyEvent;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.module.api.manager.ModuleManager;
import com.moonlight.client.scripting.ScriptManager;
import com.moonlight.client.ui.hud.HudManager;
import com.moonlight.client.util.MinecraftInstance;

import de.florianmichael.viamcp.ViaMCP;

public enum Client {

	INSTANCE;
	
	public static Color MAIN_COLOR = new Color(84, 179, 95);
	public static boolean DEVELOPMENT_SWITCH = true;
	
	public static String NAME = "moonlight";
	public static String VERSION = "1.0";
	public static String BRANCH = "NIGHTLY";
	
	public static EventBus EVENT_BUS = new EventBus();
	
	public List<Integer> bots = new ArrayList<Integer>();
	
	public ModuleManager moduleManager;
	public CommandManager commandManager;
	public HudManager hudManager;

	public void onInit() {
		Display.setTitle(NAME + " " + VERSION);
		
		EVENT_BUS.register(this);
		
		moduleManager = new ModuleManager();
		commandManager = new CommandManager();
		hudManager = new HudManager();
		new ComponentManager();
		
		//Done scripting api, such a pain in the ass
		new ScriptManager();
		
		Fonts.bangers30.getHeight();
		
		ClickUIInstance.init();
		
		try {
			ViaMCP.create();
			
			ViaMCP.INSTANCE.initAsyncSlider();
		}catch (Exception e) {
			
		}
	}

	public void onShutDown() {
	}
	
	@EventLink
	public void onKeyInput(KeyEvent event) {
		moduleManager.forEach(m -> {			
			if(event.getKey() == m.getKeyCode()) m.toggle();
		});
	}
	
}
