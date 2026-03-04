package com.moonlight.client.util.player;

import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.strafe.StrafeEvent;
import com.moonlight.client.util.MinecraftInstance;

import net.minecraft.util.MathHelper;

public class MoveUtil implements MinecraftInstance {

	public static float[] incrementMoveDirection(float forward, float strafe) {
	    if (forward == 0 && strafe == 0) {
	        return new float[]{forward, strafe};
	    }

	    float value = Math.abs(forward != 0 ? forward : strafe);

	    if (forward > 0) {
	        strafe = (strafe > 0) ? 0 : ((strafe == 0) ? -value : strafe);
	    } else if (forward == 0) {
	        forward = (strafe > 0) ? value : -value;
	    } else {
	        strafe = (strafe < 0) ? 0 : ((strafe == 0) ? value : (strafe > 0) ? 0 : strafe);
	    }

	    return new float[]{forward, strafe};
	}
	
	 public static void fixMovement(final MoveInputEvent event, final float yaw) {
		 final float forward = event.forward;
	     final float strafe = event.strafe;

	     final double angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(mc.thePlayer.rotationYaw, forward, strafe)));

	     if (forward == 0 && strafe == 0) {
	    	 return;
	     }
	     
	     float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;
	     
	     for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
	    	 for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
	    		 if (predictedStrafe == 0 && predictedForward == 0) continue;
	    		 
	    		 final double predictedAngle = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(yaw, predictedForward, predictedStrafe)));
	             final double difference = Math.abs(angle - predictedAngle);
	             
	             if (difference < closestDifference) {
	            	 closestDifference = (float) difference;
	            	 closestForward = predictedForward;
	            	 closestStrafe = predictedStrafe;
	             }
	    	 }
	     }
	     
	     event.forward = closestForward;
	     event.strafe = closestStrafe;
	 }
	
	public static float getPlayerDirection() {
        float direction = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.movementInput.realForward > 0) {
            if (mc.thePlayer.movementInput.realStrafe > 0) {
                direction -= 45;
            } else if (mc.thePlayer.movementInput.realStrafe < 0) {
                direction += 45;
            }
        } else if (mc.thePlayer.movementInput.realForward < 0) {
            if (mc.thePlayer.movementInput.realStrafe > 0) {
                direction -= 135;
            } else if (mc.thePlayer.movementInput.realStrafe < 0) {
                direction += 135;
            } else {
                direction -= 180;
            }
        } else {
            if (mc.thePlayer.movementInput.realStrafe > 0) {
                direction -= 90;
            } else if (mc.thePlayer.movementInput.realStrafe < 0) {
                direction += 90;
            }
        }

        return direction;
    }
	
	public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }
	
	public static void stop() {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
    }
	
	public static float direction() {
        return direction(mc.thePlayer.rotationYaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
    }
	
	public static float direction(float rotationYaw) {
        return direction(rotationYaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
    }

    public static float direction(float rotationYaw, float forward, float strafe) {
    	return (float) Math.toRadians(directionYaw(rotationYaw, forward, strafe));
    }
    
    public static float directionYaw(float rotationYaw, float forward, float strafe) {
        if (forward < 0f) rotationYaw += 180f;
        float f = 1f;
        if (forward < 0f) f = -0.5f;
        if (forward > 0f) f = 0.5f;
        if (strafe > 0f) rotationYaw -= 90f * f;
        if (strafe < 0f) rotationYaw += 90f * f;
        return rotationYaw;
    }
    
    public static void strafe(float yaw, float speed) {
        mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
	
    public static void strafe(float speed) {
    	strafe(direction(), speed);
    }
    
    public static float getSpeed() {
        return getSpeed(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }

    public static float getSpeed(double motionX, double motionZ) {
        return (float) Math.sqrt(motionX * motionX + motionZ * motionZ);
    }
}
