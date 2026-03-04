package com.moonlight.client.module.impl.movement.flight;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

public class GroundCollisionFlight extends Mode {

	private NumberValue<Integer> speed = new NumberValue("Speed", 1, 1, 10);
	
	public GroundCollisionFlight(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		if(mc.thePlayer == null) return;
		MoveUtil.stop();
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		int speed = this.speed.getValue();
				
		mc.thePlayer.motionY = 0 + (mc.gameSettings.keyBindSneak.isKeyDown() ? speed : (mc.gameSettings.keyBindSprint.isKeyDown()
				 ? -speed : 0));
		
		if(MoveUtil.isMoving()) {
			MoveUtil.strafe(speed);
		}else {
			MoveUtil.stop();
		}
		
		event.setOnGround(true);
	}

}
