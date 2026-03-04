package com.moonlight.client.module.impl.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
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
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.util.timer.TimerHelper;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.impl.NumberValue;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "FastMine", description = "mine block faster" , category = Category.PLAYER)
public class FastMine extends Module {
    
	private NumberValue<Integer> percentage = new NumberValue("Percentage", 90, 1, 100);
	
	private EnumFacing facing;
    private BlockPos pos;
    private float damage;

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	mc.playerController.blockHitDelay = 0;
        if (pos != null) {
            IBlockState blockState = mc.theWorld.getBlockState(pos);
            if (blockState == null) return;

            damage += blockState.getBlock().getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, pos);
            
            if (damage >= (percentage.getValue() / 100.0D)) {
                mc.theWorld.setBlockState(pos, Blocks.air.getDefaultState(), 11);
                PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, facing));
                damage = 0;
            }
        }
    }
    
    @EventLink
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacket() instanceof C07PacketPlayerDigging) {
            C07PacketPlayerDigging packet = (C07PacketPlayerDigging) event.getPacket();
            if (packet.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                pos = packet.getPosition();
                facing = packet.getFacing();
                damage = 0;
            } else if (packet.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK | packet.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                pos = null;
                facing = null;
            }
        }
    }
    
}
