package com.moonlight.client.module.impl.movement.flight;

import java.util.ArrayList;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

public class VerusFlight extends Mode {
			
	public VerusFlight(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		if(mc.thePlayer == null) return;

		MoveUtil.stop();
	}
	
	@Override
	public void onEnabled() {
		
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {	
		if(MoveUtil.isMoving()) {
			MoveUtil.strafe(0.32f);
		}
				
		if(mc.thePlayer.ticksExisted % 5 == 0) {
			mc.thePlayer.motionY = 0.42f;
		}
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
        
	}

}
