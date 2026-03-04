package com.moonlight.client.event.impl.update;

import com.moonlight.client.event.CancellableEvent;

public class PreMotionEvent extends CancellableEvent {
	
	private double x, y, z;
	private float pitch, yaw;
	private boolean onGround;

	public PreMotionEvent(double x, double y, double z, float pitch, float yaw, boolean onGround) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.onGround = onGround;
	}

	public boolean isOnGround() {
		return onGround;
	}
	
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	
	
}
