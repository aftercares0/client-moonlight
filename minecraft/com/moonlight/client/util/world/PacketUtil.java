package com.moonlight.client.util.world;

import com.moonlight.client.util.MinecraftInstance;

import net.minecraft.network.Packet;

public class PacketUtil implements MinecraftInstance {

	public static void send(Packet packet) {
		mc.thePlayer.sendQueue.addToSendQueue(packet);
	}
	
	public static void sendNoEvent(Packet packet) {
		mc.thePlayer.sendQueue.getNetworkManager().sendNoEvent(packet);
	}
	
	public static void receive(Packet packet) {
		mc.thePlayer.sendQueue.getNetworkManager().receive(packet);
	}
	
	public static void receiveNoEvent(Packet packet) {
		mc.thePlayer.sendQueue.getNetworkManager().receiveNoEvent(packet);
	}
}
