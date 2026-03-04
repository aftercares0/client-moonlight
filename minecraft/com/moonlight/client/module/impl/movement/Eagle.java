package com.moonlight.client.module.impl.movement;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.movement.speed.*;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.util.TextFormatting;

@ModuleInfo(name = "Eagle", description = "just eagle", category = Category.MOVEMENT)
public class Eagle extends Module {

	@EventLink
    public void onPreMotion(PreMotionEvent event) {
        if(mc.thePlayer.onGround && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() instanceof BlockAir) {
            mc.gameSettings.keyBindSprint.setPressed(true);
        }else {
            mc.gameSettings.keyBindSprint.setPressed(false);
        }
    }

    @Override
    public void onDisable() {
        if(mc.thePlayer == null) return;
        //mc.thePlayer.safeWalk = false;
        mc.gameSettings.keyBindSprint.setPressed(false);
    }
	
}
