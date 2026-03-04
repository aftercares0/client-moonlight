package com.moonlight.client.module.impl.movement.flight;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.StrafeEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C03PacketPlayer;

import javax.vecmath.Vector3d;

public class StuckFlight extends Mode {

	private Vector3d motionXYZ;
	
	public StuckFlight(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onEnabled() {
		if(mc.thePlayer == null) return;

		motionXYZ = new Vector3d(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
	}
	
	@EventLink
	public void onStrafe(StrafeEvent event) {
		MoveUtil.stop();
		mc.thePlayer.motionY = 0;

		event.setCancelled(true);
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		if(event.getPacket() instanceof C03PacketPlayer) {
			event.setCancelled(true);
//			ChatUtil.sendClientMessage("Cancelled!");
		}
	}
	
	@Override
	public void onDisabled() {
		if(mc.thePlayer == null) return;
		if(motionXYZ == null) return;
		
		mc.thePlayer.motionX = motionXYZ.x;
		mc.thePlayer.motionY = motionXYZ.y;
		mc.thePlayer.motionZ = motionXYZ.z;
	}

}
