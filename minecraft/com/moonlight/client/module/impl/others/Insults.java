package com.moonlight.client.module.impl.others;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PostAttackEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.WorldChangeEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.util.client.FileUtil;
import com.moonlight.client.value.impl.ModeValue;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.TextFormatting;

@ModuleInfo(name = "Insults", description = "insult other player after kill", category = Category.OTHERS)
public class Insults extends Module {

	private EntityPlayer target;
	
	private final List<String> skid;
	private final List<String> hypixel;
	
	private ModeValue modes = new ModeValue("Mode", this, "Skid", "Hypixel", "Simple");
	
	@EventLink
	public void onPreMotionE(PreMotionEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + modes.getMode().getName());
	}
	
	public Insults() {
		skid = FileUtil.getLinesFromFile("text/insults/client.txt");
		hypixel = FileUtil.getLinesFromFile("text/insults/hypixel.txt");
	}
	
	@EventLink
	public void onPostAttack(PostAttackEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			target = (EntityPlayer) event.getEntity();
		}
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(mc.thePlayer.getHealth() == 0) {
            target = null;
		}
		
		if(modes.getMode().getName().equalsIgnoreCase("Simple")) {
			if (!mc.theWorld.playerEntities.contains(target) && target != null) {
				mc.thePlayer.sendChatMessage("L, " + target.getGameProfile().getName());
	            target = null;
			}
		}else {
			if (!mc.theWorld.playerEntities.contains(target) && target != null) {
	            mc.thePlayer.sendChatMessage(randomMessage());
	            target = null;
	        }
		}
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		if(event.getPacket() instanceof S08PacketPlayerPosLook) {
			target = null;
		}
	}
	
	@EventLink
	public void onWorldChange(WorldChangeEvent event) {
		target = null;
	}
	
	private String randomMessage() {
        return getInsults().get(ThreadLocalRandom.current().nextInt(0, getInsults().size() - 1));
    }
	
	private List<String> getInsults() {
		if(modes.getMode().getName().equalsIgnoreCase("Skid")) return skid;
		return hypixel;
	}
	
}
