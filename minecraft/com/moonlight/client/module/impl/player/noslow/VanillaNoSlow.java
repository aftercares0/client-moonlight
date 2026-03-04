package com.moonlight.client.module.impl.player.noslow;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.SlowDownEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.value.Mode;

public class VanillaNoSlow extends Mode {

	public VanillaNoSlow(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onSlowDown(SlowDownEvent event) {
		event.setCancelled(true);
		
        mc.thePlayer.setSprinting(true);
	}

}
