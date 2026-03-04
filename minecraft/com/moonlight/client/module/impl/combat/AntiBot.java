package com.moonlight.client.module.impl.combat;

import com.moonlight.client.Client;
import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.WorldChangeEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.util.TextFormatting;

import com.moonlight.client.module.impl.combat.antibot.MatrixAntiBot;
import com.moonlight.client.module.impl.combat.antibot.WatchdogAntiBot;
import com.moonlight.client.module.impl.combat.criticals.*;

@ModuleInfo(name = "AntiBot", description = "remove aura bot", category = Category.COMBAT)
public class AntiBot extends Module {

    private ModeValue mode = new ModeValue("Mode", this, new MatrixAntiBot(this, "Matrix")
    		, new WatchdogAntiBot(this, "Watchdog"));

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + mode.getMode().getName());
    }
    
    @EventLink
    public void onWorldChange(WorldChangeEvent event) {
    	Client.INSTANCE.bots.clear();
    }

}
