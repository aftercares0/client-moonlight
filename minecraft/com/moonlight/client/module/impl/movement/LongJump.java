package com.moonlight.client.module.impl.movement;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.movement.flight.*;
import com.moonlight.client.module.impl.movement.flight.grim.*;
import com.moonlight.client.module.impl.movement.longjump.*;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.TextFormatting;

@ModuleInfo(name = "LongJump", description = "jump but long", category = Category.MOVEMENT)
public class LongJump extends Module {

	private ModeValue modes = new ModeValue("Mode", this, new WatchdogLongJump(this, "Watchdog"));
	
}
