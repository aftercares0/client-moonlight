package com.moonlight.client.scripting.bindings;

import com.moonlight.client.scripting.bindings.impl.MinecraftBindings;
import com.moonlight.client.util.MinecraftInstance;

public class MinecraftBindingsExtends implements MinecraftBindings {

	@Override
	public float getTimer() {
		return MinecraftInstance.mc.timer.timerSpeed;
	}

	@Override
	public void setTimer(float arg0) {
		MinecraftInstance.mc.timer.timerSpeed = arg0;
	}

}
