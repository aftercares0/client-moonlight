package com.moonlight.client.module.impl.visuals;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.font.basic.MinecraftFontRenderer;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.visuals.Interface;
import com.moonlight.client.module.impl.visuals.arraylist.NewArrayList;
import com.moonlight.client.module.impl.visuals.arraylist.OldCustomizeableArrayList;
import com.moonlight.client.module.impl.visuals.arraylist.VapeArrayList;
import com.moonlight.client.ui.hud.HudComponent;
import com.moonlight.client.ui.hud.gui.CustomizeComponentScreen;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.draw.ColorUtil;
import com.moonlight.client.util.visuals.draw.RenderUtil;
import com.moonlight.client.util.visuals.gui.GuiUtil;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;

@ModuleInfo(name = "Arraylist", description = "render arraylist" , category = Category.VISUALS, autoEnabled = true)
public class ArraylistModule extends com.moonlight.client.module.api.Module {

	private ModeValue mode = new ModeValue("Mode", this, new NewArrayList(this, "New"), new VapeArrayList(this, "Vape"), new OldCustomizeableArrayList(this, "OldCustom"));

}
