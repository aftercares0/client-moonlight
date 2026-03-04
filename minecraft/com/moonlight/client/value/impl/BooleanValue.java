package com.moonlight.client.value.impl;

import com.moonlight.client.Client;
import com.moonlight.client.event.impl.client.ValueUpdateEvent;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.Value;

public class BooleanValue extends Value {
	
	private Mode mode;
	
	public BooleanValue(String name, boolean value) {
		super(name, value);
	}
	
	public BooleanValue(Mode mode, boolean value) {
		super(mode.getName(), value);
		
		this.mode = mode;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public boolean getState() {
		return (boolean) this.value;
	}
	
	public void setState(boolean b) {		
		this.value = b;
		
		if(mode != null && mode.getParent().isEnabled()) {
			if(b) {
				mode.onEnabled();
				Client.EVENT_BUS.register(mode);
			}else {
				mode.onDisabled();
				Client.EVENT_BUS.unregister(mode);
			}
		}
		
		new ValueUpdateEvent(this).fire();
	}

}
