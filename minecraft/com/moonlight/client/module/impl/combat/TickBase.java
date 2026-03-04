package com.moonlight.client.module.impl.combat;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.RandomUtils;

import com.mojang.authlib.GameProfile;
import com.moonlight.client.Client;
import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.WorldChangeEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.util.BlockPos;

import com.moonlight.client.module.impl.combat.morekb.*;
import com.moonlight.client.util.client.ChatUtil;

@ModuleInfo(name = "TickBase", description = "dunnu how to explain", category = Category.COMBAT)
public class TickBase extends Module {
		
	private long lastMs;
	private int balance, ticks;
	
	private boolean teleport;
	
	@Override
	public void onEnabled() {
		lastMs = System.currentTimeMillis();
	}
	
	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0F;
		teleport = false;
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		if(event.getPacket() instanceof C03PacketPlayer) {
			if(lastMs == 0) {
				lastMs = System.currentTimeMillis();
			}
						
			balance = (int) (System.currentTimeMillis() - lastMs);
			
			if(!event.isCancelled()) {
				lastMs = System.currentTimeMillis();
			}
		}
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {					
		Aura aura = Client.INSTANCE.moduleManager.get(Aura.class);
		
		if(!aura.isEnabled() || aura.target == null) {
			mc.timer.timerSpeed = 1.0f;
			return; 
		}
		
		ticks++;
		
		if(aura.target.getDistanceToEntity(mc.thePlayer) < 4.3f && ticks >= 50 * mc.timer.timerSpeed) {
			if(!teleport) {
				teleport = true;
			}
			
			ticks = 0;
		}
		
		if(teleport) {
			mc.timer.timerSpeed = 0.3f;
			
			if(balance > 300) {
				mc.timer.timerSpeed = balance / 49;
				
//				if(balance >= 47 && balance <= 50) {
//					teleport = false;
//				}
			}
		}
	}
	
}
