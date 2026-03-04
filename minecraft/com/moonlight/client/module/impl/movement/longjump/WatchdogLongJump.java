package com.moonlight.client.module.impl.movement.longjump;

import java.util.ArrayList;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;

public class WatchdogLongJump extends Mode {
	
	private boolean lastBoost;
	
	public WatchdogLongJump(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		lastBoost = false;
	}
	
	@Override
	public void onEnabled() {
		
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {	
		mc.thePlayer.setSprinting(true);
		if(mc.thePlayer.onGround && !lastBoost && MoveUtil.isMoving()) {
			mc.thePlayer.jump();
			MoveUtil.strafe((float) Math.toRadians(mc.thePlayer.rotationYaw) , 0.408f);
		}else {
			if(!lastBoost) lastBoost = true;
		}
		
		if(mc.thePlayer.onGround && lastBoost) {
			getParent().setEnabled(false);
		}
	}
	
	@EventLink
	public void onMotionInput(MoveInputEvent event) {
		event.forward = 1f;
	}

}
