package com.moonlight.client.module.impl.combat.criticals;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C03PacketPlayer;

public class VisualsCriticals extends Mode {
	
	public VisualsCriticals(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onEnabled() {
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {		
		mc.thePlayer.onCriticalHit(event.getEntity());
	}

}
