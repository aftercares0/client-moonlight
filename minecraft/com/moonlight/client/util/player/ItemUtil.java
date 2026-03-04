package com.moonlight.client.util.player;

import com.moonlight.client.util.MinecraftInstance;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemUtil implements MinecraftInstance {

	public static int getSlotFor(ItemStack item) {
        for(int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem().getClass() == item.getItem().getClass()) {
                return i;
            }
        }

        return -1;
    }
	
}
