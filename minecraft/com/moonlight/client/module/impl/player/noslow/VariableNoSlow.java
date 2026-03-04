package com.moonlight.client.module.impl.player.noslow;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.SlowDownEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

public class VariableNoSlow extends Mode {

	public NumberValue<Float> forward = new NumberValue("Forward", 1f, 0.2f, 1f);
	public NumberValue<Float> strafe = new NumberValue("Strafe", 1f, 0.2f, 1f);
	
	public VariableNoSlow(Module parent, String name) {
		super(parent, name);
	}
    
    @EventLink
    public void onSlowDown(SlowDownEvent event) {
        event.setForward(this.forward.getValue());
        event.setStrafe(this.strafe.getValue());
        
        mc.thePlayer.setSprinting(true);
    }
    
}
