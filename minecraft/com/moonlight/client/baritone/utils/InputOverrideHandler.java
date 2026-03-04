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

package com.moonlight.client.baritone.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInputFromOptions;

import java.util.HashMap;
import java.util.Map;

import com.moonlight.client.Client;
import com.moonlight.client.baritone.Baritone;
import com.moonlight.client.baritone.api.BaritoneAPI;
import com.moonlight.client.baritone.api.IBaritone;
import com.moonlight.client.baritone.api.event.events.RenderEvent;
import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.baritone.api.utils.IInputOverrideHandler;
import com.moonlight.client.baritone.api.utils.input.Input;
import com.moonlight.client.baritone.behavior.Behavior;
import com.moonlight.client.component.impl.BaritoneComponent;
import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.render.Render3DEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.strafe.StrafeEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.player.MoveUtil;

/**
 * An interface with the game's control system allowing the ability to
 * force down certain controls, having the same effect as if we were actually
 * physically forcing down the assigned key.
 *
 * @author Brady
 * @since 7/31/2018
 */
public final class InputOverrideHandler extends Behavior implements IInputOverrideHandler {

    /**
     * Maps inputs to whether or not we are forcing their state down.
     */
    private final Map<Input, Boolean> inputForceStateMap = new HashMap<>();

    private final BlockBreakHelper blockBreakHelper;

    public InputOverrideHandler(Baritone baritone) {
        super(baritone);
        this.blockBreakHelper = new BlockBreakHelper(baritone.getPlayerContext());
        Client.EVENT_BUS.register(this);
    }

    /**
     * Returns whether or not we are forcing down the specified {@link KeyBinding}.
     *
     * @param key The KeyBinding object
     * @return Whether or not it is being forced down
     */
    @Override
    public final Boolean isInputForcedDown(KeyBinding key) {
        Input input = Input.getInputForBind(key);
        if (input == null) {
            return null;
        }
        if (input == Input.CLICK_LEFT && inControl()) {
            // only override left click off when pathing
            return false;
        }
        if (input == Input.CLICK_RIGHT) {
            if (isInputForcedDown(Input.CLICK_RIGHT)) {
                // gettoblock and builder can right click even when not pathing; allow them to do so
                return true;
            } else if (inControl()) {
                // but when we are pathing for real, force right click off
                return false;
            }
        }
        return null; // dont force any inputs other than left and right click
    }

    /**
     * Returns whether or not we are forcing down the specified {@link Input}.
     *
     * @param input The input
     * @return Whether or not it is being forced down
     */
    @Override
    public final boolean isInputForcedDown(Input input) {
        return input == null ? false : this.inputForceStateMap.getOrDefault(input, false);
    }

    /**
     * Sets whether or not the specified {@link Input} is being forced down.
     *
     * @param input  The {@link Input}
     * @param forced Whether or not the state is being forced
     */
    @Override
    public final void setInputForceState(Input input, boolean forced) {
        this.inputForceStateMap.put(input, forced);
    }

    /**
     * Clears the override state for all keys
     */
    @Override
    public final void clearAllKeys() {
        this.inputForceStateMap.clear();
    }
    
    @Override
    public final void onTick(TickEvent event) {
        if (event.getType() == TickEvent.Type.OUT) {
            return;
        }
        blockBreakHelper.tick(isInputForcedDown(Input.CLICK_LEFT));

        if (inControl()) {
            if (ctx.player().movementInput.getClass() != PlayerMovementInput.class) {
                ctx.player().movementInput = new PlayerMovementInput(this);
            }
        } else {
            if (ctx.player().movementInput.getClass() == PlayerMovementInput.class) { // allow other movement inputs that aren't this one, e.g. for a freecam
                ctx.player().movementInput = new MovementInputFromOptions(Minecraft.getMinecraft().gameSettings);
            }
        }
        // only set it if it was previously incorrect
        // gotta do it this way, or else it constantly thinks you're beginning a double tap W sprint lol
    }
    
    @EventLink
	public void onRender3D(Render3DEvent event) {
		if(inControl()) {
			for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
	            ibaritone.getGameEventHandler().onRenderPass(new RenderEvent(1.0F));
	        }
		}
	}
    
    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	BaritoneComponent.isActive = inControl();
    }
    	
	@EventLink
	public void onStrafe(StrafeEvent event) {
	}
	
	@EventLink
	public void onMoveInput(MoveInputEvent event) {
//		if(inControl()) {
//			MoveUtil.fixMovement(event, MinecraftInstance.mc.thePlayer.rotationYaw);
//		}
	}

    private boolean inControl() {
        return baritone.getPathingBehavior().isPathing() || baritone != BaritoneAPI.getProvider().getPrimaryBaritone();
    }

    public BlockBreakHelper getBlockBreakHelper() {
        return blockBreakHelper;
    }
}
