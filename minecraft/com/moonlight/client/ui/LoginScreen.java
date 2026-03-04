package com.moonlight.client.ui;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.lwjgl.opengl.Display;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.Client;
import com.moonlight.client.animation.fake.ColorFadeAnimate;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.protection.LoginProtectionHandler;
import com.moonlight.client.util.visuals.draw.RenderUtil;

public class LoginScreen extends GuiScreen {

	public static boolean lastRun = false;
	
    public boolean lastFailed;

    private LoginButton button;
    private TextBox box;

    public ColorFadeAnimate animate = new ColorFadeAnimate(new Color(255,255,255,0), 0);

    public boolean out = false;

    public LoginScreen() {
        button = new LoginButton("Login", 0, 0, 190, 20);
        box = new TextBox(new Vector2f(0, 0), Color.WHITE, "Username", 190);
        animate.updateLastValue(false);
        
        lastRun = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        RenderUtil.drawPicture(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 0, "login-background.png");

        Fonts.productSans30.drawCenteredString("Welcome", width / 2, ((sr.getScaledHeight() / 2) - (150 / 2)) + 150 - 100, -1);

        button.setX((sr.getScaledWidth() / 2) - (200 / 2) + 5);
        button.setY(((sr.getScaledHeight() / 2) - (150 / 2)) + 150 - 30);

        box.setPosition(new Vector2f((sr.getScaledWidth() / 2) - (box.isEmpty() ? (Fonts.productSans18.getStringWidth("Username") / 2) : (Fonts.productSans18.getStringWidth(box.getText()) / 2))
                , ((sr.getScaledHeight() / 2) - (150 / 2)) + 150 - 30 - 25));

        RenderUtil.drawRoundedRect((sr.getScaledWidth() / 2) - (200 / 2) + 5,
        ((sr.getScaledHeight() / 2) - (150 / 2)) + 150 - 30 - 32, 190, 20, 5, new Color(0,0,0,80).getRGB(), 0);

        RenderUtil.drawRoundedRect((sr.getScaledWidth() / 2) - (200 / 2),
                (sr.getScaledHeight() / 2) - (150 / 2), 200, 150, 5, new Color(255,255,255,80).getRGB(), 0);

        button.drawButton(mouseX, mouseY);
        box.draw();

        if(out) {
            for(int i = 0; i < 5; i++) {
                animate.update();
            }
            RenderUtil.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), animate.getColor().getRGB());
        }

        if(lastFailed) {
            Fonts.productSans18.drawCenteredString("Your hwid or username is invalid!", width / 2, ((sr.getScaledHeight() / 2) - (150 / 2)) + 150 - 100 + 25, Color.RED.getRGB());
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        box.key(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        box.click(mouseX, mouseY, mouseButton);
        if(mouseButton == 0 && button.isHover(mouseX, mouseY)) {
            LoginProtectionHandler.loginAndLaunch(this, box.getText());
        }
    }

}