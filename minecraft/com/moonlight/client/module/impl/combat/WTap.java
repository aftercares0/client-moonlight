package com.moonlight.client.module.impl.combat;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.TextFormatting;

import com.moonlight.client.module.impl.combat.morekb.*;

@ModuleInfo(name = "WTap", description = "uh wtap?", category = Category.COMBAT)
public class WTap extends Module {
	
	private int shouldReset, ticks;
	
	@Override
	public void onEnabled() {
		shouldReset = 0;
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {
		if(ticks > 12) {
			shouldReset = 2;
			ticks = 0;
		}
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		ticks++;
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
		if(!GameSettings.isKeyDown(mc.gameSettings.keyBindLeft) && shouldReset != 0) {
			shouldReset = 0;
			return; 
		}
		if(shouldReset == 2) {
			event.forward = 0;
			shouldReset = 1;
		}else if(shouldReset == 1) {
			event.forward = 1;
			shouldReset = 0;
		}
	}
	
}
