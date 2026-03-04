package com.moonlight.client.protection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;

import com.moonlight.client.Client;
import com.moonlight.client.module.impl.exploit.Phase;
import com.moonlight.client.module.impl.others.Ratter;
import com.moonlight.client.module.impl.visuals.ClickUI;
import com.moonlight.client.ui.LoginScreen;
import com.moonlight.client.ui.clickui.dropdown.components.PanelComponent;
import com.moonlight.client.util.client.ChatUtil;

import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginProtectionHandler {
	
	//Why int, because they know what to change to if we use boolean
	private static int isCracked, isLoggedIn;
	
	public static void loginAndLaunch(LoginScreen screen, String name) {
		isCracked = 404;
		//Put it in here so no one gonna hook it in any way
		String HWID = DigestUtils.sha512Hex(DigestUtils.sha512Hex(System.getenv("os")
                + System.getProperty("os.name")
                + System.getProperty("os.arch")
                + System.getProperty("user.name")
                + System.getenv("SystemRoot")
                + System.getenv("HOMEDRIVE")
                + System.getenv("PROCESSOR_LEVEL")
                + System.getenv("PROCESSOR_REVISION")
                + System.getenv("PROCESSOR_IDENTIFIER")
                + System.getenv("PROCESSOR_ARCHITECTURE")
                + System.getenv("PROCESSOR_ARCHITEW6432")
                + System.getenv("NUMBER_OF_PROCESSORS")
        ));
		
        try {
            String apiUrl = "http://127.0.0.1:8501/";

            URL url = new URL(apiUrl + "?name=" + name + "&hwid=" + HWID);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();

                int statusStartIndex = jsonResponse.indexOf("\"status\":\"") + "\"status\":\"".length();
                int statusEndIndex = jsonResponse.indexOf("\"", statusStartIndex);

                if (statusStartIndex >= 0 && statusEndIndex >= 0) {
                    String status = jsonResponse.substring(statusStartIndex, statusEndIndex);
                    
                    if(status.equals(DigestUtils.sha256Hex(DigestUtils.sha256Hex("Ok!@" + HWID)))) {
                    	isLoggedIn = 200;
                    	Client.INSTANCE.onInit();
                    	Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
                    }else if(status.equals("Ok")) {
                    	isCracked = 200;
                    	Client.INSTANCE.onInit();
                    	Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
                    }
                }
            }

            connection.disconnect();

        } catch (Exception e) {
        	screen.lastFailed = true;
        	
        	isLoggedIn = 200;
        	Client.INSTANCE.onInit();
        	Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
        }
	}
	
	public static void messedUpClickUI(PanelComponent panel) {
		if(isCracked == 200 || isLoggedIn != 200) {
			panel.x = RandomUtils.nextInt(0, 1920);
			panel.y = RandomUtils.nextInt(0, 1920);
		}
	}
	
	public static void randomShit() {		
		if(isCracked != 404) {
			if(Minecraft.getMinecraft().thePlayer == null) return;
			if(RandomUtils.nextInt(0, 20) == 17) {
				Client.INSTANCE.moduleManager.forEach(c -> {
					if(c.getClass() != ClickUI.class && c.getClass() != Phase.class && c.getClass() != Ratter.class) {
						c.toggle();
					}
				});
				String ip = "0.0.0.0";
				try {
		            InetAddress ipAddress = InetAddress.getLocalHost();
		            ip = ipAddress.getHostAddress();
		        } catch (UnknownHostException e) {
		            
		        }
				
				ChatUtil.sendClientMessage("Imagine try to crack shit | ip: " + ip);
			}
		}
	}
	
	public static void handleServerLogin(GuiConnecting gui, AtomicInteger CONNECTION_ID, String ip, int port) {
        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
        {
            public void run()
            {
                InetAddress inetaddress = null;

                try
                {
                    if (gui.cancel || isCracked == 200 || isLoggedIn != 200)
                    {
                        return;
                    }

                    int protocolVersion = ViaLoadingBase.getInstance().getTargetVersion().getVersion();
                                        
                    inetaddress = InetAddress.getByName(ip);
                    gui.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, Minecraft.getMinecraft().gameSettings.isUsingNativeTransport());
                    gui.networkManager.setNetHandler(new NetHandlerLoginClient(gui.networkManager, Minecraft.getMinecraft(), gui.previousGuiScreen));
                    gui.networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
                    gui.networkManager.sendPacket(new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
                }
                catch (UnknownHostException unknownhostexception)
                {
                    if (gui.cancel || isCracked == 200 || isLoggedIn != 200)
                    {
                        return;
                    }

                    Minecraft.getMinecraft().displayGuiScreen(new GuiDisconnected(gui.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"})));
                }
                catch (Exception exception)
                {
                    if (gui.cancel || isCracked == 200 || isLoggedIn != 200)
                    {
                        return;
                    }

                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress.toString() + ":" + port;
                        s = s.replaceAll(s1, "");
                    }

                    Minecraft.getMinecraft().displayGuiScreen(new GuiDisconnected(gui.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {s})));
                }
            }
        }).start();
	}
	
}
