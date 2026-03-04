package com.moonlight.client.util.rotation;

import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.util.MinecraftInstance;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class RotationUtil implements MinecraftInstance {

	public static Vector2f getBlockRotation(BlockPos pos, final EnumFacing enumfacing) {
        final EntityEgg var4 = new EntityEgg(mc.theWorld);
        var4.posX = pos.getX() + 0.5D;
        var4.posY = pos.getY() + 0.5D;
        var4.posZ = pos.getZ() + 0.5D;
        var4.posX += (double) enumfacing.getDirectionVec().getX() * 0.5D;
        var4.posY += (double) enumfacing.getDirectionVec().getY() * 0.5D;
        var4.posZ += (double) enumfacing.getDirectionVec().getZ() * 0.5D;
        return getRotations(var4, false);
    }
	
	public static Vector2f getBlockRotation(double x, double y, double z) {
        final EntityEgg var4 = new EntityEgg(mc.theWorld);
        var4.posX = x;
        var4.posY = y;
        var4.posZ = z;
        return getRotations(var4, false);
	}
    
	
	public static Vector2f getRotations(Entity entity, boolean random) {
        final EntityPlayerSP player = mc.thePlayer;
        final double x = entity.posX - player.posX;
        final double y = entity.posY - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        final double z = entity.posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));

        final double randomAmount = 3;

        if(random) {
        	float randomYaw = 0, randomPitch = 0;
            if (randomAmount != 0) {
                randomYaw += ((Math.random() - 0.5) * randomAmount) / 2;
                randomYaw += ((Math.random() - 0.5) * randomAmount) / 2;
                randomPitch += ((Math.random() - 0.5) * randomAmount) / 2;

                if (mc.thePlayer.ticksExisted % 5 == 0) {
                    randomYaw = (float) (((Math.random() - 0.5) * randomAmount) / 2);
                    randomPitch = (float) (((Math.random() - 0.5) * randomAmount) / 2);
                }

                yaw += randomYaw;
                pitch += randomPitch;
            }
        }
        
        return new Vector2f(yaw, pitch);
    }
	
	public static Vector2f getEntityRotation(Entity entity, boolean random) {
        double deltaX = entity.posX + (entity.posX - entity.lastTickPosX) - mc.thePlayer.posX;
        double deltaY = entity.posY - 3.5 + entity.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        double deltaZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) - mc.thePlayer.posZ;
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ));
        float pitch = (float) Math.toDegrees(-Math.atan(deltaY / distance));

        if(deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ /deltaX)));
        }else if(deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ /deltaX)));
        }

        if(random) {
        	final double randomAmount = 3;

            float randomYaw = 0, randomPitch = 0;
            if (randomAmount != 0) {
                randomYaw += ((Math.random() - 0.5) * randomAmount) / 2;
                randomYaw += ((Math.random() - 0.5) * randomAmount) / 2;
                randomPitch += ((Math.random() - 0.5) * randomAmount) / 2;

                if (mc.thePlayer.ticksExisted % 5 == 0) {
                    randomYaw = (float) (((Math.random() - 0.5) * randomAmount) / 2);
                    randomPitch = (float) (((Math.random() - 0.5) * randomAmount) / 2);
                }

                yaw += randomYaw;
                pitch += randomPitch;
            }
        }  

        return new Vector2f(yaw, pitch);
    }
	
	public static Vector2f smooth(Vector2f prevRotation, Vector2f targetRotation, float rotationSpeed)
    {
        float f = MathHelper.wrapAngleTo180_float(targetRotation.x - prevRotation.x);
        float f2 = MathHelper.wrapAngleTo180_float(targetRotation.y - prevRotation.y);

        if (f > rotationSpeed)
        {
            f = rotationSpeed;
        }else if (f < -rotationSpeed)
        {
            f = -rotationSpeed;
        }

        if (f2 > rotationSpeed)
        {
            f2 = rotationSpeed;
        }else if (f2 < -rotationSpeed)
        {
            f2 = -rotationSpeed;
        }

        return new Vector2f(prevRotation.x + f, prevRotation.y + f2);
    }

	public static Vector2f applySensitivityPatch(final Vector2f rotation, final Vector2f previousRotation) {
        final float mouseSensitivity = (float) (mc.gameSettings.mouseSensitivity * (1 + Math.random() / 10000000) * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = previousRotation.x + (float) (Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float) (Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp_float(pitch, -90, 90));
    }

	public static Vector2f resetRotation(Vector2f rotation) {
		if (rotation == null)
		      return null; 
		float yaw = rotation.x + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - rotation.x);
		float pitch = mc.thePlayer.rotationPitch;
		return new Vector2f(yaw, pitch);
	}
	
}
