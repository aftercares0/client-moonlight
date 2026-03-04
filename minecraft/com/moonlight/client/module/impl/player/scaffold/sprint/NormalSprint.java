package com.moonlight.client.module.impl.player.scaffold.sprint;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;

public class NormalSprint extends Mode {

	public NormalSprint(Module parent, String name) {
		super(parent, name);
	}

	@Override
	public void onDisabled() {
	}
	
	@Override
	public void onEnabled() {
		
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		mc.thePlayer.setSprinting(true);
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
	}
	
}
