package com.moonlight.client.module.impl.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.util.timer.TimerHelper;
import com.moonlight.client.value.impl.NumberValue;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Stealer", description = "Steal items in chest" , category = Category.PLAYER, key = Keyboard.KEY_P)
public class Stealer extends Module {
    
	private TimerHelper timer = new TimerHelper();
	
	private float lastTimer;
	
	private NumberValue<Float> min = new NumberValue<Float>("Min", 20.0f, 0f, 500f);
	private NumberValue<Float> max = new NumberValue<Float>("Max", 25.0f, 0f, 500f);
	
	@Override
	public void onEnabled() {
		lastTimer = RandomUtils.nextFloat(min.getValue(), max.getValue());
	}
	
    @EventLink
    public void onUpdate(PreUpdateEvent event) {
    	if(min.getValue() > max.getValue()) min.setValue(max.getValue());
    	if(!(mc.currentScreen instanceof GuiChest)) return;
    	
        ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;

    	if(!timer.hasTimeElapsed(lastTimer)) return;
    	
    	for(int i = 0; i < container.inventorySlots.size(); i++) {
            final ItemStack stack = container.getLowerChestInventory().getStackInSlot(i);
            if(stack == null) continue;
    		if(isTrash(stack.getItem())) continue;
    		
            mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
    		timer.reset();
    		lastTimer = RandomUtils.nextFloat(min.getValue(), max.getValue());
    		return;
    	}
    }
    
    private final List<Item> WHITELISTED_ITEMS = Arrays.asList(Items.fishing_rod, Items.water_bucket, Items.bucket, Items.arrow, Items.bow, Items.snowball, Items.egg, Items.ender_pearl);
    
    public boolean isTrash(Item item) {
    	if (item instanceof ItemBlock) {
            final Block block = ((ItemBlock) item).getBlock();
            if (!(block instanceof BlockStainedGlass) && !(block instanceof BlockTNT) && !(block instanceof BlockFalling) && block.isFullBlock()) {
                return false;
            }
        }
    	
    	return !(item instanceof ItemSword ||
                item instanceof ItemTool ||
                item instanceof ItemArmor ||
                item instanceof ItemFood || item instanceof ItemPotion ||
                WHITELISTED_ITEMS.contains(item));
    }
}
