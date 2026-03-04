package com.moonlight.client.event.impl;

import com.moonlight.client.event.CancellableEvent;
import com.moonlight.client.event.Event;

public class KeyEvent extends CancellableEvent {

	private final int key;
	
	public KeyEvent(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
	
}
