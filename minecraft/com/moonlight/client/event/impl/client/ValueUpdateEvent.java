package com.moonlight.client.event.impl.client;

import com.moonlight.client.event.Event;
import com.moonlight.client.value.Value;

public class ValueUpdateEvent extends Event {

	private final Value value;
	
	public ValueUpdateEvent(Value value) {
		this.value = value;
	}
	
	public Value getValue() {
		return value;
	}
	
}
