package com.moonlight.client.module.impl.movement.speed;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.StrafeEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.player.PlayerUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class HypixelSpeed extends Mode {

	private ModeValue mode = new ModeValue("Mode", getParent(), "Ground", "YPort", "Full");
	
    private int ticksSinceVelocity;

    public HypixelSpeed(Module parent, String name) {
        super(parent, name);
    }

    @EventLink
    public void onStrafe(StrafeEvent event) {
        switch (mode.getMode().getName()) {
        	case "Full":
        		getParent().setEnabled(false);
        		ChatUtil.sendClientMessage("This is patched :(, no more full strafe");
                
                break;
        }
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        switch (mode.getMode().getName()) {
        	case "Full":
        		if (mc.thePlayer.fallDistance < 1 && ticksSinceVelocity > 20)
                    event.setOnGround(true);
        		break;
        	case "Ground":
        		if(!MoveUtil.isMoving()) {
                    MoveUtil.stop();
                    return;
                }
        		
        		if(mc.thePlayer.onGround) {
        			mc.thePlayer.jump();
        			MoveUtil.strafe((float) (0.4 - Math.random() / 100.0D));
        		}
        		break;
        	case "YPort":
        		if(!MoveUtil.isMoving()) {
                    MoveUtil.stop();
                    return;
                }
        		
        		if(mc.thePlayer.onGround) {
        			mc.thePlayer.jump();
        		}
        		
        		if(mc.thePlayer.offGroundTicks == 5) {
        			mc.thePlayer.motionY = -0.09800000190734864;
        		}
        		break;
        }
    	
        
    }

    @EventLink
    public void onPreUpdate(PreUpdateEvent event) {
        ticksSinceVelocity++;
    }

    @EventLink
    public void onPacketSend(PacketSendEvent event) {
    }

    @EventLink
    public void onPacketReceive(PacketReceiveEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null) return;

        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                ticksSinceVelocity = 0;
            }
        }
    }

}
