package com.moonlight.client.module.impl.combat.velocity.grim.old;

import java.util.ArrayList;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.impl.combat.Velocity;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.moonlight.client.module.api.Module;

public class GrimDiggingVelocity extends Mode {

    private boolean canCancel;
    private boolean canSpoof;
        
    public GrimDiggingVelocity(Module parent, String name) {
        super(parent, name);
    }

    @Override
    public void onDisabled() {
    }

    @EventLink
    public void onPacket(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus packet = (S19PacketEntityStatus)event.getPacket();
            if (packet.getEntity(mc.theWorld) != mc.thePlayer || packet.getOpCode() != 2)
                return;

            this.canCancel = true;
        }

        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId() && this.canCancel) {
        	event.setCancelled(true);
        	this.canCancel = false;
            this.canSpoof = true;
        }
    }

    @EventLink
    public void onUpdate(PreUpdateEvent event) {
        if(canSpoof) {
            PacketUtil.sendNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                    new BlockPos(mc.thePlayer), EnumFacing.DOWN));
            mc.theWorld.setBlockToAir(new BlockPos(mc.thePlayer));
            canSpoof = false;
        }
    }

}
