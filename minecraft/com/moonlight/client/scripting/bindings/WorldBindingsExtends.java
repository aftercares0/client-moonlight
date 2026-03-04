package com.moonlight.client.scripting.bindings;

import java.util.ArrayList;
import java.util.List;

import com.moonlight.client.scripting.bindings.impl.WorldBindings;
import com.moonlight.client.scripting.objects.entity.Entity;
import com.moonlight.client.scripting.objects.entity.PlayerEntity;
import com.moonlight.client.scripting.utils.BlockPos;
import com.moonlight.client.util.MinecraftInstance;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class WorldBindingsExtends implements WorldBindings {

	@Override
	public List<Entity> getLoadedEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		for(net.minecraft.entity.Entity e : MinecraftInstance.mc.theWorld.loadedEntityList) {
			entities.add(new Entity(e.getEntityId(), e.posX, e.posY, e.posZ));
		}
		return entities;
	}

	@Override
	public List<PlayerEntity> getLoadedPlayerEntities() {
		List<PlayerEntity> entities = new ArrayList<PlayerEntity>();
		for(net.minecraft.entity.Entity e : MinecraftInstance.mc.theWorld.playerEntities) {
			entities.add(new PlayerEntity(e.getEntityId(), e.posX, e.posY, e.posZ));
		}
		return entities;
	}

	@Override
	public boolean isLiquid(BlockPos arg0) {
		return MinecraftInstance.mc.theWorld.getBlockState(new net.minecraft.util.BlockPos(arg0.getX(),
				arg0.getY(), arg0.getZ())).getBlock() instanceof BlockLiquid;
	}

	@Override
	public boolean isSolid(BlockPos arg0) {
		return true;
	}

	@Override
	public void removeEntity(Entity arg0) {
		MinecraftInstance.mc.theWorld.removeEntityFromWorld(arg0.getEntityId());
	}

}
