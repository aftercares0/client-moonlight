package com.moonlight.client.module.impl.movement.speed;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

public class NCPSpeed extends Mode {
	
	public NCPSpeed(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		if(mc.thePlayer == null) return;
		mc.timer.timerSpeed = 1.0F;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
        if(MoveUtil.isMoving()) {
            if(mc.thePlayer.onGround) {
            	mc.thePlayer.jump();
                MoveUtil.strafe(0.42f);
            }else {
                MoveUtil.strafe(0.25f);
            }
            
            if(mc.thePlayer.offGroundTicks == 5) {
    			mc.thePlayer.motionY = -0.09800000190734864;
    		}
        }else {
        	MoveUtil.stop();
        }
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
	}

}
