package com.moonlight.client.module.impl.movement.flight;

import java.util.ArrayList;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S27PacketExplosion;

public class LuckyVNFlight extends Mode {
		
	private ArrayList<Packet> packets = new ArrayList<>();
	
	public LuckyVNFlight(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		mc.thePlayer.noClip = false;
		mc.timer.timerSpeed = 1f;
		mc.thePlayer.speedInAir = 0.02f;
		MoveUtil.stop();
		
		if(mc.thePlayer.onGround) mc.thePlayer.jump();
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {		
		mc.thePlayer.speedInAir = 5;
		mc.timer.timerSpeed = 0.2f;
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
		
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
	}
	

}
