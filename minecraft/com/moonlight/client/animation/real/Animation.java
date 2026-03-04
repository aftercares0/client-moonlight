package com.moonlight.client.animation.real;

public class Animation {

    private float value, min, max, speed, time;
    private boolean reversed;
    private Easing ease;
    
    private int animationTime;

    public Animation() {
        this.ease = Easing.LINEAR;
        this.value = 0;
        this.min = 0;
        this.max = 1;
        this.speed = 50;
        this.reversed = false;
    }

    public void reset() {
        time = min;
    }

    public Animation update() {
        if (reversed) {
            if (time > min) time -= (animationTime * .001F * speed);
        } else {
            if (time < max) time += (animationTime * .001F * speed);
        }
        time = clamp(time, min, max);
        float easeVal = getEase().ease(time, min, max, max);
        this.value = Math.min(easeVal, max);
        return this;
    }

    private float clamp(float num, float min, float max) {
        return num < min ? min : (Math.min(num, max));
    }

    public boolean hasFinished() {
        if (reversed) {
            return value == getMin();
        } else {
            return value == getMax();
        }
    }

    public int getValueI() {
        return (int) value;
    }

    public float getValueF() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public Easing getEase() {
        return ease;
    }

    public void setEase(Easing ease) {
        this.ease = ease;
    }
}