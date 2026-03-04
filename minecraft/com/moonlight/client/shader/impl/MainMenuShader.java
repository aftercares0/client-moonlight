package com.moonlight.client.shader.impl;

import com.moonlight.client.shader.base.ShaderBase;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.shader.ShaderUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class MainMenuShader extends ShaderBase {

	private final int timeUniform;
    private final int mouseUniform;
    private final int resolutionUniform;
    private long initTime = System.currentTimeMillis();
     
	public MainMenuShader() {
		super("mainmenu.fsh", "backvertex.vsh");
		
        int linked = glGetProgrami(getProgramId(), GL_LINK_STATUS);

        // If linking failed
        if (linked == 0) {
            System.err.println(glGetProgramInfoLog(getProgramId(), glGetProgrami(getProgramId(), GL_INFO_LOG_LENGTH)));

            throw new IllegalStateException("Shader failed to link");
        }

        // Setup uniforms
        glUseProgram(getProgramId());

        this.timeUniform = glGetUniformLocation(getProgramId(), "time");
        this.mouseUniform = glGetUniformLocation(getProgramId(), "mouse");
        this.resolutionUniform = glGetUniformLocation(getProgramId(), "resolution");

        glUseProgram(0);
	}

	@Override
	public void render(int mouseX, int mouseY) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		float width = sr.getScaledWidth(), height = sr.getScaledHeight();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		
		glUseProgram(getProgramId());

        glUniform2f(this.resolutionUniform, width, height);
        glUniform2f(this.mouseUniform, mouseX / width, 1.0f - mouseY / height);
        glUniform1f(this.timeUniform, (System.currentTimeMillis() - initTime) / 1000f);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-1f, -1f);
		GL11.glVertex2f(-1f, 1f);
		GL11.glVertex2f(1f, 1f);
		GL11.glVertex2f(1f, -1f);
		
		GL11.glEnd();
		
		GL20.glUseProgram(0);
		GlStateManager.enableCull();
		GlStateManager.disableAlpha();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
