package com.moonlight.client.module.impl.player;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.player.noslow.*;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.util.TextFormatting;

@ModuleInfo(name = "No Slow Down", description = "stop slow down on item use!", category = Category.PLAYER)
public class NoSlow extends Module {

	private ModeValue modes = new ModeValue("Mode", this, new VanillaNoSlow(this, "Vanilla"), new VariableNoSlow(this, "Variable"),
			new GrimNoSlow(this, "Grim"), new SwitchItemNoSlow(this, "SwitchItem"), new OldIntaveNoSlow(this, "Old Intave"),
			new WatchdogNoSlow(this, "Watchdog"));
		
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + modes.getMode().getName());
	}
	
}
