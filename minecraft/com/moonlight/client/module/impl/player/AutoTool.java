package com.moonlight.client.module.impl.player;

import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.value.impl.BooleanValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
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

import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "AutoTool", description = "auto tool" , category = Category.PLAYER)
public class AutoTool extends Module {

    @Override
    public void onEnabled() {
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        if (mc.objectMouseOver != null && mc.gameSettings.keyBindPickBlock.isKeyDown()) {
            MovingObjectPosition objectMouseOver = mc.objectMouseOver;
            if (objectMouseOver.getBlockPos() != null) {
                Block block = mc.theWorld.getBlockState(objectMouseOver.getBlockPos()).getBlock();
                switchTool(block);
            }
        }
    }

    @EventLink
    public void onPreAttack(PreAttackEvent event) {
        switchSword();
    }

    @EventLink
    public void onPacketSend(PacketSendEvent event) {

    }

    private void switchTool(Block block) {
        float strength = 1.0F;
        int bestItem = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack == null) {
                continue;
            }
            float strVsBlock = itemStack.getStrVsBlock(block);
            if (strVsBlock > strength) {
                strength = strVsBlock;
                bestItem = i;
            }
        }
        if (bestItem != -1) {
            int oldSlot = -1;
            mc.thePlayer.inventory.currentItem = bestItem;
        }
    }

    private void switchSword() {
        float damage = 1;
        int bestItem = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack is = mc.thePlayer.inventory.mainInventory[i];
            if (is != null && is.getItem() instanceof ItemSword && ((ItemSword)is.getItem()).getDamageVsEntity() > damage) {
                damage = ((ItemSword)is.getItem()).getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(16, is);
                bestItem = i;
            }
        }
        if (bestItem != -1) {
            int oldSlot = -1;
            mc.thePlayer.inventory.currentItem = bestItem;
        }
    }

}
