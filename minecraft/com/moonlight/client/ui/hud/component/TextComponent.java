package com.moonlight.client.ui.hud.component;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.Client;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.font.basic.MinecraftFontRenderer;
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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;

public class TextComponent extends HudComponent implements MinecraftInstance {

	private String text;
	private int clickCount;
	private boolean isFocused;
	
	private int calculatedWidth, calculatedHeight;
	
	private NumberValue<Integer> red = new NumberValue("Red", Client.MAIN_COLOR.getRed(), 0, 255);
	private NumberValue<Integer> green = new NumberValue("Green", Client.MAIN_COLOR.getGreen(), 0, 255);
	private NumberValue<Integer> blue = new NumberValue("Blue", Client.MAIN_COLOR.getBlue(), 0, 255);
	
	private BooleanValue isRainbow = new BooleanValue("Rainbow", false);
	
	private BooleanValue background = new BooleanValue("Background", false);
	private NumberValue<Integer> bred = new NumberValue("Back R", 0, 0, 255);
	private NumberValue<Integer> bgreen = new NumberValue("Back G", 0, 0, 255);
	private NumberValue<Integer> bblue = new NumberValue("Back B", 0, 0, 255);
	private NumberValue<Integer> balpha = new NumberValue("Back B", 80, 0, 255);
	
	private ModeValue fontMode = new ModeValue("Font", null, "Minecraft","Product Sans 16", 
			"Product Sans 18", "Product Sans 25", "Product Sans 30", "Product Sans 40", "SF UI 40", "SF UI 30", "SF UI 25", "SF UI 18", "SF UI 16",
			"Comfortaa 16", "Comfortaa 18", "Comfortaa 25", "Comfortaa 30", "Comfortaa 40");
	
	public TextComponent() {
		super(50, 50);
		
		text = "Hello world";
	}
	
	public TextComponent(String text, int x, int y, Color color) {
		super(x, y);
		
		this.text = text;
		this.red.setValue(color.getRed());
		this.green.setValue(color.getGreen());
		this.blue.setValue(color.getBlue());
	}

	@Override
	public void render(int mouseX, int mouseY) {
		int color = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
		int backColor = new Color(bred.getValue(), bgreen.getValue(), bblue.getValue(), balpha.getValue()).getRGB();
		
		if(isFocused || isDrag()) {
			if(!background.getState()) {
				RenderUtil.drawBorderedRect(getX(), getY(), getWidth(), getHeight(), 1, Color.white.getRGB(), new Color(0,0,0,0).getRGB());
			}else {
				RenderUtil.drawBorderedRect(getX() - 5, getY() - 5, getWidth(), getHeight(), 1, Color.white.getRGB(), new Color(0,0,0,0).getRGB());
			}
		}
			
		if(mc.currentScreen == null) {
			isFocused = false;
			setDrag(false);
		}
		
		String actualText = isFocused ? text + "_" : text;
		
		if(!fontMode.getMode().getName().contains("Minecraft")) {
			MinecraftFontRenderer font = Fonts.getFontByName(fontMode.getMode().getName());
			
			if(!background.getState()) {
				if(!isRainbow.getState()) {
					font.drawString(actualText, getX(), getY(), color);
				}else {
					ColorUtil.drawRainbowText(font, actualText, getX(), getY(), 3000, 0.6f, 0.8f);
				}
				calculatedWidth = font.getStringWidth(actualText);
				calculatedHeight = font.getHeight();
			}else {
				int width = font.getStringWidth(actualText);
				
				RenderUtil.drawRect(getX() - 5, getY() - 5, width + 10, font.getHeight() + 10, backColor);
				if(!isRainbow.getState()) {
					font.drawString(actualText, getX(), getY(), color);
				}else {
					ColorUtil.drawRainbowText(font, actualText, getX(), getY(), 3000, 0.6f, 0.8f);
				}
				
				calculatedWidth = width + 10;
				calculatedHeight = font.getHeight() + 10;
			}
		}else {			
			calculatedWidth = (int) (mc.fontRendererObj.getStringWidth(actualText));
			calculatedHeight = (int) (mc.fontRendererObj.FONT_HEIGHT);
			
			if(!background.getState()) {
				mc.fontRendererObj.drawString(actualText, getX(), getY(), color, true);
			}else {
				int width = mc.fontRendererObj.getStringWidth(actualText);
				
				RenderUtil.drawRect(getX() - 5, getY() - 5, width + 10, mc.fontRendererObj.FONT_HEIGHT + 10, backColor);
				mc.fontRendererObj.drawString(text, getX(), getY(), color);
				
				calculatedWidth = (int) (mc.fontRendererObj.getStringWidth(actualText)) + 10;
				calculatedHeight = (int) (mc.fontRendererObj.FONT_HEIGHT) + 10;
			}
		}
	}
	
	@Override
	public void click(int mouseX, int mouseY, int mouseButton) {
		if(GuiUtil.isHover(mouseX, mouseY, getX(), getY(), getWidth(), getHeight()) && mouseButton == 0) {
			clickCount++;
			
			if(isDrag()) clickCount = 0;
			
			if(clickCount >= 2) {
				isFocused = !isFocused;
				setDrag(false);
				clickCount = 0;
			}
		}else if(!GuiUtil.isHover(mouseX, mouseY, getX(), getY(), getWidth(), getHeight()) && mouseButton == 0) {
			clickCount = 0;
			isFocused = false;
		}else if(GuiUtil.isHover(mouseX, mouseY, getX(), getY(), getWidth(), getHeight()) && mouseButton == 1) {
			mc.displayGuiScreen(new CustomizeComponentScreen(this));
			isFocused = false;
			clickCount = 0;
		}
	}

	@Override
	public void key(char typedChar, int keyCode) {
		if(!isFocused) return;
		
		if(keyCode == Keyboard.KEY_ESCAPE) {
			isFocused = false;
		}
		
		if(keyCode == Keyboard.KEY_BACK && text.length() > 0) {
			text = text.substring(0, text.length() - 1);
		}
		
		if(ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
			text = text + typedChar;
		}
	}

	@Override
	public int getWidth() {
		return calculatedWidth;
	}

	@Override
	public int getHeight() {
		return calculatedHeight;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
