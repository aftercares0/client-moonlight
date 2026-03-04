package com.moonlight.client.module.impl.movement.speed;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

public class RedeLowSpeed extends Mode {
	
	public RedeLowSpeed(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		mc.timer.timerSpeed = 1.0F;
	}
	
	@EventLink
	public void onUpdate(PreUpdateEvent event) {
	if(mc.thePlayer.onGround) {
		if(MoveUtil.isMoving()) {
			mc.thePlayer.jump();
			if(!mc.gameSettings.keyBindRight.pressed) {
				MoveUtil.strafe(0.694F);
			} else if(mc.gameSettings.keyBindBack.pressed || mc.gameSettings.keyBindBack.pressed) {
				MoveUtil.strafe(0.240F);
			}
			mc.thePlayer.motionY *= 0.7841F;
			mc.thePlayer.moveStrafing *= 2f;
			} else {
				mc.thePlayer.jumpMovementFactor = 0.0265F;
			}
		}
	}

}
