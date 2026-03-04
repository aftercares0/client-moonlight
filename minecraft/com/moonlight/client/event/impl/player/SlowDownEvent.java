package com.moonlight.client.event.impl.player;

import com.moonlight.client.event.CancellableEvent;

public class SlowDownEvent extends CancellableEvent {

	private float forward, strafe;
	
	public SlowDownEvent() {
		this.forward = 0.2f;
		this.strafe = 0.2f;
	}
	
	public float getForward() {
		return forward;
	}
	
	public float getStrafe() {
		return strafe;
	}
	
	public void setForward(float forward) {
		this.forward = forward;
	}
	
	public void setStrafe(float strafe) {
		this.strafe = strafe;
	}
	
}
