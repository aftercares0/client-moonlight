package com.moonlight.client.module.impl.combat.velocity;

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
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.moonlight.client.module.api.Module;

public class BlinkVelocity extends Mode {

    private int ticks;
    private ArrayList<Packet> packets = new ArrayList<>();

    public BlinkVelocity(Module parent, String name) {
        super(parent, name);
    }

    @Override
    public void onDisabled() {
    	if(packets == null) return;
    	packets.forEach(PacketUtil::sendNoEvent);
    	packets.clear();
    }

    @EventLink
    public void onPacket(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
        	event.setCancelled(true);
        	ticks = 6;
        }
    }
    
    @EventLink
    public void onPacket(PacketSendEvent event) {
    	if (mc.thePlayer == null || mc.thePlayer.isDead || mc.isSingleplayer() || mc.thePlayer.ticksExisted < 50) {
        	packets.clear();
			return;
		}
		
    	event.setCancelled(true);
		packets.add(event.getPacket());
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        if(ticks > 0) ticks--;
        
        if(ticks <= 0 && !packets.isEmpty()) {
        	packets.forEach(PacketUtil::sendNoEvent);
        	packets.clear();
        }
    }

}
