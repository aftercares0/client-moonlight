package com.moonlight.client.module.impl.others;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus.Action;
import net.minecraft.network.play.server.S48PacketResourcePackSend;

@ModuleInfo(name = "AntiResources", description = "prevent server from force you install texture pack", category = Category.OTHERS)
public class AntiResources extends Module {
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		if(event.getPacket() instanceof C19PacketResourcePackStatus) {
			C19PacketResourcePackStatus packet = (C19PacketResourcePackStatus) event.getPacket();
            
			if(packet.status == Action.DECLINED) {
				packet.status = Action.ACCEPTED;
			}else if(packet.status == Action.FAILED_DOWNLOAD) {
				packet.status = Action.SUCCESSFULLY_LOADED;
			}
            
            event.setPacket(packet);
		}
	} 
	
}
