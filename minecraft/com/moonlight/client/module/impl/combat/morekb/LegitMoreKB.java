package com.moonlight.client.module.impl.combat.morekb;

import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;

import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.impl.combat.Aura;

public class LegitMoreKB extends Mode {
	
    public LegitMoreKB(Module parent, String name) {
        super(parent, name);
    }

    @Override
    public void onDisabled() {
    }
    
    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	Aura aura = Client.INSTANCE.moduleManager.get(Aura.class);
    	
    	EntityLivingBase target = aura.target;
    	if(target == null) return;
    	if(!aura.canAttack(target, false)) return;
    	
    	if(target.hurtTime == 10) {
    		mc.thePlayer.serverSprintState = false;
    		mc.thePlayer.reSprint = 2;
    		mc.thePlayer.serverSprintState = true;
    	}
    }

}
