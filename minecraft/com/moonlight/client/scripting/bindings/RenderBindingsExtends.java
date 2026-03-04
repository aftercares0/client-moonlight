package com.moonlight.client.scripting.bindings;

import org.lwjgl.opengl.GL11;

import com.moonlight.client.scripting.bindings.impl.RenderBindings;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.visuals.draw.RenderUtil;

public class RenderBindingsExtends implements RenderBindings {

	@Override
	public void color(int arg0) {
		RenderUtil.color(arg0);
	}

	@Override
	public void color3f(float arg0, float arg1, float arg2) {
		GL11.glColor3f(arg0, arg1, arg2);
	}

	@Override
	public void color4f(float arg0, float arg1, float arg2, float arg3) {
		GL11.glColor4f(arg0, arg1, arg2, arg3);
	}

	@Override
	public void drawRect(float arg0, float arg1, float arg2, float arg3, int arg4) {
		RenderUtil.drawRect(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void drawRoundedRect(float arg0, float arg1, float arg2, float arg3, int arg4, int arg5) {
		RenderUtil.drawRoundedRect(arg0, arg1, arg2, arg3, arg4, arg5, 0);
	}

	@Override
	public void glDisable(int arg0) {
		GL11.glDisable(arg0);
	}

	@Override
	public void glEnable(int arg0) {
		GL11.glEnable(arg0);
	}

	@Override
	public void popMatrix() {
		GL11.glPopMatrix();
	}

	@Override
	public void pushMatrix() {
		GL11.glPushMatrix();
	}

	@Override
	public void drawText(String arg0, float arg1, float arg2, int color) {
		MinecraftInstance.mc.fontRendererObj.drawString(arg0, (int) arg1, (int) arg2, color, true);
	}

}
