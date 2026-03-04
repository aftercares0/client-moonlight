package com.moonlight.client.module.impl.movement.flight.grim.impl;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S27PacketExplosion;

public class GrimTNTFly extends Mode {
	
	private boolean canFlag;	
	
	public GrimTNTFly(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		canFlag = false;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(!canFlag) return;
		
		event.setX(event.getX() + 1000);
		event.setZ(event.getZ() + 1000);
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {

	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if (event.getPacket() instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus packet = (S19PacketEntityStatus)event.getPacket();
            if (packet.getEntity(mc.theWorld) != mc.thePlayer || packet.getOpCode() != 2)
                return;

            canFlag = true;
        }

        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
        	canFlag = true;
        }
        
        if (event.getPacket() instanceof S27PacketExplosion) {
        	canFlag = true;
        }
	}

}
