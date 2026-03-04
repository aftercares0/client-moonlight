package com.moonlight.client.module.impl.combat;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.util.TextFormatting;

import com.moonlight.client.module.impl.combat.criticals.*;

@ModuleInfo(name = "Criticals", description = "crit while on ground", category = Category.COMBAT)
public class Criticals extends Module {
	
	private ModeValue mode = new ModeValue("Mode", this, new JumpCriticals(this, "Jump"), new MiniJumpCriticals(this, "MiniJump"), new NoGroundCriticals(this, "NoGround"),
			new NCPCriticals(this, "NCP"), new VisualsCriticals(this, "Visuals"));

	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + mode.getMode().getName());
	}
	
}
