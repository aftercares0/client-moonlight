package com.moonlight.client.module.impl.movement.speed;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;

public class LegitSpeed extends Mode {

	public LegitSpeed(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
		if(MoveUtil.isMoving()) {
			event.setJump(true);
		}
	}
}
