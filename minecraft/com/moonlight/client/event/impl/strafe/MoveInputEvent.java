package com.moonlight.client.event.impl.strafe;

import com.moonlight.client.event.Event;

public class MoveInputEvent extends Event {

    private boolean jump, sneak;

    public MoveInputEvent(float strafe, float forward, boolean jump, boolean sneak) {
        this.strafe = strafe;
        this.forward = forward;
        this.sneak = sneak;
        this.jump = jump;
    }

    public float strafe;
    public float forward;

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setSneak(boolean sneak) {
        this.sneak = sneak;
    }

    public boolean isJump() {
        return jump;
    }

    public boolean isSneak() {
        return sneak;
    }
}
