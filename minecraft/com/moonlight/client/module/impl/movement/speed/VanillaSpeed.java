package com.moonlight.client.module.impl.movement.speed;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

public class VanillaSpeed extends Mode {

	private NumberValue<Integer> speed = new NumberValue("Speed", 1, 1, 10);
	
	public VanillaSpeed(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		if(mc.thePlayer == null) return;
		mc.thePlayer.speedInAir = 0.02F;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		mc.thePlayer.speedInAir = 0.02F * (speed.getValue() + 0.2F);
		
		if(!MoveUtil.isMoving()) {
			MoveUtil.stop();
		}
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
		event.setJump(true);
	}

}
