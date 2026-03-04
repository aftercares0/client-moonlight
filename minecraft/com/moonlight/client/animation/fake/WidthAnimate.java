package com.moonlight.client.animation.fake;

import com.moonlight.client.util.timer.TimerHelper;

public class WidthAnimate {

	private int time;
	
	private int tempWidth;
	private int width1, width2;
	private boolean lastValue;
	private TimerHelper timer;
	private boolean reversed;
		
	public WidthAnimate(int width1, int width2, int time) {		
		timer = new TimerHelper();
		
		this.time = time;
		
		this.tempWidth = width1;
		this.width1 = width1;
		this.width2 = width2;
		
		reversed = true;
		lastValue = false;
	}
	
	public void setWidth1(int width1) {
		this.width1 = width1;
	}
	
	public void setWidth2(int width2) {
		this.width2 = width2;
	}
	
	public void updateLastValue(boolean value) {
		lastValue = value;
		
		reversed = !lastValue;
	}
		
	public int getWidth() {
		return tempWidth;
	}
	
	public void update() {
		if(!timer.hasTimeElapsed(time) && time != 0) return;
		
		if(reversed) {
			if(tempWidth < width1) {
				tempWidth += 1;
			}
			
			if(tempWidth > width1) {
				tempWidth -= 1;
			}
		}else {
			if(tempWidth < width2) {
				tempWidth += 1;
			}
			
			if(tempWidth > width2) {
				tempWidth -= 1;
			}
		}
		
		timer.reset();
	}
	
}
