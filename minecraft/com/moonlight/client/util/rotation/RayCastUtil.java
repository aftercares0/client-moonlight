package com.moonlight.client.util.rotation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.moonlight.client.util.MinecraftInstance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;
import net.optifine.reflect.Reflector;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class RayCastUtil implements MinecraftInstance {

	public static boolean compare(BlockPos pos1, BlockPos pos2) {
		if(pos1.getX() != pos2.getX() ||
				pos1.getZ() != pos2.getZ()) return false;

		return true;
	}

	public static boolean compareAll(BlockPos pos1, BlockPos pos2) {
		if(pos1.getX() != pos2.getX() || pos1.getY() != pos2.getY() ||
				pos1.getZ() != pos2.getZ()) return false;

		return true;
	}

	public static Entity rayCastEntity(Entity entity, double reach, float yaw, float pitch)
	{
		Entity returnEntity = null;

		if (entity != null && mc.theWorld != null)
		{
			double d1 = reach;
			Vec3 vec3 = entity.getPositionEyes(1.0F);
			boolean flag = false;
			int i = 3;
			Vec3 vec31 = entity.getLook(yaw, pitch);
			Vec3 vec32 = vec3.addVector(vec31.xCoord * reach, vec31.yCoord * reach, vec31.zCoord * reach);
			Vec3 vec33 = null;
			float f = 1.0F;
			List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord *
					reach, vec31.yCoord * reach, vec31.zCoord * reach).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(Entity p_apply_1_)
				{
					return p_apply_1_.canBeCollidedWith();
				}
			}));
			double d2 = d1;

			for (int j = 0; j < list.size(); ++j)
			{
				Entity entity1 = (Entity)list.get(j);
				float f1 = entity1.getCollisionBorderSize();
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

				if (axisalignedbb.isVecInside(vec3))
				{
					if (d2 >= 0.0D)
					{
						returnEntity = entity1;
						vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
						d2 = 0.0D;
					}
				}
				else if (movingobjectposition != null)
				{
					double d3 = vec3.distanceTo(movingobjectposition.hitVec);

					if (d3 < d2 || d2 == 0.0D)
					{
						boolean flag1 = false;

						if (!flag1 && entity1 == entity.ridingEntity)
						{
							if (d2 == 0.0D)
							{
								returnEntity = entity1;
								vec33 = movingobjectposition.hitVec;
							}
						}
						else
						{
							returnEntity = entity1;
							vec33 = movingobjectposition.hitVec;
							d2 = d3;
						}
					}
				}
			}
		}

		return returnEntity;
	}

	public static boolean lookingAtBlock(final BlockPos blockFace, final float yaw, final float pitch, final EnumFacing enumFacing, final boolean strict) {
		final MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTrace(mc.playerController.getBlockReachDistance(), yaw, pitch);
		if (movingObjectPosition == null) return false;
		final Vec3 hitVec = movingObjectPosition.hitVec;
		if (hitVec == null) return false;
		if ((hitVec.xCoord - blockFace.getX()) > 1.0 || (hitVec.xCoord - blockFace.getX()) < 0.0) return false;
		return !((hitVec.zCoord - blockFace.getZ()) > 1.0) && !((hitVec.zCoord - blockFace.getZ()) < 0.0) && (movingObjectPosition.sideHit == enumFacing || !strict);
	}

	public static BlockPos rayTraceCustom(double reach, float yaw, float pitch) {
		return mc.thePlayer.rayTrace(reach, yaw, pitch).getBlockPos();
	}
	
	public static double fovFromEntity(Entity en) {
		return ((double)(mc.thePlayer.rotationYaw - fovToEntity(en)) % 360.0D + 540.0D) % 360.0D - 180.0D;
	}

	public static boolean overBlock(Vector2f rotation, EnumFacing enumFacing, BlockPos pos, boolean strict) {
		MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTrace(4.5D, rotation.x, rotation.y);
		if (movingObjectPosition == null)
			return false;
		Vec3 hitVec = movingObjectPosition.hitVec;
		if (hitVec == null)
			return false;
		return (movingObjectPosition.getBlockPos().equals(pos) && (!strict || movingObjectPosition.sideHit == enumFacing));
	}

	public static float fovToEntity(Entity ent) {
		double x = ent.posX - mc.thePlayer.posX;
		double z = ent.posZ - mc.thePlayer.posZ;
		double yaw = Math.atan2(x, z) * 57.2957795D;
		return (float)(yaw * -1.0D);
	}

	public static boolean fov(Entity entity, float fov) {
		if(fov > 360) fov = 360;
		fov = (float)((double)fov * 0.5D);
		double v = ((double)(mc.thePlayer.rotationYaw - fovToEntity(entity)) % 360.0D + 540.0D) % 360.0D - 180.0D;
		return v > 0.0D && v < (double)fov || (double)(-fov) < v && v < 0.0D;
	}
	
	protected static final Vec3 getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
	
}
