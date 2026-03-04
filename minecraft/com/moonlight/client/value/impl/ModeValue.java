package com.moonlight.client.value.impl;

import java.util.ArrayList;
import java.util.List;

import com.moonlight.client.event.impl.client.ValueUpdateEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.Value;

public class ModeValue extends Value {

    private final List<Mode> modes = new ArrayList<>();

    public ModeValue(String name) {
        super(name, null);
    }
    
    public ModeValue(String name, Mode value) {
        super(name, value);

        modes.add(value);
    }

    public ModeValue(String name, Module parent , String... values) {
        super(name, null);

        for(String v : values) {
            modes.add(new Mode(parent, v));
        }

        setMode(0);
    }
    
    public ModeValue(String name, Module parent , Mode... modes) {
        super(name, null);

        for(Mode m : modes) {
            this.modes.add(m);
        }

        setMode(0);
    }
    
    public Mode getMode() {
    	return (Mode) value;
    }
    
    public void setMode(int index) {    	
    	value = modes.get(index);
    	
		new ValueUpdateEvent(this).fire();
    }
    
    public List<Mode> getModes() {
		return modes;
	}
    public int getIndexMode() {
        return modes.indexOf(getMode());
    }
}