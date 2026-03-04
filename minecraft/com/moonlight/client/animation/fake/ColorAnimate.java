package com.moonlight.client.animation.fake;

import java.awt.Color;

import com.moonlight.client.util.timer.TimerHelper;

public class ColorAnimate {

	private int time;
	
	private Color temp;
	private Color color1,color2;
	private boolean lastValue;
	private TimerHelper timer;
	private boolean reversed;
		
	public ColorAnimate(Color color1, Color color2, int time) {		
		timer = new TimerHelper();
		
		this.time = time;
		
		this.color1 = color1;
		this.color2 = color2;
		
		this.temp = color1;
		
		reversed = false;
		lastValue = false;
	}
	
	public void setColor2(Color color2) {
		this.color2 = color2;
	}
	
	public void setColor1(Color color1) {
		this.color1 = color1;
	}
	
	public boolean animationInProgress() {
		return temp.getRGB() != color1.getRGB() && temp.getRGB() != color2.getRGB();
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
			if(temp.getRed() < color1.getRed()) {
				temp = new Color(temp.getRed() + 1, temp.getGreen(), temp.getBlue(), temp.getAlpha());
			}
			if(temp.getRed() > color1.getRed()) {
				temp = new Color(temp.getRed() - 1, temp.getGreen(), temp.getBlue(), temp.getAlpha());
			}
			if(temp.getGreen() < color1.getGreen()) {
				temp = new Color(temp.getRed(), temp.getGreen() + 1, temp.getBlue(), temp.getAlpha());
			}
			if(temp.getGreen() > color1.getGreen()) {
				temp = new Color(temp.getRed(), temp.getGreen() - 1, temp.getBlue(), temp.getAlpha());
			}
			if(temp.getBlue() < color1.getBlue()) {
				temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue() + 1, temp.getAlpha());
			}
			if(temp.getBlue() > color1.getBlue()) {
				temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue() - 1, temp.getAlpha());
			}
			if(temp.getAlpha() < color1.getAlpha()) {
				temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha() + 1);
			}
			if(temp.getAlpha() > color1.getAlpha()) {
				temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha() - 1);
			}
		}else {
			if(temp.getRed() < color2.getRed()) {
				temp = new Color(temp.getRed() + 1, temp.getGreen(), temp.getBlue(), temp.getAlpha());
			}
			if(temp.getRed() > color2.getRed()) {
				temp = new Color(temp.getRed() - 1, temp.getGreen(), temp.getBlue(), temp.getAlpha());
			}
			if(temp.getGreen() < color2.getGreen()) {
				temp = new Color(temp.getRed(), temp.getGreen() + 1, temp.getBlue(), temp.getAlpha());
			}
			if(temp.getGreen() > color2.getGreen()) {
				temp = new Color(temp.getRed(), temp.getGreen() - 1, temp.getBlue(), temp.getAlpha());
			}
			if(temp.getBlue() < color2.getBlue()) {
				temp = new Color(temp.getRed(), temp.getBlue(), temp.getBlue() + 1, temp.getAlpha());
			}
			if(temp.getBlue() > color2.getBlue()) {
				temp = new Color(temp.getRed(), temp.getBlue(), temp.getBlue() - 1, temp.getAlpha());
			}
			if(temp.getAlpha() < color2.getAlpha()) {
				temp = new Color(temp.getRed(), temp.getBlue(), temp.getBlue(), temp.getAlpha() + 1);
			}
			if(temp.getAlpha() > color2.getAlpha()) {
				temp = new Color(temp.getRed(), temp.getBlue(), temp.getBlue(), temp.getAlpha() - 1);
			}
		}
		
		timer.reset();
	}
	
}
