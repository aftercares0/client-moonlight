package com.moonlight.client.module.impl.combat.criticals;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C03PacketPlayer;

public class JumpCriticals extends Mode {

	private boolean jump;
	
	public JumpCriticals(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {
		jump = true;
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
		if(jump) {
			event.setJump(true);
			jump = false;
		}
	}

}
