package com.moonlight.client.command.impl;

import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.TextFormatting;
import org.lwjgl.input.Keyboard;

import com.moonlight.client.Client;
import com.moonlight.client.command.Command;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.world.PacketUtil;

public class FakeDamageCommand extends Command {

	public FakeDamageCommand() {
		super("fakedamage");
	}

	@Override
	public void onCommand(String[] args) {
		if(mc.thePlayer != null) {
			S19PacketEntityStatus s19 = new S19PacketEntityStatus(mc.thePlayer, (byte) 2);
			PacketUtil.receiveNoEvent(s19);
		}
	}

}
