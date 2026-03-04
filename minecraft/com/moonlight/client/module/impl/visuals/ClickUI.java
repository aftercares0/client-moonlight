package com.moonlight.client.module.impl.visuals;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.ClickUIInstance;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.value.impl.ModeValue;

@ModuleInfo(name = "ClickUI", description = "Clickable User Interface" , category = Category.VISUALS, key = Keyboard.KEY_RSHIFT)
public class ClickUI extends Module {

	private ModeValue test2 = new ModeValue("Style", this, "Normal", "Simple");
	
	@Override
	public void onEnabled() {
		this.setEnabled(false);
		
		switch (test2.getMode().getName()) {
			case "Simple":
				mc.displayGuiScreen(ClickUIInstance.dropDownClickUI);
				break;
			case "Normal":
				mc.displayGuiScreen(ClickUIInstance.normalClickUI);
				break;
		}		
	}
	
}
