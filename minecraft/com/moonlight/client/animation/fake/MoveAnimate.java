package com.moonlight.client.animation.fake;

import com.moonlight.client.util.timer.TimerHelper;

public class MoveAnimate {

	private int time;
	
	private int tempX, tempY;
	private int x1, y1, x2, y2;
	private boolean lastValue;
	private TimerHelper timer;
	private boolean reversed;
		
	public MoveAnimate(int x1, int y1, int x2, int y2, int time) {		
		timer = new TimerHelper();
		
		this.time = time;
		
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
		this.tempX = x1;
		this.tempY = y1;
		
		reversed = true;
		lastValue = false;
	}
	
	public boolean animationInProgress() {
		boolean inProgress = false;
		
		if(reversed) {
			if(tempX != x1 || tempY != y1) {
				inProgress = true;
			}
		}else {
			if(tempX != x2 || tempY != y2) {
				inProgress = true;
			}
		}
		
		return inProgress;
	}
	
	public int getX1() {
		return x1;
	}
	
	public int getX2() {
		return x2;
	}
	
	public int getY1() {
		return y1;
	}
	
	public int getY2() {
		return y2;
	}
	
	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	public void setX2(int x2) {
		this.x2 = x2;
	}
	
	public void setY1(int y1) {
		this.y1 = y1;
	}
	
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public void updateLastValue(boolean value) {
		lastValue = value;
		
		reversed = !lastValue;
	}
		
	public int[] getLocation() {
		return new int[] {tempX, tempY};
	}
	
	public void update() {
		if(!timer.hasTimeElapsed(time) && time != 0) return;
		
		if(reversed) {
			if(tempX < x1) {
				tempX += 1;
			}else if(tempX > x1) {
				tempX -= 1;
			}

			if(tempY < y1) {
				tempY += 1;
			}else if(tempY > y1) {
				tempY -= 1;
			}
		}else {
			if(tempX < x2) {
				tempX += 1;
			}
			if(tempX > x2) {
				tempX -= 1;
			}

			if(tempY < y2) {
				tempY += 1;
			}else if(tempY > y2) {
				tempY -= 1;
			}
		}
		
		timer.reset();
	}
	
}
