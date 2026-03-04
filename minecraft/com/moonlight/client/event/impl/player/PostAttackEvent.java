package com.moonlight.client.event.impl.player;

import com.moonlight.client.event.Event;

import net.minecraft.entity.Entity;

public class PostAttackEvent extends Event {

	private final Entity entity;
	
	public PostAttackEvent(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
}
