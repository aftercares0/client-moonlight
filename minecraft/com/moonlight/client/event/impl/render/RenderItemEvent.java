package com.moonlight.client.event.impl.render;

import com.moonlight.client.event.Event;

import net.minecraft.item.EnumAction;

public class RenderItemEvent extends Event {

	private EnumAction action;
	
	public RenderItemEvent(EnumAction action) {
		this.action = action;
	}
	
	public EnumAction getAction() {
		return action;
	}
	
	public void setAction(EnumAction action) {
		this.action = action;
	}
	
}
