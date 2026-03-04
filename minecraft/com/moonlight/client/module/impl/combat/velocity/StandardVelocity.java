package com.moonlight.client.module.impl.combat.velocity;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class StandardVelocity extends Mode {

	private NumberValue<Integer> h = new NumberValue("Horizontally", 0, 0, 100);
    private NumberValue<Integer> v = new NumberValue("Vertically", 0, 0, 100);
	
	public StandardVelocity(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(mc.theWorld == null || mc.thePlayer == null) return;
        
        if(event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

            if(packet.getEntityID() == mc.thePlayer.getEntityId()) {
                if(h.getValue() == 0 && v.getValue() == 0) event.setCancelled(true);

                //Preventing player movement getting slowed down.
                if(v.getValue() != 0 && h.getValue() == 0) {
                    event.setCancelled(true);
                    mc.thePlayer.motionY = (packet.motionY / 8000.0D) * (v.getValue() / 100.0);
                }
                
                if(h.getValue() != 0 && v.getValue() == 0) {
                    event.setCancelled(true);
                    mc.thePlayer.motionX = (packet.motionX / 8000.0D) * (h.getValue() / 100.0);
                    mc.thePlayer.motionZ = (packet.motionZ / 8000.0D) * (h.getValue() / 100.0);   
                }

                //Basic math, if you can't understand shit, you are fking dumb
                packet.motionX = (int) (packet.motionX * (h.getValue() / 100.0));
                packet.motionZ = (int) (packet.motionZ * (h.getValue() / 100.0));
                packet.motionY = (int) (packet.motionY * (v.getValue() / 100.0));
            }
        }
	}

}
