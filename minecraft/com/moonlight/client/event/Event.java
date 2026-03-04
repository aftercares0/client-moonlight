package com.moonlight.client.event;

import com.moonlight.client.Client;

public class Event {
	
	public Event fire() {
		Client.EVENT_BUS.handle(this);
		return this;
	}
	
}
