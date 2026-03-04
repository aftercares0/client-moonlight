/*
 * This file is part of ViaMCP - https://github.com/FlorianMichael/ViaMCP
 * Copyright (C) 2020-2023 FlorianMichael/EnZaXD and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.florianmichael.viamcp.gui;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.protocolinfo.ProtocolInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;

public class GuiProtocolSelector extends GuiScreen {
    private final GuiScreen parent;
    public SlotList list;

    public GuiProtocolSelector(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1, width / 2 - 100, height - 25, 200, 20, "Back"));
        list = new SlotList(mc, width, height, 32, height - 32);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) throws IOException {
        list.actionPerformed(guiButton);

        if (guiButton.id == 1) {
            mc.displayGuiScreen(parent);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        list.handleMouseInput();
        super.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        
    }

    class SlotList extends GuiSlot {

        public SlotList(Minecraft mc, int width, int height, int top, int bottom) {
            super(mc, width, height, top + 30, bottom, 18);
        }

        @Override
        protected int getSize() {
            return ViaLoadingBase.getProtocols().size();
        }

        @Override
        protected void elementClicked(int i, boolean b, int i1, int i2) {
            final ProtocolVersion protocolVersion = ViaLoadingBase.getProtocols().get(i);
            ViaLoadingBase.getInstance().reload(protocolVersion);
        }

        @Override
        protected boolean isSelected(int i) {
            return false;
        }

        @Override
        protected void drawBackground() {
            drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5) {
            
        }
    }
}
