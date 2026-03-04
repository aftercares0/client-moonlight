package com.moonlight.client.value;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.MinecraftInstance;

public class Mode implements MinecraftInstance
{
    private String name;
    private final Module parent;

    public Mode(Module parent, String name) {
        this.parent = parent;
		this.name = name;
    }

    public void onEnabled() {
    }

    public void onDisabled() {
    }

    public String getName() {
        return name;
    }

    public Module getParent() {
        return parent;
    }
    
    public List<Value> getValues() {
        final List<Value> values = new ArrayList<>();

        for(final Field field : getClass().getDeclaredFields()) {
            try{
                field.setAccessible(true);

                final Object o = field.get(this);

                if(o instanceof Value) values.add((Value) o);
            }catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return values;
    }
}