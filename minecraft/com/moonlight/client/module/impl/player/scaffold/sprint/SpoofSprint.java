package com.moonlight.client.module.impl.player.scaffold.sprint;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;

public class SpoofSprint extends Mode {

	public SpoofSprint(Module parent, String name) {
		super(parent, name);
	}

	@Override
	public void onDisabled() {
	}
	
	@Override
	public void onEnabled() {
		if(mc.thePlayer.serverSprintState) {
			mc.thePlayer.serverSprintState = false;
			PacketUtil.sendNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SPRINTING));
		}
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		mc.thePlayer.setSprinting(true);
    	mc.gameSettings.keyBindInventory.setPressed(true);
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		if(event.getPacket() instanceof C0BPacketEntityAction) {
			C0BPacketEntityAction packet = (C0BPacketEntityAction) event.getPacket();
			
			if(packet.getAction() == Action.START_SPRINTING || packet.getAction() == Action.STOP_SPRINTING) {
				event.setCancelled(true);
			}
		}
	}
	
}
