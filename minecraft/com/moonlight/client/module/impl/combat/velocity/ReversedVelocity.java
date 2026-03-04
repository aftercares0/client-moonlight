package com.moonlight.client.module.impl.combat.velocity;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class ReversedVelocity extends Mode {
	
	public ReversedVelocity(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(mc.theWorld == null || mc.thePlayer == null) return;
        
        if(event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

            if(packet.getEntityID() == mc.thePlayer.getEntityId()) {
                packet.motionX = -packet.motionX;
                packet.motionZ = -packet.motionZ;
            }
        }
	}

}
