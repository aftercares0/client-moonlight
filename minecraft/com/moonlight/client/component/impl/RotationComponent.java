package com.moonlight.client.component.impl;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.component.Component;
import com.moonlight.client.component.impl.rotation.MovementCorrection;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.JumpEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.strafe.StrafeEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.rotation.RotationUtil;
import com.moonlight.client.util.world.PacketUtil;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class RotationComponent implements Component, MinecraftInstance {

	private static boolean active;
	
	private static MovementCorrection movementCorrection;
	private static int ticksSinceLastSet;
	
	public static Vector2f prevRotation, targetRotation, rotations;
	public static float rotationSpeed;
	
	public static boolean rotate;
		
	public static void setRotations(Vector2f targetRotation, float speed, MovementCorrection correction) {
		RotationComponent.targetRotation = targetRotation;
		RotationComponent.rotationSpeed = speed;
		
		movementCorrection = correction;
		
		rotate = true;
		
		ticksSinceLastSet = 0;
		
		active = true;
		
		smooth();
	}
	
	public static void setRotations(Vector2f targetRotation, float speed, boolean rotate, MovementCorrection correction) {
		RotationComponent.targetRotation = targetRotation;
		RotationComponent.rotationSpeed = speed;
		
		movementCorrection = correction;
		
		RotationComponent.rotate = rotate;
		
		ticksSinceLastSet = 0;
		
		active = true;
		
		smooth();
	}
	
	
	public static void stop() {
		active = false;
	}
	
	private void correct() {
		Vector2f rotations = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
	    Vector2f fixedRotations = RotationUtil.resetRotation(RotationUtil.applySensitivityPatch(rotations, prevRotation));
	    mc.thePlayer.rotationYaw = fixedRotations.x;
	    mc.thePlayer.rotationPitch = fixedRotations.y;
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(!active) {
			prevRotation = new Vector2f(event.getYaw(), event.getPitch());
			return;
		}
			
		smooth();
		
		ticksSinceLastSet++;
		if (Math.abs((mc.thePlayer.rotationYawHead - mc.thePlayer.rotationYaw) % 360.0F) < 1.0F && Math.abs(
				mc.thePlayer.rotationPitchHead - mc.thePlayer.rotationPitch) < 1.0F) {	
			active = false;
			correct();
		}
		
		prevRotation = new Vector2f(rotations.x, rotations.y);
		
		if(rotate) {
			mc.thePlayer.rotationYawHead = rotations.x;
			mc.thePlayer.renderYawOffset = rotations.x;
			mc.thePlayer.rotationPitchHead = rotations.y;
		}
		
		event.setYaw(rotations.x);
		event.setPitch(rotations.y);
	}
	
	@EventLink
	public void onStrafe(StrafeEvent event) {
		if(!active) return;
				
		if(movementCorrection != MovementCorrection.OFF) {
			event.setYaw(rotations.x);
		}
		
	}
	
	@EventLink
	public void onJump(JumpEvent event) {
		if(!active) return;
				
		if(movementCorrection != MovementCorrection.OFF) {
			event.setYaw(rotations.x);
		}
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
		if(!active) return;
		
		if(movementCorrection == MovementCorrection.SILENT) {
			MoveUtil.fixMovement(event, rotations.x);
		}
	}
	
	private static void smooth() {
		if(!active || prevRotation == null || targetRotation == null) return;
		
		rotations = RotationUtil.applySensitivityPatch(RotationUtil.smooth(prevRotation, targetRotation, rotationSpeed), prevRotation);
	}
	
}
