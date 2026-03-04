package com.moonlight.client.ui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.moonlight.client.Client;
import com.moonlight.client.alt.AltManagerGui;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.shader.ShaderInstance;
import com.moonlight.client.util.visuals.draw.RenderUtil;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class MainMenu extends GuiScreen {
	
	private ArrayList<MainMenuButton> buttons = new ArrayList<>();
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);
        width = sr.getScaledWidth();
        height = sr.getScaledHeight();

//        RenderUtil.drawPicture(0, 0,
//                width, height, 0, "login-background.png");
        ShaderInstance.mainMenuShader.render(mouseX, mouseY);
        GlStateManager.disableBlend();
		GL11.glColor4f(0, 0, 0, 0);
		
		float outlineImgWidth = 688 / 2.3f;
        float outlineImgHeight = 681 / 2.5f;
		
        Fonts.productSans40.drawCenteredString("Moonlight", width / 2F, (height / 2f - outlineImgHeight / 2) + 50, new Color(0,0,0,120).getRGB());
        Fonts.productSans40.drawCenteredString("Moonlight", width / 2F, (height / 2f - outlineImgHeight / 2) + 50, -1);
        
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        RenderUtil.color(-1);
       
        RenderUtil.drawPicture(width / 2f - outlineImgWidth / 2f, height / 2f - outlineImgHeight / 2f,
                outlineImgWidth, outlineImgHeight, 0, "rect.png");
        GlStateManager.disableBlend();
        
        buttons.forEach(b -> {
        	b.drawButton(mouseX, mouseY);
        });
	}
	
	@Override
	public void initGui() {
		float outlineImgWidth = 688 / 2.3f;
        float outlineImgHeight = 681 / 2.5f;
        int y = (int) ((height / 2f - outlineImgHeight / 2) + 80);
        buttons.clear();
        buttons.add(new MainMenuButton("Singleplayer", (width / 2) - (210 / 2), y, 210, 25));
        buttons.add(new MainMenuButton("Multiplayer", (width / 2) - (210 / 2), y + 30, 210, 25));
        buttons.add(new MainMenuButton("Options", (width / 2) - (210 / 2), y + 60, 210, 25));
        buttons.add(new MainMenuButton("Alt Manager", (width / 2) - (210 / 2), y + 90, 210, 25));
	}  
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		buttons.forEach(b -> {
        	if(b.isHover(mouseX, mouseY) && mouseButton == 0) {
        		switch (b.getName()) {
        			case "Singleplayer":
        				mc.displayGuiScreen(new GuiSelectWorld(this));
        				break;
        			case "Multiplayer":
        				mc.displayGuiScreen(new GuiMultiplayer(this));
        				break;
        			case "Options":
        				mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        				break;
        			case "Alt Manager":
        				mc.displayGuiScreen(new AltManagerGui());
        				break;
        		}
        	}
        });
	}
	
}
