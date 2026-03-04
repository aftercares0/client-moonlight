package com.moonlight.client.module.impl.movement.flight;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.StrafeEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;

//Attempt to make watchdog airlines
public class WatchdogFlight extends Mode {

    private boolean last;
    private boolean canFly;

    public WatchdogFlight(Module parent, String name) {
        super(parent, name);
    }

    @EventLink
    public void onStrafe(StrafeEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onEnabled() {
        canFly = false;
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        mc.thePlayer.motionY = 0;
        if(!canFly) return;

        if(mc.thePlayer.ticksExisted % 20 == 0) {
            double x = event.getX() + -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw)) * 1;
            double y = event.getY() + (last ? 0.2 : -0.2);
            double z = event.getZ() + Math.cos(Math.toRadians(mc.thePlayer.rotationYaw)) * 1;
            if(mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.air) {
                PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, !last));
                mc.thePlayer.setPosition(x, y, z);
            }
            last = !last;
        }
    }

    @EventLink
    public void onPacketSend(PacketSendEvent event) {
        if(event.getPacket() instanceof C03PacketPlayer) {
            event.setCancelled(true);
        }


    }

    @EventLink
    public void onPacketReceive(PacketReceiveEvent event) {
        if(event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                event.setCancelled(true);
                canFly = true;

                mc.thePlayer.motionY = (packet.motionY / 8000.0D);
            }
        }
    }

}
