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

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItem.Action;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.network.play.server.S41PacketServerDifficulty;

public class MatrixAntiBot extends Mode {

	private boolean wasAdded;
	private int lastHitTicks;
	
    public MatrixAntiBot(Module parent, String name) {
        super(parent, name);
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	lastHitTicks++;
    }

    @EventLink
    public void onPacketReceive(PacketReceiveEvent event) {
    	if(event.getPacket() instanceof S41PacketServerDifficulty) {
    		wasAdded = false; 
    	}
    	if(event.getPacket() instanceof S38PacketPlayerListItem) {
    		S38PacketPlayerListItem packet = (S38PacketPlayerListItem) event.getPacket();
    		if (((S38PacketPlayerListItem) event.getPacket()).getAction() == Action.ADD_PLAYER) {
    			//I mean normally when a player join a world, they should be not set
                String name = packet.getEntries().get(0).getProfile().getName();
                if (!wasAdded) wasAdded = name == mc.thePlayer.getName();
                else if (!mc.thePlayer.isSpectator() && !mc.thePlayer.capabilities.allowFlying && packet.getEntries().get(0).getGameMode() != GameType.NOT_SET) {
                	event.setCancelled(true);
                }
            }
    	}
    	
    	if (event.getPacket() instanceof S0CPacketSpawnPlayer) {
            if(lastHitTicks < 8) {
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
