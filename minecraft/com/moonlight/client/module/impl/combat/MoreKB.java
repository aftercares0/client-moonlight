package com.moonlight.client.module.impl.combat;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.util.TextFormatting;

import com.moonlight.client.module.impl.combat.morekb.*;

@ModuleInfo(name = "MoreKB", description = "more kb :>", category = Category.COMBAT)
public class MoreKB extends Module {
	
	private ModeValue mode = new ModeValue("Mode", this, new LegitMoreKB(this, "Legit"));

	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + mode.getMode().getName());
	}
	
}
