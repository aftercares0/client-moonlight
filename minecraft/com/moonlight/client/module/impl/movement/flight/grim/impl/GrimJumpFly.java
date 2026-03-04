package com.moonlight.client.module.impl.movement.flight.grim.impl;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.strafe.StrafeEvent;
import com.moonlight.client.event.impl.update.PostMotionEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

public class GrimJumpFly extends Mode {
		
	public GrimJumpFly(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		mc.timer.timerSpeed = 1f;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(mc.thePlayer.offGroundTicks > 2) {
			event.setX(event.getX() + 1000);
			event.setY(event.getY() + 1000);
			event.setZ(event.getZ() + 1000);
		}
		
		if(mc.thePlayer.fallDistance > 2) {
			getParent().setEnabled(false);
		}
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
		event.setJump(true);
	}
	
	@EventLink
	public void onStrafe(StrafeEvent event) {
		
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(event.getPacket() instanceof S08PacketPlayerPosLook) {
//			ChatUtil.sendClientMessage(mc.thePlayer.fallDistance);
		}
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		
	}

}
