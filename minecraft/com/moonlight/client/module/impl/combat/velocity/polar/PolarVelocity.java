package com.moonlight.client.module.impl.combat.velocity.polar;

import java.util.ArrayList;
import java.util.Random;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.TickEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.client.resources.Language;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;

public class PolarVelocity extends Mode {

    private NumberValue<Integer> chance = new NumberValue("Chance", 50, 1, 100);
    private BooleanValue groundOnly = new BooleanValue("Ground Only", false);

    private int ticks;
    private boolean canCancel, last;
	        
    public PolarVelocity(Module parent, String name) {
        super(parent, name);
    }
    
    @Override
    public void onDisabled() {
        last = false;
    }
    
    @EventLink
    public void onTick(TickEvent event) {
    	
    }
    
    @EventLink
    public void onPacket(PacketReceiveEvent event) {
    	if (event.getPacket() instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus packet = (S19PacketEntityStatus)event.getPacket();
            if (packet.getEntity(mc.theWorld) != mc.thePlayer || packet.getOpCode() != 2)
                return;

            this.canCancel = true;
        }
    	
        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()
        		&& canCancel && Math.random() * 100.0D < chance.getValue()) {
            if(groundOnly.getState() && !mc.thePlayer.onGround) return;
            if(last) {
                last = false;
                return;
            }
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

            ChatUtil.sendClientMessage("Cancelled");
        	ticks = 7;
        	
            event.setCancelled(true);

            last = true;
        }
    }
    
    @EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
        if(event.getPacket() instanceof S00PacketKeepAlive && ticks > 0) {
        	event.setCancelled(true);
        }

	}
    
    @EventLink
    public void onPacket(PacketSendEvent event) {
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	if(ticks > -1) ticks--;
    }

    @EventLink
    public void onMoveInput(MoveInputEvent event) {

    }

}
