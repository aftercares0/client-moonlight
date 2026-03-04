package com.moonlight.client.module.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.Value;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;

public class Module implements MinecraftInstance {

	private final String name;
	private final String description;
	private final Category category;
	
	private String displayName;
	
	private boolean enabled;
	private int keyCode;
    
	protected ArrayList<Value> values = new ArrayList<Value>();
	
    public Module() {
    	this.enabled = getClass().getAnnotation(ModuleInfo.class).autoEnabled();
    	this.name = getClass().getAnnotation(ModuleInfo.class).name();
    	this.description = getClass().getAnnotation(ModuleInfo.class).description();
    	this.category = getClass().getAnnotation(ModuleInfo.class).category();
    	this.keyCode = getClass().getAnnotation(ModuleInfo.class).key();
    	
    	this.displayName = name;
	}
    
    public Module(String name, String description, Category category, int key, boolean enabled) {
    	this.enabled = enabled;
    	this.name = name;
    	this.description = description;
    	this.category = category;
    	this.keyCode = key;
    	
    	this.displayName = name;
    	
    	setEnabled(enabled);
    }
    
    public void onEnabled() {

    }

    public void onDisable() {
    }
    
    public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
		setup();
	}
    
    public void setup() {
    	if(this.enabled) {
			superEnabled();
		}else {
			superDisabled();
		}
    }
    
    private void superEnabled() {
        onEnabled();
        
        Client.EVENT_BUS.register(this);
        
        for(Value v : getValuesAll()) {
            if(v instanceof ModeValue) {
                ModeValue value = (ModeValue) v;
                value.getMode().onEnabled();
                Client.EVENT_BUS.register(value.getMode());
            }
            
            if(v instanceof BooleanValue) {
            	BooleanValue value = (BooleanValue) v;
            	if(value.getMode() != null && value.getState()) {
            		value.getMode().onEnabled();
                    Client.EVENT_BUS.register(value.getMode());
            	}
            }
        }
    }
    
    private void superDisabled() {
        onDisable();
        
        Client.EVENT_BUS.unregister(this);
        
        for(Value v : getValuesAll()) {
            if(v instanceof ModeValue) {
                ModeValue value = (ModeValue) v;
                value.getMode().onDisabled();
                for(Mode m : value.getModes()) {
                    Client.EVENT_BUS.unregister(m);
                }
            }
            
            if(v instanceof BooleanValue) {
            	BooleanValue value = (BooleanValue) v;
            	if(value.getMode() != null && value.getState()) {
            		value.getMode().onDisabled();
                    Client.EVENT_BUS.unregister(value.getMode());
            	}
            }
        }
    }
    
    public void toggle() {
    	this.setEnabled(!enabled);
    }
    
    public boolean isEnabled() {
		return enabled;
	}
    
    public String getDescription() {
		return description;
	}
    
    public Category getCategory() {
		return category;
	}
    
    public String getName() {
		return name;
	}
    
    public int getKeyCode() {
		return keyCode;
	}
    
    public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
    
    public String getDisplayName() {
		return displayName;
	}
    
    public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
    
    public List<Value> getValuesAll() {
        final CopyOnWriteArrayList<Value> values = new CopyOnWriteArrayList<>();

        for (final Field field : getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                final Object o = field.get(this);

                if (o instanceof Value) {
                    values.add((Value) o);
                    
                    if(o instanceof ModeValue) {
                    	ModeValue wo = (ModeValue) o;
                    	values.addAll(wo.getMode().getValues());
                    }
                }
            } catch (IllegalAccessException e) {
                //Do nothing
            }
        }
        values.addAll(this.values);
        
        return values;
    }
    
    public List<Value> getValues() {
        final CopyOnWriteArrayList<Value> values = new CopyOnWriteArrayList<>();

        for (final Field field : getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                final Object o = field.get(this);

                if (o instanceof Value) {
                    values.add((Value) o);
                }
            } catch (IllegalAccessException e) {
                //Do nothing
            }
        }
        values.addAll(this.values);

        return values;
    }
}