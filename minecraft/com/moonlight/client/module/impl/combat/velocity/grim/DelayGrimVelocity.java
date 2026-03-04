package com.moonlight.client.module.impl.combat.velocity.grim;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.impl.combat.Velocity;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.moonlight.client.module.api.Module;

public class DelayGrimVelocity extends Mode {
	
    private boolean canCancel;

    private CopyOnWriteArrayList<Packet> last = new CopyOnWriteArrayList<>();
    
    private int ticks;
    
    public DelayGrimVelocity(Module parent, String name) {
        super(parent, name);
    }

    @Override
    public void onDisabled() {
    	if(last == null) return;
    	try {
    		if(!last.isEmpty()) {
    			last.forEach(p -> {
    				if(p instanceof C0FPacketConfirmTransaction) {
    					PacketUtil.sendNoEvent(p);
    				}
    				
    				if(p instanceof S12PacketEntityVelocity) {
    					S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;
    					mc.thePlayer.setVelocity(wrapper.getMotionX() / 8000.0D, wrapper.getMotionY() / 8000.0D, 
    							wrapper.getMotionZ() / 8000.0D);
    				}
    			});
            	last.clear();
        	}
    	}catch (Exception e) {
			
		}
    	
    	ticks = 0;
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
    	if(ticks > 0) ticks--;
    }
    
    @EventLink
    public void onPacketSend(PacketSendEvent event) {
    	//me when desync start kicking in
        if(mc.thePlayer != null) {
        	if(event.getPacket() instanceof C0FPacketConfirmTransaction && ticks > 0) {
            	event.setCancelled(true);
            	
            	last.add(event.getPacket());
            }else if(event.getPacket() instanceof C0FPacketConfirmTransaction && ticks <= 0) {
            	if(!last.isEmpty()) {
        			last.forEach(p -> {
        				if(p instanceof C0FPacketConfirmTransaction) {
        					PacketUtil.sendNoEvent(p);
        				}
        				
        				if(p instanceof S12PacketEntityVelocity) {
        					S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;
        					mc.thePlayer.setVelocity(wrapper.getMotionX() / 8000.0D, wrapper.getMotionY() / 8000.0D, 
        							wrapper.getMotionZ() / 8000.0D);
        				}
        			});
                	last.clear();
            	}
            }
        }
    }
    
    @EventLink
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus packet = (S19PacketEntityStatus)event.getPacket();
            if (packet.getEntity(mc.theWorld) != mc.thePlayer || packet.getOpCode() != 2)
                return;

            this.canCancel = true;
        }

        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId() && this.canCancel) {
        	event.setCancelled(true);
        	last.add(event.getPacket());

            this.canCancel = false;
            
            if(ticks <= 0) {
                ticks = 4;
            }
        }
    }
    
    @EventLink
    public void onUpdate(PreUpdateEvent event) {
        
    }

}
