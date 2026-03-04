package com.moonlight.client.shader.base;

import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.GL11;

import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.shader.ShaderUtil;

import net.minecraft.util.ResourceLocation;

public abstract class ShaderBase {

	private final int programId;
	
	public ShaderBase(String fragmentShader, String vertexShader) {
        this.programId = ShaderUtil.createShader(fragmentShader, vertexShader);
    }
	
	public int getProgramId() {
		return programId;
	}
	
	public abstract void render(int mouseX, int mouseY);
	
	public static void drawQuad(double x, double y, double width, double height) {
		GL11.glBegin(7);
	    GL11.glTexCoord2f(0.0F, 0.0F);
	    GL11.glVertex2d(x, y + height);
	    GL11.glTexCoord2f(1.0F, 0.0F);
	    GL11.glVertex2d(x + width, y + height);
	    GL11.glTexCoord2f(1.0F, 1.0F);
	    GL11.glVertex2d(x + width, y);
	    GL11.glTexCoord2f(0.0F, 1.0F);
	    GL11.glVertex2d(x, y);
	    GL11.glEnd();
	}
	
	public static void drawQuad() {
	    drawQuad(0.0D, 0.0D, MinecraftInstance.mc.displayWidth, MinecraftInstance.mc.displayHeight);
	}
}
