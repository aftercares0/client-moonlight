package com.moonlight.client.command.impl;

import net.minecraft.util.TextFormatting;
import org.lwjgl.input.Keyboard;

import com.moonlight.client.Client;
import com.moonlight.client.command.Command;
import com.moonlight.client.util.client.ChatUtil;

public class BindCommand extends Command {

	public BindCommand() {
		super("bind");
	}

	@Override
	public void onCommand(String[] args) {
		//args[0] is shit so ignore it
		if(args.length < 3) {
			ChatUtil.sendClientMessage("Useage: .bind <module> <key>");
			return;
		}
				
		if(Client.INSTANCE.moduleManager.get(args[1]) == null) {
			ChatUtil.sendClientMessage("Module not found!");
			return;
		}
		
		com.moonlight.client.module.api.Module m = Client.INSTANCE.moduleManager.get(args[1]);

		if(Keyboard.getKeyIndex(args[2].toUpperCase()) == 0) {
			ChatUtil.sendClientMessage(TextFormatting.GRAY + "Bind " + TextFormatting.WHITE + m.getName()
			+ TextFormatting.GRAY + " to " + TextFormatting.WHITE + "NONE" + TextFormatting.GRAY  + ".");
			
			m.setKeyCode(0);
		}else {
			ChatUtil.sendClientMessage(TextFormatting.GRAY + "Bind " + TextFormatting.WHITE + m.getName()
			+ TextFormatting.GRAY + " to " + TextFormatting.WHITE + args[2].toUpperCase() + TextFormatting.GRAY  + ".");
			
			m.setKeyCode(Keyboard.getKeyIndex(args[2].toUpperCase()));
		}
	}

}
