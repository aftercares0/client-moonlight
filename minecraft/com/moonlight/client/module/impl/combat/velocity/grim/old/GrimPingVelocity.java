package com.moonlight.client.module.impl.combat.velocity.grim.old;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.impl.combat.Velocity;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.moonlight.client.module.api.Module;

public class GrimPingVelocity extends Mode {
	
    private boolean canCancel;
    
    private int ticks;
    
    public GrimPingVelocity(Module parent, String name) {
        super(parent, name);
    }

    @Override
    public void onDisabled() {
    	ticks = 0;
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	if(ticks > 0) ticks--;
    }
    
    @EventLink
    public void onPacketSend(PacketSendEvent event) {
    	//me when desync start kicking in
        if(mc.thePlayer != null) {
        	if(event.getPacket() instanceof C0FPacketConfirmTransaction && ticks > 0) {
        		event.setCancelled(true);
        		ticks--;
        	}
        }
    }
    
    @EventLink
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus packet = (S19PacketEntityStatus)event.getPacket();
            if (packet.getEntity(mc.theWorld) != mc.thePlayer || packet.getOpCode() != 2)
                return;

            this.canCancel = true;
        }

        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId() && this.canCancel) {
        	event.setCancelled(true);

            this.canCancel = false;
            
            ticks = 6;
        }
    }
    
    @EventLink
    public void onUpdate(PreUpdateEvent event) {
        
    }

}
