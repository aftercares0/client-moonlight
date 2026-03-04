package com.moonlight.client.command;

import com.moonlight.client.util.MinecraftInstance;

public abstract class Command implements MinecraftInstance {

	public String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public abstract void onCommand(String[] args);
	
}
