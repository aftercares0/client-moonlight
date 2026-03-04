package com.moonlight.client.font;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.moonlight.client.font.basic.MinecraftFontRenderer;
import com.moonlight.client.util.client.FontUtil;

public interface Fonts {

    public static MinecraftFontRenderer productSans40 = FontUtil.getFont("product_sans_regular.ttf", 40);
    public static MinecraftFontRenderer productSans30 = FontUtil.getFont("product_sans_regular.ttf", 30);
    public static MinecraftFontRenderer productSans25 = FontUtil.getFont("product_sans_regular.ttf", 25);
    public static MinecraftFontRenderer productSans18 = FontUtil.getFont("product_sans_regular.ttf", 18);
    public static MinecraftFontRenderer productSans16 = FontUtil.getFont("product_sans_regular.ttf", 16);

    public static MinecraftFontRenderer sfUI40 = FontUtil.getFont("sfuiregular.ttf", 40);
    public static MinecraftFontRenderer sfUI30 = FontUtil.getFont("sfuiregular.ttf", 30);
    public static MinecraftFontRenderer sfUI25 = FontUtil.getFont("sfuiregular.ttf", 25);
    public static MinecraftFontRenderer sfUI18 = FontUtil.getFont("sfuiregular.ttf", 18);
    public static MinecraftFontRenderer sfUI16 = FontUtil.getFont("sfuiregular.ttf", 16);

    public static MinecraftFontRenderer comfortaa40 = FontUtil.getFont("cfont.ttf", 40);
    public static MinecraftFontRenderer comfortaa30 = FontUtil.getFont("cfont.ttf", 30);
    public static MinecraftFontRenderer comfortaa25 = FontUtil.getFont("cfont.ttf", 25);
    public static MinecraftFontRenderer comfortaa18 = FontUtil.getFont("cfont.ttf", 18);
    public static MinecraftFontRenderer comfortaa16 = FontUtil.getFont("cfont.ttf", 16);

    public static MinecraftFontRenderer comfortaaNew18 = FontUtil.getFont("cfont-NEW.ttf", 18);
    
	public static MinecraftFontRenderer bangers30 = FontUtil.getFont("Bangers-Regular.ttf", 30);

    public static MinecraftFontRenderer rubix18 = FontUtil.getFont("Rubik-Regular.ttf", 18);
    public static MinecraftFontRenderer rubix16 = FontUtil.getFont("Rubik-Regular.ttf", 16);
	
    public static MinecraftFontRenderer proxima18 = FontUtil.getFont("proxima.ttf", 18);
    
//    public static MinecraftFontRenderer jelloRegular18 = FontUtil.getFont("jelloregular.ttf", 18);

    public static MinecraftFontRenderer getFontByName(String name) {    	
    	if(name.equalsIgnoreCase("Product Sans 40") || name.equalsIgnoreCase("ProdSans 40")) {
    		return productSans40;
    	}
    	
    	if(name.equalsIgnoreCase("Product Sans 30") || name.equalsIgnoreCase("ProdSans 30")) {
    		return productSans30;
    	}
    	
    	if(name.equalsIgnoreCase("Product Sans 25") || name.equalsIgnoreCase("ProdSans 25")) {
    		return productSans25;
    	}
    	
    	if(name.equalsIgnoreCase("Product Sans 18") || name.equalsIgnoreCase("ProdSans 18")) {
    		return productSans18;
    	}
    	
    	if(name.equalsIgnoreCase("SF UI 40") || name.equalsIgnoreCase("SFUI 40")) {
    		return sfUI40;
    	}
    	
    	if(name.equalsIgnoreCase("SF UI 30") || name.equalsIgnoreCase("SFUI 30")) {
    		return sfUI30;
    	}
    	
    	if(name.equalsIgnoreCase("SF UI 25") || name.equalsIgnoreCase("SFUI 25")) {
    		return sfUI25;
    	}
    	
    	if(name.equalsIgnoreCase("SF UI 18") || name.equalsIgnoreCase("SFUI 18")) {
    		return sfUI18;
    	}
    	
    	if(name.equalsIgnoreCase("SF UI 16") || name.equalsIgnoreCase("SFUI 16")) {
    		return sfUI16;
    	}
    	
    	if(name.equalsIgnoreCase("Comfortaa 40")) {
    		return comfortaa40;
    	}
    	
    	if(name.equalsIgnoreCase("Comfortaa 30")) {
    		return comfortaa30;
    	}
    	
    	if(name.equalsIgnoreCase("Comfortaa 25")) {
    		return comfortaa25;
    	}
    	
    	if(name.equalsIgnoreCase("Comfortaa 18")) {
    		return comfortaa18;
    	}
    	
    	if(name.equalsIgnoreCase("Comfortaa 46")) {
    		return comfortaa16;
    	}
    	
    	return productSans16;
    }
    
}