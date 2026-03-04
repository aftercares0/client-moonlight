package com.moonlight.client.module.impl.movement;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.movement.speed.*;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.util.TextFormatting;

@ModuleInfo(name = "Sprint", description = "auto sprint", category = Category.MOVEMENT)
public class Sprint extends Module {

    @Override
    public void onDisable() {
        if(mc.thePlayer == null) return;
        mc.gameSettings.keyBindInventory.setPressed(false);
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        mc.gameSettings.keyBindInventory.setPressed(true);
    }

}
