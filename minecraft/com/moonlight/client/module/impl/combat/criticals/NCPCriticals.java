package com.moonlight.client.module.impl.combat.criticals;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C03PacketPlayer;

public class NCPCriticals extends Mode {

	private int ticks;
	
	public NCPCriticals(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onEnabled() {
		ticks = 0;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		ticks++;
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {				
		if(ticks > 6) {
			if(mc.thePlayer.onGround) {
				PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY + 0.00001, mc.thePlayer.posZ, false));
				PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY + 0.000008, mc.thePlayer.posZ, false));
				PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY  + 0.0000002, mc.thePlayer.posZ, false));
			}
			ticks = 0;
			
			mc.thePlayer.onCriticalHit(event.getEntity());
		}
	}
	
	

}
