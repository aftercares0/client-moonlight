package com.moonlight.client.module.impl.movement.speed;

import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.component.impl.rotation.MovementCorrection;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PostAttackEvent;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.strafe.StrafeEvent;
import com.moonlight.client.event.impl.update.PostMotionEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.rotation.RotationUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.network.play.client.C03PacketPlayer;

public class GrimSpeed extends Mode {

	private ModeValue mode = new ModeValue("Mode", getParent(), "Normal", "1.17");
	private NumberValue<Integer> ticks = new NumberValue("Ticks", 3, 1, 5);
		
	private boolean stop;
	
	public GrimSpeed(Module parent, String name) {
		super(parent, name);
	}
	
	@Override
	public void onDisabled() {
		stop = false;
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
		if(mc.thePlayer.ticksExisted % ticks.getValue() != 0) return;
		
		if(mode.getMode().getName().equalsIgnoreCase("Normal")) {
			if(event.forward != 0 || event.strafe != 0 && !stop) {
				mc.thePlayer.setSprinting(true);
				
				Vector2f rotation = new Vector2f(MoveUtil.directionYaw(
						mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.realForward , mc.thePlayer.movementInput.realStrafe), mc.thePlayer.rotationPitchHead);
				
				RotationComponent.setRotations(rotation, 360, MovementCorrection.STRAFE);
				
				event.forward = 1;
				event.strafe = 0;
				
				event.setJump(true);
			}
		}else {
			if(event.forward != 0 || event.strafe != 0 && !stop) {
				mc.thePlayer.setSprinting(true);
				
				Vector2f rotation = new Vector2f(MoveUtil.directionYaw(
						mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.realForward , mc.thePlayer.movementInput.realStrafe), mc.thePlayer.rotationPitchHead);
				
				RotationComponent.setRotations(rotation, 360, false,  MovementCorrection.STRAFE);
				
				event.forward = 1;
				event.strafe = 0;
				
				event.setJump(true);
			}
		}
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		
	}
	
	@EventLink
	public void onStrafe(StrafeEvent event) {
		
	}
	
	@EventLink
	public void onPostMotion(PostMotionEvent event) {
		if(mode.getMode().getName().equalsIgnoreCase("1.17")) {
			//yaw = mc.thePlayer.rotationYawHead;
		}
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {
		if(mode.getMode().getName().equalsIgnoreCase("1.17")) {
			Vector2f vec = RotationUtil.getEntityRotation(event.getEntity(), false);
			PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
    				vec.x, vec.y, mc.thePlayer.onGround));
		}
		stop = true;
	}
	
	@EventLink
	public void onPostAttack(PostAttackEvent event) {
		stop = false;
	}
	
}
