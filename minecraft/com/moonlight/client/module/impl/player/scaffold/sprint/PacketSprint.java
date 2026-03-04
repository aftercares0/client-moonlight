package com.moonlight.client.module.impl.player.scaffold.sprint;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PostMotionEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.BooleanValue;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.util.MathHelper;

public class PacketSprint extends Mode {
	
	public PacketSprint(Module parent, String name) {
		super(parent, name);
	}

	@Override
	public void onDisabled() {
		mc.timer.timerSpeed = 1f;
	}
	
	@Override
	public void onEnabled() {
		
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		mc.thePlayer.setSprinting(true);
		if(MoveUtil.isMoving() && mc.thePlayer.isSprinting()) {
			float yaw = (float)MoveUtil.direction();
			double speed = -0.221D;
			
			double posX = MathHelper.sin(yaw) * speed + mc.thePlayer.posX;
			double posZ = -MathHelper.cos(yaw) * speed + mc.thePlayer.posZ;
			event.setX(posX);
			event.setZ(posZ);
		}
	}
	
	@EventLink
	public void onPostMotion(PostMotionEvent event) {
		PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
	}
	
}
