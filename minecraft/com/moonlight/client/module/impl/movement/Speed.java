package com.moonlight.client.module.impl.movement;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.movement.speed.*;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.util.TextFormatting;

@ModuleInfo(name = "Speed", description = "zooming go bruhhhhhh", category = Category.MOVEMENT)
public class Speed extends Module {

	private ModeValue modes = new ModeValue("Mode", this, new VanillaSpeed(this, "Vanilla") , new LegitSpeed(this, "Legit"), new GrimSpeed(this, "Grim"), new VerusSpeed(this, "Verus"),
			new HypixelSpeed(this, "Watchdog"), new NCPSpeed(this, "NCP"), new RedeLowSpeed(this, "Redesky Low"));
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + modes.getMode().getName());
	}
	
}
