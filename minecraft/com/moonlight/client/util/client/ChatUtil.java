package com.moonlight.client.util.client;

import com.moonlight.client.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.TextFormatting;

public class ChatUtil {

	public static void sendClientMessage(Object message) {
		if(Minecraft.getMinecraft().thePlayer == null) return;
		sendClientSideMessage(getPrefix() + message);
	}
	
	public static void sendClientSideMessage(Object message) {
		if(Minecraft.getMinecraft().thePlayer == null) return;
		ChatComponentText text = new ChatComponentText(message.toString());
		Minecraft.getMinecraft().thePlayer.addChatMessage(text);
	}

	private static String getPrefix() {
		return TextFormatting.BOLD.toString() + TextFormatting.RED.toString() + Client.NAME + TextFormatting.RESET.toString() + TextFormatting.RED.toString() + " \u00BB " + TextFormatting.RESET.toString();
	}
	
}
