package com.moonlight.client.component;

import java.util.ArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.component.impl.viamcp.ViaMCPPatch;

public class ComponentManager extends ArrayList<Component> {
	
	public ComponentManager() {
		add(new ViaMCPPatch());
		add(new RotationComponent());
	}
	
	@Override
	public boolean add(Component e) {
		Client.EVENT_BUS.register(e);
		return true;
	}

}
