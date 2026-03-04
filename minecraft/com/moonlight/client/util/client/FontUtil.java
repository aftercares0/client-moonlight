package com.moonlight.client.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.moonlight.client.font.basic.MinecraftFontRenderer;

public class FontUtil {

    public static Font getFontByLocation(String location, int size) {
        Font font = null;

        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("moonlight/fonts/" + location)).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception e) {
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, +10);
        }

        return font;
    }


    private static Font getFontByLocationByType(String location, int size, int type) {
        Font font = null;

        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("moonlight/fonts/" + location)).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(type, size);
        } catch (Exception e) {
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, +10);
        }

        return font;
    }
    private static Font getFontByLocation(String location, int size, int type) {
        Font font = null;

        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("moonlight/fonts/" + location)).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception e) {
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, +10);
        }

        return font;
    }

    public static MinecraftFontRenderer getFont(String font, int size) {
        MinecraftFontRenderer fontT = null;
        Font fontF = null;
        fontF = getFontByLocation(font, size);
        fontT = new MinecraftFontRenderer(fontF, true, true);

        return fontT;
    }

    public static MinecraftFontRenderer getFontWithType(String font, int size, int type) {
        MinecraftFontRenderer fontT = null;
        Font fontF = null;
        fontF = getFontByLocation(font, size);
        fontT = new MinecraftFontRenderer(fontF, true, true);

        return fontT;
    }

    public static MinecraftFontRenderer getFont(String font, int size, int type) {
        MinecraftFontRenderer fontT = null;
        Font fontF = null;
        fontF = getFontByLocation(font, size, type);
        fontT = new MinecraftFontRenderer(fontF, true, true);

        return fontT;
    }

}
