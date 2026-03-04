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

package com.moonlight.client.baritone.utils.player;

import com.moonlight.client.baritone.api.utils.IPlayerController;
import com.moonlight.client.baritone.utils.Helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldSettings.GameType;

/**
 * Implementation of {@link IPlayerController} that chains to the primary player controller's methods
 *
 * @author Brady
 * @since 12/14/2018
 */
public enum PrimaryPlayerController implements IPlayerController, Helper {

    INSTANCE;

    @Override
    public boolean onPlayerDamageBlock(BlockPos pos, EnumFacing side) {
        return mc.playerController.onPlayerDamageBlock(pos, side);
    }

    @Override
    public void resetBlockRemoving() {
        mc.playerController.resetBlockRemoving();
    }

    @Override
    public ItemStack windowClick(int windowId, int slotId, int mouseButton, int type, EntityPlayer player) {
        return mc.playerController.windowClick(windowId, slotId, mouseButton, type, player);
    }

    @Override
    public void setGameType(GameType type) {
        mc.playerController.setGameType(type);
    }

    @Override
    public GameType getGameType() {
        return mc.playerController.getCurrentGameType();
    }
}
