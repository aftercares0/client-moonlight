/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.moonlight.client.baritone.behavior;

import com.moonlight.client.baritone.Baritone;
import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.baritone.utils.ToolSet;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class InventoryBehavior extends Behavior {
    public InventoryBehavior(Baritone baritone) {
        super(baritone);
    }

    @Override
    public void onTick(TickEvent event) {
        if (!Baritone.settings().allowInventory.get()) {
            return;
        }
        if (event.getType() == TickEvent.Type.OUT) {
            return;
        }
        if (ctx.player().openContainer != ctx.player().inventoryContainer) {
            // we have a crafting table or a chest or something open
            return;
        }
        if (firstValidThrowaway() >= 9) { // aka there are none on the hotbar, but there are some in main inventory
            swapWithHotBar(firstValidThrowaway(), 8);
        }
        int pick = bestToolAgainst(Blocks.stone, ItemPickaxe.class);
        if (pick >= 9) {
            swapWithHotBar(pick, 0);
        }
    }

    private void swapWithHotBar(int inInventory, int inHotbar) {
        ctx.playerController().windowClick(ctx.player().inventoryContainer.windowId, inInventory < 9 ? inInventory + 36 : inInventory, inHotbar, 2, ctx.player());
    }

    private int firstValidThrowaway() { // TODO offhand idk
        ItemStack[] invy = ctx.player().inventory.mainInventory;
        for (int i = 0; i < invy.length; i++) {
        	if(invy[i] == null) continue;
            return i;
        }
        return -1;
    }

    private int bestToolAgainst(Block against, Class<? extends ItemTool> klass) {
        ItemStack[] invy = ctx.player().inventory.mainInventory;
        int bestInd = -1;
        double bestSpeed = -1;
        for (int i = 0; i < invy.length; i++) {
            ItemStack stack = invy[i];
            if (stack == null) {
                continue;
            }
            if (klass.isInstance(stack.getItem())) {
                double speed = ToolSet.calculateSpeedVsBlock(stack, against.getDefaultState()); // takes into account enchants
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestInd = i;
                }
            }
        }
        return bestInd;
    }
}
