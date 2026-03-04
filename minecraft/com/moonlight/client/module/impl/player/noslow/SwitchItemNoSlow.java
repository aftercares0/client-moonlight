package com.moonlight.client.module.impl.player.noslow;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.SlowDownEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.item.ItemSword;

import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class SwitchItemNoSlow extends Mode {

	public SwitchItemNoSlow(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onSlowDown(SlowDownEvent event) {
    	if(!mc.thePlayer.isBlocking() || mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
		
		event.setCancelled(true);
	}
	
	@EventLink
    public void onPreMotion(PreMotionEvent event) {
    	if(!mc.thePlayer.isBlocking() || mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
    	
        if(mc.thePlayer.ticksExisted % 2 == 0) {
        	PacketUtil.sendNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
            PacketUtil.sendNoEvent(new C09PacketHeldItemChange( mc.thePlayer.inventory.currentItem));
        }
    }

}
