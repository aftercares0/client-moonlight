package com.moonlight.client.event.impl.strafe;

import com.moonlight.client.event.CancellableEvent;

public class JumpEvent extends CancellableEvent {

	private float yaw;
    private double motionY;
	
    public JumpEvent(float yaw, double motionY) {
        this.yaw = yaw;
        this.motionY = motionY;
    }

    public float getYaw() {
		return yaw;
	}
    
    public void setYaw(float yaw) {
		this.yaw = yaw;
	}

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public double getMotionY() {
        return motionY;
    }
}
