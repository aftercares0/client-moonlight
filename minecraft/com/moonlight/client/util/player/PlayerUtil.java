package com.moonlight.client.util.player;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.moonlight.client.util.MinecraftInstance;

public class PlayerUtil implements MinecraftInstance {

	public static Block blockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).add(offsetX, offsetY, offsetZ)).getBlock();
    }

    public static boolean isBlockUnder(double height) {
    	for (int offset = 0; offset < height; offset += 2) {
            AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0.0D, -offset, 0.0D);
            if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, bb).isEmpty())
              return true; 
        } 
        return false;
      }
    
    public static void rightClickMouse(Entity entity)
    {
        if (!mc.playerController.getIsHittingBlock())
        {
            mc.rightClickDelayTimer = 4;
            boolean flag = true;
            ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();

            if (mc.playerController.isPlayerRightClickingOnEntity(mc.thePlayer, entity, new MovingObjectPosition(entity)))
            {
                flag = false;
            }
            else if (mc.playerController.interactWithEntitySendPacket(mc.thePlayer, entity))
            {
                flag = false;
            }

            if (flag)
            {
                ItemStack itemstack1 = mc.thePlayer.inventory.getCurrentItem();

                if (itemstack1 != null && mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, itemstack1))
                {
                    mc.entityRenderer.itemRenderer.resetEquippedProgress2();
                }
            }
        }
    }
	
}
