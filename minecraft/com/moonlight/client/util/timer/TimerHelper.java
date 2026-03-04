package com.moonlight.client.util.timer;

public class TimerHelper {
	
    public long lastMS = System.currentTimeMillis();

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public long getMillis() {
        return this.lastMS;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    public boolean hasTimeElapsed(boolean reset, long time) {
        if(System.currentTimeMillis() - lastMS > time) {
            if(reset) reset();
            return true;
        }

        return false;
    }

    public boolean hasTimeElapsed(long time) {
        return hasTimeElapsed(false, time);
    }
    
    public boolean hasTimeElapsed(float time) {
        return hasTimeElapsed(false, (long) time);
    }

    public long hasTimeLeft(final long time) {
        return (time + lastMS) - System.currentTimeMillis();
    }

}