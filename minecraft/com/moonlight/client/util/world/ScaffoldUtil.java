package com.moonlight.client.util.world;

import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.util.MinecraftInstance;

public class ScaffoldUtil implements MinecraftInstance {

	public static final List<Block> BLOCK_BLACKLIST = Arrays.asList(Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest,
            Blocks.trapped_chest, Blocks.anvil, Blocks.sand, Blocks.web, Blocks.torch,
            Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser,
            Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock,
            Blocks.dropper, Blocks.tnt, Blocks.standing_banner, Blocks.wall_banner, Blocks.redstone_torch);
	
    public static List<Block> blacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);
	
    public static boolean hasBlock() {
        int BlockInInventory = findBlock(9, 36);
        int BlockInHotbar = findBlock(36, 45);
        return BlockInInventory != -1 || BlockInHotbar != -1;
    }

    public static List<BlockData> getPossiblePlace(boolean keepY, int y) {
    	BlockPos pos = new BlockPos(mc.thePlayer).down();
    	
    	if(keepY) {
    		pos = new BlockPos(mc.thePlayer.posX, y, mc.thePlayer.posZ);
    	}

    	List<BlockData> data = new ArrayList<>();
    	data.add(getBlockData(pos));
    	
    	return data;
    }
    
    public static int findBlock(int startSlot, int endSlot) {
        for(int i = startSlot; i < endSlot; ++i) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBlock && !BLOCK_BLACKLIST.contains(((ItemBlock)stack.getItem()).getBlock())) {
                return i;
            }
        }

        return -1;
    }

    public static Vec3 getVec3(BlockPos pos, EnumFacing facing, boolean randomised) {
        Vec3 vec3 = new Vec3(pos);

        double amount1 = 0.5;
        double amount2 = 0.5;

        if(randomised) {
            amount1 = 0.45 + Math.random() * 0.1;
            amount2 = 0.45 + Math.random() * 0.1;
        }

        if(facing == EnumFacing.UP) {
            vec3 = vec3.addVector(amount1, 1, amount2);
        } else if(facing == EnumFacing.DOWN) {
            vec3 = vec3.addVector(amount1, 0, amount2);
        } else if(facing == EnumFacing.EAST) {
            vec3 = vec3.addVector(1, amount1, amount2);
        } else if(facing == EnumFacing.WEST) {
            vec3 = vec3.addVector(0, amount1, amount2);
        } else if(facing == EnumFacing.NORTH) {
            vec3 = vec3.addVector(amount1, amount2, 0);
        } else if(facing == EnumFacing.SOUTH) {
            vec3 = vec3.addVector(amount1, amount2, 1);
        }

        return vec3;
    }

    public static void placeBlock(boolean swing, BlockPos pos, EnumFacing facing, Vec3 vec) {
        ItemStack item = mc.thePlayer.getHeldItem();
        if (item != null) {
            if(mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, item, pos, facing, vec)) {
                if(swing) mc.thePlayer.swingItem();
                else PacketUtil.sendNoEvent(new C0APacketAnimation());
                mc.rightClickDelayTimer = 0;
            }
        }

        mc.playerController.updateController();
    }

    public static class BlockData {
        public BlockPos position;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

    public static BlockData getBlockData(BlockPos input, EnumFacing facing) {
        List<BlockPos> positions = Arrays.asList(
                input.add(0, -1, 0),
                input.add(-1, 0, 0),
                input.add(1, 0, 0),
                input.add(0, 0, -1),
                input.add(0, 0, 1)
        );
        for (BlockPos pos : positions) {
            if (!blacklist.contains(mc.theWorld.getBlockState(pos).getBlock())) {
                return new BlockData(pos, facing);
            }
        }
        return null;
    }
    
    public static BlockData getBlockData(BlockPos input) {
        List<BlockPos> positions = Arrays.asList(
                input.add(0, -1, 0),
                input.add(-1, 0, 0),
                input.add(1, 0, 0),
                input.add(0, 0, -1),
                input.add(0, 0, 1)
        );
        for (BlockPos pos : positions) {
            if (!blacklist.contains(mc.theWorld.getBlockState(pos).getBlock())) {
                EnumFacing facing = getFacing(input, pos);
                return new BlockData(pos, facing);
            }
        }
        return null;
    }

    public static EnumFacing getFacing(BlockPos input, BlockPos neighbor) {
        if (input.getX() < neighbor.getX()) {
            return EnumFacing.WEST;
        } else if (input.getX() > neighbor.getX()) {
            return EnumFacing.EAST;
        } else if (input.getZ() < neighbor.getZ()) {
            return EnumFacing.NORTH;
        } else if (input.getZ() > neighbor.getZ()) {
            return EnumFacing.SOUTH;
        } else {
            return EnumFacing.UP;
        }
    }

}