package com.moonlight.client.ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.font.Fonts;

public class TextBox {
    public void setText(String text) {
        this.text = text;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public void setAnimatedCursorPosition(double animatedCursorPosition) {
        this.animatedCursorPosition = animatedCursorPosition;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLastTime(double lastTime) {
        this.lastTime = lastTime;
    }

    public void setLastBackSpace(double lastBackSpace) {
        this.lastBackSpace = lastBackSpace;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHideCharacters(boolean hideCharacters) {
        this.hideCharacters = hideCharacters;
    }

    public String text = "";

    public Vector2f position;

    public boolean selected;

    public int cursor;

    public double animatedCursorPosition;

    public Color color;

    private double lastTime;

    private double lastBackSpace;

    private double posX;

    private String emptyText;

    private float width;

    private boolean hideCharacters;

    public String getText() {
        return this.text;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public int getCursor() {
        return this.cursor;
    }

    public double getAnimatedCursorPosition() {
        return this.animatedCursorPosition;
    }

    public Color getColor() {
        return this.color;
    }

    public double getLastTime() {
        return this.lastTime;
    }

    public double getLastBackSpace() {
        return this.lastBackSpace;
    }

    public double getPosX() {
        return this.posX;
    }

    public String getEmptyText() {
        return this.emptyText;
    }

    public float getWidth() {
        return this.width;
    }

    public boolean isHideCharacters() {
        return this.hideCharacters;
    }

    public TextBox(Vector2f position, Color color, String emptyText, float width, boolean hideCharacters) {
        this.position = position;
        this.color = color;
        this.emptyText = emptyText;
        this.width = width;
        this.hideCharacters = hideCharacters;
    }

    public TextBox(Vector2f position, Color color, String emptyText, float width) {
        this.position = position;
        this.color = color;
        this.emptyText = emptyText;
        this.width = width;
        this.hideCharacters = false;
    }

    public void draw() {
        int speed;
        this.cursor = Math.min(Math.max(this.cursor, 0), this.text.length());
        Keyboard.enableRepeatEvents(true);
        StringBuilder drawnString = new StringBuilder(this.text);
        if (this.hideCharacters && !isEmpty()) {
            StringBuilder string = new StringBuilder();
            for (int m = 0; m < drawnString.length(); m++)
                string.append("*");
            drawnString = new StringBuilder(string);
        }
        double time = System.currentTimeMillis();
        double difference = Math.abs(time - this.lastTime);
        this.lastTime = time;
        difference = Math.min(difference, 500.0D);

        this.posX = this.position.x;

        if (isEmpty()) {
            Fonts.productSans18.drawString(this.emptyText, this.posX, this.position.y, (new Color(this.color.getRed(), this.color
                    .getBlue(), this.color.getGreen(), (int)(this.color.getAlpha() * (this.selected ? 1F : 0.8F)))).hashCode());
        } else {
            Fonts.productSans18.drawString(drawnString.toString(), this.posX, this.position.y, this.color.hashCode());
        }
        this.cursor = Math.min(Math.max(this.cursor, 0), drawnString.length());
        StringBuilder textBeforeCursor = new StringBuilder();
        for (int i = 0; i < this.cursor; i++)
            textBeforeCursor.append(drawnString.charAt(i));
        float cursorOffset = Fonts.productSans18.getStringWidth(textBeforeCursor.toString());
        int j = 20;
        for (int k = 0; k < difference; k++)
            this.animatedCursorPosition = (this.animatedCursorPosition * 19.0D + (cursorOffset - 2.0F)) / 20.0D;
        if (this.selected)
            Fonts.productSans18.drawString("|", (double) (this.posX + this.animatedCursorPosition + 1.0D), (float) (this.position.y - 1.0D), (new Color(this.color.getRed(), this.color.getBlue(), this.color.getGreen(), (this.color.getAlpha() == 0) ? 0 : (int)((Math.sin(System.currentTimeMillis() / 150.0D) + 1.0D) / 2.0D * 255.0D))).hashCode());
    }

    public void click(int mouseX, int mouseY, int mouseButton) {
        Vector2f position = getPosition();
        this.selected = (mouseButton == 0 && mouseOver(position.x + ((-this.width / 2.0F)), position.y, this.width, Fonts.productSans18.getHeight()
                , mouseX, mouseY));
    }

    private boolean mouseOver(float x, float y, float width, int height, int mouseX, int mouseY) {
        if(mouseX > x && mouseX <= x + width && mouseY > y && mouseY <= y + height) {
            return true;
        }

        return false;
    }

    public void key(char typedChar, int keyCode) {
        if (!this.selected)
            return;
        String character = String.valueOf(typedChar);
        this.cursor = Math.min(Math.max(this.cursor, 0), this.text.length());
        if (Keyboard.isKeyDown(29) && keyCode == 47) {
            try {
                String clipboard = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                addText(clipboard, this.cursor);
                this.cursor += clipboard.length();
            } catch (UnsupportedFlavorException|java.io.IOException e) {
                e.printStackTrace();
            }
        } else if ("abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()-_+=[{]};:.>,</?| ".contains(character.toLowerCase())) {
            addText(character, this.cursor);
            this.cursor++;
        } else if (keyCode == 211 && !this.text.isEmpty()) {
            removeText(this.cursor);
        } else if (keyCode == 14 && !this.text.isEmpty()) {
            removeText(this.cursor);
            this.cursor--;
            if (Keyboard.isKeyDown(29))
                while (!this.text.isEmpty() && 0 < this.cursor) {
                    removeText(this.cursor);
                    this.cursor--;
                }
        } else if (keyCode == 205) {
            this.cursor++;
            if (Keyboard.isKeyDown(29))
                while (this.text.length() > this.cursor)
                    this.cursor++;
        } else if (keyCode == 203) {
            this.cursor--;
            if (Keyboard.isKeyDown(29))
                while (0 < this.cursor)
                    this.cursor--;
        }
        this.cursor = Math.min(Math.max(this.cursor, 0), this.text.length());
    }

    private void addText(String text, int position) {
        if (Fonts.productSans18.getStringWidth(this.text + text) <= this.width) {
            StringBuilder newText = new StringBuilder();
            boolean append = false;
            for (int i = 0; i < this.text.length(); i++) {
                String character = String.valueOf(this.text.charAt(i));
                if (i == position) {
                    append = true;
                    newText.append(text);
                }
                newText.append(character);
            }
            if (!append)
                newText.append(text);
            this.text = newText.toString();
        }
    }

    private void removeText(int position) {
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < this.text.length(); i++) {
            String character = String.valueOf(this.text.charAt(i));
            if (i != position - 1)
                newText.append(character);
        }
        this.text = newText.toString();
    }

    public boolean isEmpty() {
        return (this.text.isEmpty() || this.text.equals(" "));
    }
}
