package com.moonlight.client.module.impl.test;

import org.lwjgl.input.Keyboard;

import com.moonlight.client.ClickUIInstance;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.ui.clickui.dropdown.components.settings.NumberComponent;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

@ModuleInfo(name = "TestModule", description = "for testing clickgui" , category = Category.VISUALS, isDev = true)
public class TestModule extends Module {
	
	private BooleanValue test1 = new BooleanValue("Test Bool", true);
	private ModeValue test2 = new ModeValue("Test Mode", this, "One", "Two", "Three");
	private NumberValue<Integer> test3 = new NumberValue("Test Int", 1, 1, 10);
	private NumberValue<Float> test4 = new NumberValue("Test Float", 1f, 1f, 10f);

}
