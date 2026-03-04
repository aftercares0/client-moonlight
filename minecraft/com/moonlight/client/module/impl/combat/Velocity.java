package com.moonlight.client.module.impl.combat;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.util.TextFormatting;

import com.moonlight.client.module.impl.combat.criticals.NCPCriticals;
import com.moonlight.client.module.impl.combat.velocity.*;
import com.moonlight.client.module.impl.combat.velocity.grim.*;
import com.moonlight.client.module.impl.combat.velocity.grim.old.GrimPingVelocity;
import com.moonlight.client.module.impl.combat.velocity.grim.old.GrimDiggingVelocity;
import com.moonlight.client.module.impl.combat.velocity.polar.PolarVelocity;

@ModuleInfo(name = "Velocity", description = "prevent player taking fucking kb", category = Category.COMBAT)
public class Velocity extends Module {
	
	private ModeValue mode = new ModeValue("Mode", this, new StandardVelocity(this, "Standard"), new ReversedVelocity(this, "Reversed"), new SimpleVelocity(this, "Simple"), new GrimDiggingVelocity(this, "Grim"),
			new OldGrimVelocity(this, "OldGrim"), new DelayGrimVelocity(this, "DelayGrim"), new LatestGrimVelocity(this, "Latest Grim"),
			new WatchdogVelocity(this, "Watchdog"), new IntaveVelocity(this, "Intave") ,
			new BlinkVelocity(this, "Lag"), new LegitVelocity(this, "Legit"), new PolarVelocity(this, "Polar"));

	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + mode.getMode().getName());
	}
	
}
