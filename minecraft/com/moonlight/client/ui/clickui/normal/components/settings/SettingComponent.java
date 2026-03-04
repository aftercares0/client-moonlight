package com.moonlight.client.ui.clickui.normal.components.settings;

import com.moonlight.client.ui.component.Component;
import com.moonlight.client.value.impl.BooleanValue;

public abstract class SettingComponent extends Component {
	
	protected int x, y;	
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
