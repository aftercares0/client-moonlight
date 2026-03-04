package com.moonlight.client.module.api.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.moonlight.client.Client;
import com.moonlight.client.font.Fonts;
import com.moonlight.client.font.basic.MinecraftFontRenderer;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.combat.*;
import com.moonlight.client.module.impl.exploit.*;
import com.moonlight.client.module.impl.movement.*;
import com.moonlight.client.module.impl.others.*;
import com.moonlight.client.module.impl.player.*;
import com.moonlight.client.module.impl.test.*;
import com.moonlight.client.module.impl.visuals.*;
import com.moonlight.client.util.MinecraftInstance;

public class ModuleManager extends ArrayList<Module>{

	public ModuleManager() {
		//Combat
		add(new Aura());
		add(new Velocity());
		add(new AntiBot());
		add(new WTap());
		//add(new TickBase());
		add(new MoreKB());
		add(new Criticals());
		
		//Movement
		add(new Flight());
		add(new LongJump());
		add(new Speed());
		add(new Eagle());
		add(new Sprint());

		//Player
		add(new NoSlow());
		add(new FastUse());
		add(new AutoTool());
		add(new Scaffold());
		add(new FastMine());
		add(new Stealer());
		
		//Visuals
		add(new Interface());
		add(new Animations());
		add(new ESP());
		add(new SuperHeroFX());
		add(new Watermark());
		add(new TargetHUD());
		add(new ArraylistModule());
		add(new ItemPhysics());
		add(new ClickUI());
		
		//Others
		add(new Insults());
		add(new AutoHeroMC());
		add(new Ratter());
		add(new AntiResources());
		add(new AutoPlay());
		add(new RichPresence());
		//add(new AlwaysGap());
		add(new AntiGuiClose());
		add(new AutoJoin());
		add(new AntiFireBall());
		add(new PacketMonitor());
		
		//Exploit
		add(new Phase());
		add(new AntiVoid());
		add(new PingSpoofer());
		add(new Regen());
		add(new AntiInvis());
		add(new Disabler());
		add(new Blink());
		add(new Timer());
		add(new NoRotate());
		
		//Test
		add(new TestModule());
	}
	
	@Override
	public boolean add(Module m) {
		if(!Client.DEVELOPMENT_SWITCH && m.getClass().getAnnotation(ModuleInfo.class).isDev()) return false;
		m.setup();
		return super.add(m);
	}
	
	public <T extends Module> T get(final Class<?> clazz) {
        return (T) this.stream()
                .filter(module -> module.getClass() == clazz)
                .findAny().orElse(null);
    }
	
	public <T extends Module> T get(final String name) {
        return (T) this.stream()
                .filter(module -> module.getName().replace(" ", "").equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public List<Module> get(final Category category) {
        return this.stream()
                .filter(module -> module.getCategory() == category)
                .collect(Collectors.toList());
    }
    
    public List<Module> getSorted() {
    	ArrayList<Module> mods = new ArrayList<>();

		for(Module m : this) {
			mods.add(m);
		}
		
		mods.sort((m2, m1) -> MinecraftInstance.mc.fontRendererObj.getStringWidth(m1.getDisplayName()) - MinecraftInstance.mc.fontRendererObj.getStringWidth(m2.getDisplayName()));
		return mods;
    }
    
    public List<Module> getSortedAnother(MinecraftFontRenderer font) {
    	ArrayList<Module> mods = new ArrayList<>();

		for(Module m : this) {
			mods.add(m);
		}
		
		mods.sort((m2, m1) -> font.getStringWidth(m1.getDisplayName()) - font.getStringWidth(m2.getDisplayName()));
		return mods;
    }
	
}
