package com.moonlight.client.value.impl;

import com.moonlight.client.event.impl.client.ValueUpdateEvent;
import com.moonlight.client.value.Value;

public class NumberValue<T> extends Value {

    private final T min;
    private final T max;

    public NumberValue(final String name, final T defaultValue, final T min, final T max) {
        super(name, defaultValue);
        this.value = defaultValue;

        this.min = min;
        this.max = max;
    }


    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getValue() {
        return (T) value;
    }

    public Class whatClass() {
        if(value instanceof Float) return Float.class;
        if(value instanceof Integer) return Integer.class;
        if(value instanceof Double) return Double.class;

        return null;
    }
    
    public void setValue(T value) {    	
    	this.value = value;
    	
		new ValueUpdateEvent(this).fire();
    }
}