package com.moonlight.client.module.impl.visuals;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.font.basic.MinecraftFontRenderer;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.visuals.arraylist.NewArrayList;
import com.moonlight.client.module.impl.visuals.arraylist.OldCustomizeableArrayList;
import com.moonlight.client.shader.ShaderInstance;
import com.moonlight.client.ui.hud.HudComponent;
import com.moonlight.client.ui.hud.gui.CustomizeScreen;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

@ModuleInfo(name = "Watermark", description = "the true watermark" , category = Category.VISUALS, autoEnabled = true)
public class Watermark extends Module {
		
	private ModeValue mode = new ModeValue("Mode", this, "New", "Image");
	
	@EventLink
	public void onRender2D(Render2DEvent event) {		
		MinecraftFontRenderer font = Fonts.comfortaaNew18;
		
        if(mode.getMode().getName().equalsIgnoreCase("Image")) {
        	GlStateManager.color(1,1,1,1);
    		
    		RenderUtil.drawPicture(-15, -15, 100, 100, 0, "logo_big.png");
        }else if(mode.getMode().getName().equalsIgnoreCase("New")) {
        	//RenderUtil.drawRRect(5, 3, 60, 13, new Color(0,0,0,80).getRGB());
        	RenderUtil.drawRoundedRect(5, 3, 60, 13, 5, new Color(0,0,0,80).getRGB(), 0);
        	
        	int x = 5 + (60 / 2) - (font.getStringWidth("moonlight " + Client.VERSION) / 2);
        	int y = 3 + (13 / 2) - (font.getHeight() / 2);
        	
        	font.drawString("moonlight " + Client.VERSION, x + 0.2f, y + 0.3f, new Color(0,0,0,80).getRGB());
			font.drawString("moonlight " + Client.VERSION, x, y, new Color(46, 67, 116).brighter().brighter().getRGB());
        }
	}
	
}
