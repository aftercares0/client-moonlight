package com.moonlight.client.module.impl.movement;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.movement.flight.*;
import com.moonlight.client.module.impl.movement.flight.grim.GrimFlight;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.TextFormatting;

@ModuleInfo(name = "Flight", description = "wow, flying on survivals?!?!?!", category = Category.MOVEMENT, key = Keyboard.KEY_F)
public class Flight extends Module {

	private ModeValue modes = new ModeValue("Mode", this, new VanillaFlight(this, "Vanilla"), new GrimFlight(this, "Grim"),
			new StuckFlight(this, "Stuck"), new NCPFlight(this, "LatestNCP"), new VerusFlight(this, "Verus") , new VulcanFlight(this, "Vulcan") , new GroundCollisionFlight(this, "Ground Collis"), new RedeFly(this, "Redesky"));
	
	private BooleanValue fakeDamage = new BooleanValue("Fake Damage", false);
	private BooleanValue fakeBobbing = new BooleanValue("Fake Bobbing", false);

	@Override
	public void onEnabled() {
		if(mc.thePlayer != null && fakeDamage.getState()) {
			S19PacketEntityStatus s19 = new S19PacketEntityStatus(mc.thePlayer, (byte) 2);
			PacketUtil.receiveNoEvent(s19);
		}
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + modes.getMode().getName());
		
		if(fakeBobbing.getState()) {
			mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = 0.1f;
		}
	}
	
}
