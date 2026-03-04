package com.moonlight.client.module.impl.player.noslow;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PostAttackEvent;
import com.moonlight.client.event.impl.player.SlowDownEvent;
import com.moonlight.client.event.impl.update.PostMotionEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class WatchdogNoSlow extends Mode {
	
	public WatchdogNoSlow(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
    	if(!mc.thePlayer.isBlocking() || mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
		
		if(mc.thePlayer.isUsingItem()) {
			PacketUtil.sendNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
					BlockPos.ORIGIN, EnumFacing.DOWN));
		}
	}
	
	@EventLink
	public void onPostMotion(PostMotionEvent event) {
		if(!mc.thePlayer.isBlocking() || mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
		
		if(mc.thePlayer.isUsingItem()) {
			PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
			
			if(mc.thePlayer.ticksExisted % 2 == 0) {
	        	PacketUtil.sendNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
	            PacketUtil.sendNoEvent(new C09PacketHeldItemChange( mc.thePlayer.inventory.currentItem));
	        }
		}
	}
	
	@EventLink
	public void onSlowDown(SlowDownEvent event) {
    	if(!mc.thePlayer.isBlocking() || mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
		
    	event.setCancelled(true);
	}

}
