package com.moonlight.client.animation.fake;

import java.awt.Color;

import com.moonlight.client.util.timer.TimerHelper;

public class ColorFadeAnimate {

	private int time;
	
	private Color temp;
	private boolean lastValue;
	private TimerHelper timer;
	private boolean reversed;
	
	public ColorFadeAnimate(Color color ,int time) {		
		timer = new TimerHelper();
		this.temp = color;
		this.time = time;
		
		reversed = false;
		lastValue = false;
	}
	
	public boolean animationInProgress() {
		return reversed ? temp.getRGB() != 255 : temp.getRGB() != 0;
	}
	
	public void updateLastValue(boolean value) {
		lastValue = value;
		
		reversed = !lastValue;
	}
		
	public Color getColor() {
		return temp;
	}
		
	public void update() {
		if(!timer.hasTimeElapsed(time) && time != 0) return;
				
		if(reversed) {
			if(temp.getAlpha() < 255) {
				temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha() + 1);
			}
		}else {
			if(temp.getAlpha() > 1) {
				temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha() - 1);
			}
		}
		
		timer.reset();
	}
	
}
