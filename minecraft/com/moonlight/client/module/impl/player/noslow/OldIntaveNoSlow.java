package com.moonlight.client.module.impl.player.noslow;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.SlowDownEvent;
import com.moonlight.client.event.impl.update.PostMotionEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.*;

public class OldIntaveNoSlow extends Mode {

	public OldIntaveNoSlow(Module parent, String name) {
		super(parent, name);
	}
    
    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	if(mc.thePlayer.isBlocking()){
    		PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
    		PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        }
    }
    
    @EventLink
    public void onPostMotion(PostMotionEvent event) {
    	if(mc.thePlayer.isBlocking()){
    		PacketUtil.send(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventoryContainer.getSlot(mc.thePlayer.inventory.currentItem + 36).getStack()));
        }
    }
    
    @EventLink
    public void onSlowDown(SlowDownEvent event) {
        event.setCancelled(true);
    }
}
