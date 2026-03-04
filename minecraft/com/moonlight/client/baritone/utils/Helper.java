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

import com.moonlight.client.baritone.Baritone;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.TextFormatting;

/**
 * @author Brady
 * @since 8/1/2018
 */
public interface Helper {

    /**
     * Instance of {@link Helper}. Used for static-context reference.
     */
    Helper HELPER = new Helper() {};

    IChatComponent MESSAGE_PREFIX = new ChatComponentText(String.format(
            "%s[%sBaritone%s]%s",
            TextFormatting.DARK_PURPLE,
            TextFormatting.LIGHT_PURPLE,
            TextFormatting.DARK_PURPLE,
            TextFormatting.GRAY
    ));

    Minecraft mc = Minecraft.getMinecraft();

    /**
     * Send a message to chat only if chatDebug is on
     *
     * @param message The message to display in chat
     */
    default void logDebug(String message) {
        if (!Baritone.settings().chatDebug.get()) {
            //System.out.println("Suppressed debug message:");
            //System.out.println(message);
            return;
        }
        logDirect(message);
    }

    /**
     * Send a message to chat regardless of chatDebug (should only be used for critically important messages, or as a direct response to a chat command)
     *
     * @param message The message to display in chat
     */
    default void logDirect(String message) {
    	IChatComponent component = MESSAGE_PREFIX.createCopy();
        component.getChatStyle().setColor(TextFormatting.GRAY);
        component.appendSibling(new ChatComponentText(" " + message));
        Minecraft.getMinecraft().addScheduledTask(() -> Baritone.settings().logger.get().accept(component));
    }
}
