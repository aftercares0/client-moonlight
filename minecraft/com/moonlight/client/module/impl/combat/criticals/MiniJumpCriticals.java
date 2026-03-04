package com.moonlight.client.module.impl.combat.criticals;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C03PacketPlayer;

public class MiniJumpCriticals extends Mode {
	
	public MiniJumpCriticals(Module parent, String name) {
		super(parent, name);
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {
		if(mc.thePlayer.onGround) {
			mc.thePlayer.motionY = 0.08f;
		}
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
	}

}
