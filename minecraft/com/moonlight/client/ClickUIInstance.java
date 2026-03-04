package com.moonlight.client;

import com.moonlight.client.ui.clickui.dropdown.DropdownClickUI;
import com.moonlight.client.ui.clickui.normal.NormalClickUI;

public class ClickUIInstance {

	public static DropdownClickUI dropDownClickUI;
	public static NormalClickUI normalClickUI;
	
	public static void init() {
		dropDownClickUI = new DropdownClickUI();
		normalClickUI = new NormalClickUI();
	}
	
}
