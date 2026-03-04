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

import com.moonlight.client.baritone.api.BaritoneAPI;
import com.moonlight.client.baritone.api.cache.IWorldData;
import com.moonlight.client.baritone.api.utils.IPlayerContext;
import com.moonlight.client.baritone.api.utils.IPlayerController;
import com.moonlight.client.baritone.utils.Helper;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Implementation of {@link IPlayerContext} that provides information about the primary player.
 *
 * @author Brady
 * @since 11/12/2018
 */
public enum PrimaryPlayerContext implements IPlayerContext, Helper {

    INSTANCE;

    @Override
    public EntityPlayerSP player() {
        return mc.thePlayer;
    }

    @Override
    public IPlayerController playerController() {
        return PrimaryPlayerController.INSTANCE;
    }

    @Override
    public World world() {
        return mc.theWorld;
    }

    @Override
    public IWorldData worldData() {
        return BaritoneAPI.getProvider().getPrimaryBaritone().getWorldProvider().getCurrentWorld();
    }

    @Override
    public MovingObjectPosition objectMouseOver() {
        return mc.objectMouseOver;
    }
}
