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

public class VulcanFlight extends Mode {
		
	private ArrayList<Packet> packets = new ArrayList<>();
	
	public VulcanFlight(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		if(mc.thePlayer == null) return;
		if(packets == null) return;
		packets.forEach(PacketUtil::sendNoEvent);
		packets.clear();
		
		MoveUtil.stop();
	}
	
	@Override
	public void onEnabled() {
		PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
				mc.thePlayer.posY - 2, mc.thePlayer.posZ, false));
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {				
		mc.thePlayer.motionY = 0;
		
		if(mc.thePlayer.getDistance(mc.thePlayer.lastReportedPosX, mc.thePlayer.lastReportedPosY, mc.thePlayer.lastReportedPosZ) < 9.7f) {
			event.setCancelled(true);
		}
		
		if(MoveUtil.isMoving()) {
			MoveUtil.strafe(0.2f);
		}else {
			MoveUtil.stop();
		}
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
        
	}

}
