package com.moonlight.client.event.impl.world.packet;

import com.moonlight.client.event.CancellableEvent;

import net.minecraft.network.Packet;

public class PacketReceiveEvent extends CancellableEvent {

	private final Packet<?> packet;
	
	public PacketReceiveEvent(Packet<?> packet) {
		this.packet = packet;
	}
	
	public Packet getPacket() {
		return packet;
	}
	
}
