package com.moonlight.client.util.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.moonlight.client.util.MinecraftInstance;

import net.minecraft.util.ResourceLocation;

public class FileUtil {

	public static List<String> getLinesFromFile(String file) {
		List<String> lines = new ArrayList<>();
		try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(MinecraftInstance.mc.getResourceManager().getResource(new ResourceLocation("moonlight/" + file)).getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	lines.add(line);
            }
            bufferedReader.close();
        } catch (IOException exc) {
        }
		
		return lines;
	}
	
}
