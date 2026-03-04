package com.moonlight.client.module.impl.others.autojoin;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.util.IChatComponent;

public class HypixelAutoJoin extends Mode {

	public HypixelAutoJoin(Module parent, String name) {
		super(parent, name);
	}

	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(event.getPacket() instanceof S02PacketChat) {
			S02PacketChat packet = (S02PacketChat) event.getPacket();
			
			if (packet.getChatComponent().getFormattedText().contains("play again?")) {
				for (IChatComponent iChatComponent : packet.getChatComponent().getSiblings()) {
					for (String s : iChatComponent.toString().split("'")) {
						 if (s.startsWith("/play") && !s.contains(".")) {
							 mc.thePlayer.sendChatMessage(s);
							 break;
						 }
					}
				}
			}
		}
	}
	
}
