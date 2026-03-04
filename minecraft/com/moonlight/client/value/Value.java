package com.moonlight.client.value;

public class Value {

	protected final String name;
	protected Object value;
	
	public Value(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
}
