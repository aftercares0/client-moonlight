package com.moonlight.client.module.impl.combat.criticals;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PostAttackEvent;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C03PacketPlayer;

public class NoGroundCriticals extends Mode {

	private boolean spoof;
	
	public NoGroundCriticals(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		spoof = false;
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {				
		spoof = true;
	}
	
	@EventLink
	public void onPostAttack(PostAttackEvent event) {
		spoof = false;
		
		mc.thePlayer.onCriticalHit(event.getEntity());
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(spoof) {
			event.setOnGround(false);
			event.setY(event.getY() + 0.12f);
		}
	}

}
