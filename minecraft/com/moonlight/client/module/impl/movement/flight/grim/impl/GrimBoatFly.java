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

public class GrimBoatFly extends Mode {
		
	private int flags;
	
	public GrimBoatFly(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		mc.timer.timerSpeed = 1f;
		flags = 0;
	}
	
	@EventLink
	public void onPostMotion(PostMotionEvent event) {
		System.out.println("Xd");
		mc.thePlayer.motionY = 0.7f;
		MoveUtil.strafe((float) Math.toRadians(mc.thePlayer.rotationYaw), 1f);
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
	}
	
	@EventLink
	public void onStrafe(StrafeEvent event) {
		event.setCancelled(true);
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(event.getPacket() instanceof S08PacketPlayerPosLook) {
			flags++;
			
			if(flags > 2) {
				getParent().setEnabled(false);
			}
		}
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		
	}

}
