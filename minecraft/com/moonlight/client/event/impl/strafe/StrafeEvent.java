package com.moonlight.client.event.impl.strafe;

import com.moonlight.client.event.CancellableEvent;

public class StrafeEvent extends CancellableEvent {

    public StrafeEvent(float yaw, float strafe, float forward, float friction) {
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
    }

    private float yaw;
    public float strafe;
    public float forward;
    public float friction;


    public float getYaw() {
        return yaw;
    }


    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

}
