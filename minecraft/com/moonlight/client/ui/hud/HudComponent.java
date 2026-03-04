package com.moonlight.client.ui.hud;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public abstract class HudComponent {

	protected double x, y, dragX, dragY;
	protected boolean drag, isDeletedable, isDraggable;
	
	public HudComponent(double x, double y) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

		this.x = x;
		this.y = y;
		
		this.isDeletedable = true;
	}

	public abstract void render(int mouseX, int mouseY);
	
	public boolean isDeletedable() {
		return isDeletedable;
	}
	
	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDragX() {
		return (int) dragX;
	}

	public void setDragX(int dragX) {
		this.dragX = dragX;
	}

	public int getDragY() {
		return (int) dragY;
	}

	public void setDragY(int dragY) {
		this.dragY = dragY;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}
	
	public List<Value> getValues() {
        final CopyOnWriteArrayList<Value> values = new CopyOnWriteArrayList<>();

        for (final Field field : getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                final Object o = field.get(this);

                if (o instanceof Value) {
                    values.add((Value) o);
                }
            } catch (IllegalAccessException e) {
                //Do nothing
            }
        }

        return values;
    }
	
	public abstract int getWidth();
	public abstract int getHeight();
	
	public abstract void click(int mouseX, int mouseY, int mouseButton);
	public abstract void key(char typedChar, int keyCode);
	
}
