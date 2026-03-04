package com.moonlight.client.module.impl.combat.velocity;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class SimpleVelocity extends Mode {

	private NumberValue<Float> h = new NumberValue("Horizontally", 0.2f, 0, 1f);
    private NumberValue<Float> v = new NumberValue("Vertically", 0.2f, 0, 1f);
	
    private boolean velocity;
    
	public SimpleVelocity(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(mc.theWorld == null || mc.thePlayer == null) return;
        
        if(event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

            if(packet.getEntityID() == mc.thePlayer.getEntityId()) {
            	velocity = true;
            }
        }
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(velocity) {
			mc.thePlayer.motionX *= h.getValue();
			mc.thePlayer.motionY *= v.getValue();
			mc.thePlayer.motionZ *= h.getValue();
			
			velocity = false;
		}
	}

}
