package com.moonlight.client.module.impl.others;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.component.impl.rotation.MovementCorrection;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.client.ForceUseStop;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.util.player.ItemUtil;
import com.moonlight.client.util.rotation.RotationUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;

@ModuleInfo(name = "Always Gap", description = "" , category = Category.OTHERS)
public class AlwaysGap extends Module {

    private BooleanValue autogive = new BooleanValue("auto give", true);

    @Override
    public void onDisable() {
        
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        int slot = ItemUtil.getSlotFor(new ItemStack(Items.golden_apple));
        
        if(slot == -1 && autogive.getState()) {
        	mc.thePlayer.sendChatMessage("/give golden_apple 64");
        	mc.thePlayer.sendChatMessage("/give @p golden_apple 64");
        	
        	slot = ItemUtil.getSlotFor(new ItemStack(Items.golden_apple));
        }
        
        if(slot != -1) {
        	mc.thePlayer.inventory.currentItem = slot - 36;
        }
    }
    
    @EventLink
    public void onForceUse(ForceUseStop event) {
    	if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemAppleGold) {
    		event.forcePress = true;
    	}
    }


}