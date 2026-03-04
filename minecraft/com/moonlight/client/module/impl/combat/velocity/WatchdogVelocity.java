package com.moonlight.client.module.impl.combat.velocity;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.WorldChangeEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class WatchdogVelocity extends Mode {

	private double motionY;
	
	public WatchdogVelocity(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onEnabled() {
		motionY = 0;
	}
	
	@EventLink
	public void onChangeWorld(WorldChangeEvent event) {
		motionY = 0;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(motionY != 0) {
			event.setY(event.getY() + motionY);
			event.setOnGround(false);
					
			motionY = 0;
		}
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(mc.theWorld == null || mc.thePlayer == null) return;
        
        if(event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

            if(packet.getEntityID() == mc.thePlayer.getEntityId()) {
               event.setCancelled(true);
               
               motionY = packet.motionY / 8000.0D;
            }
        }
	}

}
