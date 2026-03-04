package com.moonlight.client.module.impl.movement.speed;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.BooleanValue;

import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class VerusSpeed extends Mode {

	private BooleanValue damageBoost = new BooleanValue("Damage Boost", false);

	private boolean canBoost = false;
	
	public VerusSpeed(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onEnabled() {
		canBoost = false;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(!MoveUtil.isMoving()) return;
		
		if(mc.thePlayer.onGround) mc.thePlayer.jump();

		if (mc.thePlayer.onGround) {
			mc.thePlayer.jump();
			MoveUtil.strafe(0.5F);
		} else {
			MoveUtil.strafe(0.25F);
		}

		if (damageBoost.getState() && canBoost) {
			MoveUtil.strafe(2);
			canBoost = false;
		}
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		//If player crouch, that will be extremly stupid so no
		if(event.getPacket() instanceof net.minecraft.network.play.client.C0BPacketEntityAction) {
			event.setCancelled(true);
		}
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(event.getPacket() instanceof S12PacketEntityVelocity) {
			canBoost = true;
		}
	}

}
