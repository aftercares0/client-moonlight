package com.moonlight.client.module.impl.movement.flight;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.util.AxisAlignedBB;

public class NCPFlight extends Mode {
	
	public NCPFlight(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		MoveUtil.stop();
		mc.timer.timerSpeed = 1.0F;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		final AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, 1, 0);
        if(mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
            if(mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                MoveUtil.strafe((float) (9 + (Math.random())));
            }else {
                MoveUtil.strafe(MoveUtil.getSpeed() * 1.026F);
            }
        }


        mc.timer.timerSpeed = 0.5f;
	}

}
