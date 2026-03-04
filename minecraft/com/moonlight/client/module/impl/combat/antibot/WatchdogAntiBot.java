package com.moonlight.client.module.impl.combat.antibot;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItem.Action;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.network.play.server.S41PacketServerDifficulty;

public class WatchdogAntiBot extends Mode {

	private int lastHitTicks;
	
    public WatchdogAntiBot(Module parent, String name) {
        super(parent, name);
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	lastHitTicks++;
    	
    	try {
    		mc.theWorld.playerEntities.forEach(p -> {
        		NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(p.getUniqueID());
                if (info == null) {
                    Client.INSTANCE.bots.add(p.getEntityId());
                } else {
                    if(Client.INSTANCE.bots.contains(p.getEntityId())) Client.INSTANCE.bots.remove(p.getEntityId());
                }
        	});
    	}catch (Exception e) {
    		
    	}
    }

    @EventLink
    public void onPacketReceive(PacketReceiveEvent event) {
    	if (event.getPacket() instanceof S0CPacketSpawnPlayer) {
            if(lastHitTicks < 10) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventLink
    public void onPacketSend(PacketSendEvent event) {
    	if(event.getPacket() instanceof C02PacketUseEntity) {
    		C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();
    		if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
    			lastHitTicks = 0;
    		}
    	}
    }

}
