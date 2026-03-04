package com.moonlight.client.animation.fake;

import com.moonlight.client.util.timer.TimerHelper;

public class AlphaAnimate {

	private int time;
	
	private int alpha, max;
	private boolean lastValue;
	private TimerHelper timer;
	private boolean reversed;
	
	public AlphaAnimate(int alpha, int max, int time) {		
		timer = new TimerHelper();
		this.alpha = alpha;
		this.max = max;
		this.time = time;
		
		reversed = false;
		lastValue = false;
	}
	
	public boolean animationInProgress() {
		return reversed ? alpha != max : alpha != 0;
	}
	
	public void updateLastValue(boolean value) {
		lastValue = value;
		
		reversed = !lastValue;
	}
		
	public int getFade() {
		return alpha;
	}
		
	public void update() {
		if(!timer.hasTimeElapsed(time) && time != 0) return;
			
		if(reversed) {
			if(alpha < max) {
				alpha += 1;
			}
			if(alpha > max) {
				alpha -= 1;
			}
		}else {
			if(alpha > 0) {
				alpha -= 1;
			}
		}
		
		timer.reset();
	}
	
}
