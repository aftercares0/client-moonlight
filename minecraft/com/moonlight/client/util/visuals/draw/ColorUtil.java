package com.moonlight.client.util.visuals.draw;

import java.awt.Color;

import com.moonlight.client.font.basic.MinecraftFontRenderer;

public class ColorUtil {

	public static int getRainbowWave(int speed, int offset, float saturation, float brightness) {
		float hue = ((System.currentTimeMillis() + offset) % speed) / (float) speed;
		return Color.getHSBColor(hue, saturation, brightness).getRGB();
	}

	public static int getColorWave(int speed, int offset, Color color) {
		return Color.getHSBColor(getHue(color), getSaturation(color), calculateBrightness(speed, offset)).getRGB();
	}

	public static void drawRainbowText(MinecraftFontRenderer font, String text, int x, int y, int speed, float saturation, float brightness) {
        font.drawString(text, x, y, getRainbowWave(speed, 0, saturation, brightness));
	}
	
	public static float calculateBrightness(int speed, int offset) {
		float minBrightness = 0.7f;
		float maxBrightness = 1f;

		float brightnessRange = maxBrightness - minBrightness;

		float sinePosition = (float) Math.sin((System.currentTimeMillis() + offset) * 2 * Math.PI / speed);

		float currentBrightness = minBrightness + (sinePosition + 1) * 0.5f * brightnessRange;

		return currentBrightness;
	}

	public static float getSaturation(Color color) {
		float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
		return hsbValues[1];
	}

	public static float getHue(Color color) {
		float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
		return hsbValues[0];
	}

	public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
		int angle = (int) (((System.currentTimeMillis()) / speed + index) % 360);
		angle = (angle >= 180 ? 360 - angle : angle) * 2;
		return trueColor ? ColorUtil.interpolateColorHue(start, end, angle / 360f) : ColorUtil.interpolateColorC(start, end, angle / 360f);
	}

	public static Color interpolateColorC(Color color1, Color color2, float amount) {
		amount = Math.min(1, Math.max(0, amount));
		return new Color(interpolateInt(color1.getRed(), color2.getRed(), amount),
				interpolateInt(color1.getGreen(), color2.getGreen(), amount),
				interpolateInt(color1.getBlue(), color2.getBlue(), amount),
				interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
	}

	public static Color interpolateColorHue(Color color1, Color color2, float amount) {
		amount = Math.min(1, Math.max(0, amount));

		float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
		float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);

		Color resultColor = Color.getHSBColor(interpolateFloat(color1HSB[0], color2HSB[0], amount),
				interpolateFloat(color1HSB[1], color2HSB[1], amount), interpolateFloat(color1HSB[2], color2HSB[2], amount));

		return ColorUtil.applyOpacity(resultColor, interpolateInt(color1.getAlpha(), color2.getAlpha(), amount) / 255f);
	}

	private static Double interpolate(double oldValue, double newValue, double interpolationValue){
		return (oldValue + (newValue - oldValue) * interpolationValue);
	}

	private static float interpolateFloat(float oldValue, float newValue, double interpolationValue){
		return interpolate(oldValue, newValue, (float) interpolationValue).floatValue();
	}

	private static int interpolateInt(int oldValue, int newValue, double interpolationValue){
		return interpolate(oldValue, newValue, (float) interpolationValue).intValue();
	}

	public static int applyOpacity(int color, float opacity) {
		Color old = new Color(color);
		return applyOpacity(old, opacity).getRGB();
	}

	public static Color applyOpacity(Color color, float opacity) {
		opacity = Math.min(1, Math.max(0, opacity));
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * opacity));
	}

}
