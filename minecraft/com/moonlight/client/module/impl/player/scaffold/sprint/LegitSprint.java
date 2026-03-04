package com.moonlight.client.module.impl.player.scaffold.sprint;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.value.Mode;

import net.minecraft.util.MathHelper;

public class LegitSprint extends Mode {

	public LegitSprint(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if (Math.abs(MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) - MathHelper.wrapAngleTo180_float(
				mc.thePlayer.rotationYawHead)) > 90) {
            mc.gameSettings.keyBindInventory.setPressed(false);
            mc.thePlayer.setSprinting(false);
        }
	}

}
