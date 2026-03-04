package com.moonlight.client.alt;

import com.moonlight.client.alt.microsoft.GuiLoginMicrosoft;
import com.moonlight.client.alt.mojang.GuiLoginMojang;
import com.moonlight.client.shader.ShaderInstance;
import com.moonlight.client.ui.MainMenu;
import com.moonlight.client.util.RandomUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class AltManagerGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	ShaderInstance.mainMenuShader.render(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, width / 2 + 4 + 50, height - 24, 100, 20, "Cancel"));
        this.buttonList.add(new GuiButton(1, width / 2 + 4 + 50, height - 48, 100, 20, "Use Cracked"));
        this.buttonList.add(new GuiButton(2, width / 2 - 50, height - 48, 100, 20, "Use Microsoft"));
        this.buttonList.add(new GuiButton(3, width / 2 - 150 - 4, height - 48, 100, 20, "Use Mojang"));
        this.buttonList.add(new GuiButton(4, width / 2 - 50, height - 24, 100, 20, "Random Cracked"));
        this.buttonList.add(new GuiButton(5, width / 2 - 150 - 4, height - 24, 100, 20, "Coming Soon..."));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(new MainMenu());
        }
        if(button.id == 1){
            mc.displayGuiScreen(new GuiLogin());
        }
        if(button.id == 2){
            mc.displayGuiScreen(new GuiLoginMicrosoft());
        }
        if(button.id == 3){
            mc.displayGuiScreen(new GuiLoginMojang());
        }
        if(button.id == 4) {
    		SessionChanger.getInstance().setUserOffline(RandomUtils.generateRandomName(System.currentTimeMillis()));
        }
    }
}
