package com.moonlight.client.module.impl.others;

import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.WorldChangeEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.others.autoplay.NormalAutoPlay;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.rotation.RayCastUtil;
import com.moonlight.client.util.timer.TimerHelper;
import com.moonlight.client.value.impl.NumberValue;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.TextFormatting;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Ratter", description = "ratted your pc omg" , category = Category.OTHERS)
public class Ratter extends Module {

	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            ChatUtil.sendClientMessage("Your IP address is " + ipAddress.getHostAddress() + ", now I'm gonna leak it"
            		+ "to the chinese goverement");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
	}
	
    @Override
    public void onEnabled() {

    }
    
    @Override
    public void onDisable() {
    	
    }
    
   

}
