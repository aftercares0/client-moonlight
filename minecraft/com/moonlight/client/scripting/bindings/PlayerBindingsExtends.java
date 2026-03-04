package com.moonlight.client.scripting.bindings;

import java.util.UUID;

import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.scripting.ScriptingModule;
import com.moonlight.client.scripting.bindings.impl.PlayerBindings;
import com.moonlight.client.scripting.objects.entity.Entity;
import com.moonlight.client.scripting.objects.packet.Packet;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.rotation.RotationUtil;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class PlayerBindingsExtends implements PlayerBindings {

	@Override
	public void addChatMessage(String arg0) {
		ChatUtil.sendClientSideMessage(arg0);
	}

	@Override
	public void attack(Entity arg0) {
		MinecraftInstance.mc.playerController.attackEntity(MinecraftInstance.mc.thePlayer, MinecraftInstance.mc.theWorld.getEntityByID(arg0.getEntityId()));
	}

	@Override
	public double getBaseMoveSpeed() {
		return 0.22F;
	}

	@Override
	public float getDirection() {
		return MoveUtil.direction();
	}

	@Override
	public float getFall() {
		return MinecraftInstance.mc.thePlayer.fallDistance;
	}

	@Override
	public float getFlySpeed() {
		return MinecraftInstance.mc.thePlayer.capabilities.getFlySpeed();
	}

	@Override
	public boolean getFlying() {
		return MinecraftInstance.mc.thePlayer.capabilities.isFlying;
	}

	@Override
	public int getFoodLevel() {
		return MinecraftInstance.mc.thePlayer.getFoodStats().getFoodLevel();
	}

	@Override
	public float getHealthLevel() {
		return MinecraftInstance.mc.thePlayer.getHealth();
	}

	@Override
	public int getHeldItemId() {
		if(MinecraftInstance.mc.thePlayer.getHeldItem() == null) return -1;
		return Item.getIdFromItem(MinecraftInstance.mc.thePlayer.getHeldItem().getItem());
	}

	@Override
	public String getHeldItemName() {
		if(MinecraftInstance.mc.thePlayer.getHeldItem() == null) return "null";
		return MinecraftInstance.mc.thePlayer.getHeldItem().getItem().getUnlocalizedName();
	}

	@Override
	public int getHurtTime() {
		return MinecraftInstance.mc.thePlayer.hurtTime;
	}

	@Override
	public int getItemInUseCount() {
		return MinecraftInstance.mc.thePlayer.getItemInUseCount();
	}

	@Override
	public int getJumpBoostAmplifier() {
        if (MinecraftInstance.mc.thePlayer.isPotionActive(Potion.jump)) {
    		return MinecraftInstance.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
        }

        return 0;
	}

	@Override
	public float getJumpMovementFactor() {
		return 0.42F;
	}

	@Override
	public double getMotionX() {
		return MinecraftInstance.mc.thePlayer.motionX;
	}

	@Override
	public double getMotionY() {
		return MinecraftInstance.mc.thePlayer.motionY;
	}

	@Override
	public double getMotionZ() {
		return  MinecraftInstance.mc.thePlayer.motionZ;
	}

	@Override
	public float getMoveForward() {
		return MinecraftInstance.mc.thePlayer.movementInput.realForward;
	}

	@Override
	public float getMoveStrafing() {
		return MinecraftInstance.mc.thePlayer.movementInput.realStrafe;
	}

	@Override
	public String getName() {
		return MinecraftInstance.mc.thePlayer.getName();
	}

	@Override
	public float getPitch() {
		return MinecraftInstance.mc.thePlayer.rotationPitch;
	}

	private EnumFacing convertFacing(com.moonlight.client.scripting.utils.EnumFacing action) {
		for(EnumFacing a : EnumFacing.values()) {
			if(a.name().equalsIgnoreCase(action.name())) {
				return a;
			}
		}
		
		return null;
	}
	
	@Override
	public float[] getRotations(double arg0, double arg1, double arg2, com.moonlight.client.scripting.utils.EnumFacing arg3) {
		Vector2f rotations = RotationUtil.getBlockRotation(new BlockPos(arg0, arg1, arg2), convertFacing(arg3));
		return new float[] {rotations.x, rotations.y};
	}

	@Override
	public float[] getRotations(double arg0, double arg1, double arg2, double arg3) {
		Vector2f rotations = RotationUtil.getBlockRotation(arg0, arg1, arg2);
		return new float[] {rotations.x, rotations.y};
	}

	@Override
	public float getSpeed() {
		return MoveUtil.getSpeed();
	}

	@Override
	public int getSpeedAmplifier() {
		if (MinecraftInstance.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
    		return MinecraftInstance.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
        }

        return 0;
	}

	@Override
	public float getStep() {
		return MinecraftInstance.mc.thePlayer.stepHeight;
	}

	@Override
	public int getTicksExisted() {
		return MinecraftInstance.mc.thePlayer.ticksExisted;
	}

	@Override
	public UUID getUniqueID() {
		return MinecraftInstance.mc.thePlayer.getUniqueID();
	}

	@Override
	public double getX() {
		return MinecraftInstance.mc.thePlayer.posX;
	}

	@Override
	public double getY() {
		return MinecraftInstance.mc.thePlayer.posY;
	}

	@Override
	public float getYaw() {
		return MinecraftInstance.mc.thePlayer.rotationYaw;
	}

	@Override
	public double getZ() {
		return MinecraftInstance.mc.thePlayer.posZ;
	}

	@Override
	public void gotoLevel(int arg0) {
		MinecraftInstance.mc.thePlayer.sendChatMessage("#goto " + arg0);
	}

	@Override
	public void gotoPosition(int arg0, int arg1, int arg2) {
		MinecraftInstance.mc.thePlayer.sendChatMessage("#goto " + arg0 + " " + arg1 + " " + arg2) ;
	}

	@Override
	public boolean isBlocking() {
		return MinecraftInstance.mc.thePlayer.isBlocking();
	}

	@Override
	public boolean isBurning() {
		return MinecraftInstance.mc.thePlayer.isBurning();
	}

	@Override
	public boolean isCollidedHorizontally() {
		return MinecraftInstance.mc.thePlayer.isCollidedHorizontally;
	}

	@Override
	public boolean isCollidedVertically() {
		return MinecraftInstance.mc.thePlayer.isCollidedVertically;
	}

	@Override
	public boolean isDead() {
		return MinecraftInstance.mc.thePlayer.isDead;
	}

	@Override
	public boolean isEating() {
		return MinecraftInstance.mc.thePlayer.isEating();
	}

	@Override
	public boolean isInLiquid() {
		return MinecraftInstance.mc.theWorld.getBlockState(MinecraftInstance.mc.thePlayer.getPosition()).getBlock() instanceof BlockLiquid;
	}

	@Override
	public boolean isInsideBlock() {
		return !(MinecraftInstance.mc.theWorld.getBlockState(MinecraftInstance.mc.thePlayer.getPosition()).getBlock() instanceof BlockAir);
	}

	@Override
	public boolean isMoving() {
		return MoveUtil.isMoving();
	}

	@Override
	public boolean isNoClip() {
		return MinecraftInstance.mc.thePlayer.noClip;
	}

	@Override
	public boolean isOnGround() {
		return MinecraftInstance.mc.thePlayer.onGround;
	}

	@Override
	public boolean isOnLadder() {
		return MinecraftInstance.mc.thePlayer.isOnLadder();
	}

	@Override
	public boolean isOnLiquid() {
		return MinecraftInstance.mc.theWorld.getBlockState(MinecraftInstance.mc.thePlayer.getPosition().down()).getBlock() instanceof BlockLiquid;
	}

	@Override
	public boolean isSneaking() {
		return MinecraftInstance.mc.thePlayer.isSneaking();
	}

	@Override
	public boolean isSprinting() {
		return MinecraftInstance.mc.thePlayer.isSprinting();
	}

	@Override
	public boolean isSwingInProgress() {
		return MinecraftInstance.mc.thePlayer.isSwingInProgress;
	}

	@Override
	public boolean isUsingItem() {
		return MinecraftInstance.mc.thePlayer.isUsingItem();
	}

	@Override
	public void jump() {
		MinecraftInstance.mc.thePlayer.jump();
	}

	@Override
	public void respawnPlayer() {
		MinecraftInstance.mc.thePlayer.respawnPlayer();
	}

	@Override
	public void sendChatMessage(String arg0) {
		MinecraftInstance.mc.thePlayer.sendChatMessage(arg0);
	}

	@Override
	public void setFall(float arg0) {
		MinecraftInstance.mc.thePlayer.fallDistance = arg0;
	}

	@Override
	public void setFlySpeed(float arg0) {
		MinecraftInstance.mc.thePlayer.capabilities.setFlySpeed(arg0);
	}

	@Override
	public void setFlying(boolean arg0) {
		MinecraftInstance.mc.thePlayer.capabilities.isFlying = arg0;
	}

	@Override
	public void setForward(float arg0) {
		MinecraftInstance.mc.thePlayer.movementInput.moveForward = arg0;
	}

	@Override
	public void setItemInUseCount(int arg0) {
		
	}

	@Override
	public void setMotionX(double arg0) {
		MinecraftInstance.mc.thePlayer.motionX = arg0;
	}

	@Override
	public void setMotionY(double arg0) {
		MinecraftInstance.mc.thePlayer.motionY = arg0;
	}

	@Override
	public void setMotionZ(double arg0) {
		MinecraftInstance.mc.thePlayer.motionZ = arg0;
	}

	@Override
	public void setNoClip(boolean arg0) {
		MinecraftInstance.mc.thePlayer.noClip = arg0;
	}

	@Override
	public void setOnGround(boolean arg0) {
		MinecraftInstance.mc.thePlayer.onGround = arg0;
	}

	@Override
	public void setPitch(float arg0) {
		MinecraftInstance.mc.thePlayer.rotationPitch = arg0;
	}

	@Override
	public void setPosX(double arg0) {
		MinecraftInstance.mc.thePlayer.setPosition(arg0, MinecraftInstance.mc.thePlayer.posY, MinecraftInstance.mc.thePlayer.posZ);
	}

	@Override
	public void setPosY(double arg0) {
		MinecraftInstance.mc.thePlayer.setPosition(MinecraftInstance.mc.thePlayer.posX, arg0, MinecraftInstance.mc.thePlayer.posZ);
	}

	@Override
	public void setPosZ(double arg0) {
		MinecraftInstance.mc.thePlayer.setPosition(MinecraftInstance.mc.thePlayer.posX, MinecraftInstance.mc.thePlayer.posY, arg0);
	}

	@Override
	public void setPosition(double arg0, double arg1, double arg2) {
		MinecraftInstance.mc.thePlayer.setPosition(arg0, arg1, arg2);
	}

	@Override
	public void setPositionAndUpdate(double arg0, double arg1, double arg2) {
		MinecraftInstance.mc.thePlayer.setPositionAndUpdate(arg0, arg1, arg2);
	}

	@Override
	public void setSneaking(boolean arg0) {
		MinecraftInstance.mc.gameSettings.keyBindSprint.setPressed(arg0);
	}

	@Override
	public void setSpeed(float arg0) {
		MinecraftInstance.mc.thePlayer.capabilities.setPlayerWalkSpeed(arg0);
	}

	@Override
	public void setSprinting(boolean arg0) {
		MinecraftInstance.mc.thePlayer.setSprinting(arg0);
	}

	@Override
	public void setStep(float arg0) {
		MinecraftInstance.mc.thePlayer.stepHeight = arg0;
	}

	@Override
	public void setStrafing(float arg0) {
		MoveUtil.strafe(arg0);
	}

	@Override
	public void setYaw(float arg0) {
		MinecraftInstance.mc.thePlayer.rotationYaw = arg0;
	}

	@Override
	public void swingItem() {
		MinecraftInstance.mc.thePlayer.swingItem();
	}

	@Override
	public void syncCurrentPlayItem() {
		MinecraftInstance.mc.playerController.syncCurrentPlayItem();
	}

}
