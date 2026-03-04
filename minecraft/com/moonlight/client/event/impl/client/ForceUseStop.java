package com.moonlight.client.event.impl.client;

import com.moonlight.client.event.CancellableEvent;

public class ForceUseStop extends CancellableEvent {

	public boolean forcePress;
	
	public ForceUseStop(boolean forcePress) {
		this.forcePress = forcePress;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		forcePress = !cancelled;
		super.setCancelled(cancelled);
	}
	
}
