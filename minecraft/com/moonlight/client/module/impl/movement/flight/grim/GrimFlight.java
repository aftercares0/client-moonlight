package com.moonlight.client.module.impl.movement.flight.grim;

import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.impl.movement.flight.LuckyVNFlight;
import com.moonlight.client.module.impl.movement.flight.grim.impl.*;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.player.EntityPlayer;

public class GrimFlight extends Mode {
	
	private ModeValue mode = new ModeValue("Mode", getParent(), new GrimTNTFly(getParent(), "Damage"),
			new GrimJumpFly(getParent(), "Jump"), new GrimBoatFly(getParent(), "Boat"));
	
	public GrimFlight(Module parent, String name) {
		super(parent, name);
	}

}
