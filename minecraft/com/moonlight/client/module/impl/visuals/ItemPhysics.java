package com.moonlight.client.module.impl.visuals;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.shader.ShaderInstance;
import com.moonlight.client.ui.hud.HudComponent;
import com.moonlight.client.ui.hud.gui.CustomizeScreen;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.gui.ScaledResolution;

@ModuleInfo(name = "ItemPhysics", description = "give an item physics" , category = Category.VISUALS, autoEnabled = true)
public class ItemPhysics extends Module {
		
	@EventLink
	public void onRender2D(Render2DEvent event) {		
	}
	
}
