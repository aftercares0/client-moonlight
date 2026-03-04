package com.moonlight.client.module.impl.movement.flight;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.impl.movement.Flight;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

public class RedeFly extends Mode {
	
	public RedeFly(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onEnabled() {
		mc.thePlayer.jump();
	}
	
	@Override
	public void onDisabled() {
		mc.timer.timerSpeed = 1.0F;
	}
	
	@EventLink
	public void onUpdate(PreUpdateEvent event) {
		mc.thePlayer.motionY = 0.0F;
		if(MoveUtil.isMoving()) {
			MoveUtil.strafe(0.8128F);
		}
		mc.timer.timerSpeed = 1.2F;
	}

}
