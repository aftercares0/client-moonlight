package com.moonlight.client.scripting;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.scripting.module.Module;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.value.Value;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.client.Minecraft;

public class ScriptManager {

	public File scriptDir = new File(new File(MinecraftInstance.mc.mcDataDir, Client.NAME.toLowerCase()), "scripts");

	public CopyOnWriteArrayList<ScriptingModule> registereds = new CopyOnWriteArrayList<ScriptingModule>();
	
	public ScriptManager() {
		reloadScripts();
	}

	public void reloadScripts() {
		registereds.clear();
		
		if(!scriptDir.exists()) {
			scriptDir.mkdirs();
		}
		
		if (scriptDir.exists() && scriptDir.isDirectory()) {
            File[] files = scriptDir.listFiles();

            if (files != null) {
                for (File file : files) {
                	System.out.println(file.getName());
                    try {
                    	Class<?> klass = defineClass(file);
                    	com.moonlight.client.scripting.module.Module module = (com.moonlight.client.scripting.module.Module) klass.getDeclaredConstructor().newInstance();
						if(Client.INSTANCE.moduleManager.get(module.getName()) == null) {
							ScriptingModule newModule = new ScriptingModule(module);
							
							Client.INSTANCE.moduleManager.add(newModule);
							
							System.out.println("Loaded: " + file.getName());
						}else {
							System.out.println("Module already exist");
							throw new Exception();
						}
					} catch (Exception e) {
						ChatUtil.sendClientMessage("Failed to load script: " + file.getName());
						e.printStackTrace();
					}
                }
            }else {
            	System.out.println("No script found, skip.");
            }
        }
	}
	
	private static Class<?> defineClass(File file) throws Exception {
        byte[] classData = Files.readAllBytes(file.toPath());
        String className = getClassNameFromFile(file);

        Method define = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class, ProtectionDomain.class);
        define.setAccessible(true);
        Class<?> klass = (Class<?>)define.invoke(getLoader(), className, classData, 0, classData.length, null);
        return klass;
	}

    private static ClassLoader getLoader() {
        try {
            for(Thread t : Thread.getAllStackTraces().keySet()) {
                ClassLoader loader = t.getContextClassLoader();
                if(loader != null && (
                        loader.toString().contains("net.minecraft.launchwrapper.Launch")
                                || loader.toString().contains("net.minecraft.launchwrapper.LaunchClassLoader"))
                ) {
                    return loader;
                }
            }
        } catch(Exception ex) {}
        return Minecraft.class.getClassLoader();
    }

    private static String getClassNameFromFile(File file) {
        String fileName = file.getName();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
	
}
