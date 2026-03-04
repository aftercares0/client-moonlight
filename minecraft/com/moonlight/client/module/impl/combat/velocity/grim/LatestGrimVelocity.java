package com.moonlight.client.module.impl.combat.velocity.grim;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.component.impl.rotation.MovementCorrection;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.impl.combat.Velocity;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;

import io.netty.buffer.Unpooled;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.moonlight.client.module.api.Module;

public class LatestGrimVelocity extends Mode {

    private boolean canCancel;
    private boolean canSpoof, lastSpoofed;
        
    private S32PacketConfirmTransaction last;
    
    public LatestGrimVelocity(Module parent, String name) {
        super(parent, name);
    }

    @Override
    public void onDisabled() {
    	last = null;
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
        
        if (event.getPacket() instanceof S32PacketConfirmTransaction) {
        	S32PacketConfirmTransaction wrapper = (S32PacketConfirmTransaction) event.getPacket();

        }
        
    }

    @EventLink
    public void onUpdate(PreUpdateEvent event) {
        if(canSpoof) {
        	
            canSpoof = false;
        }
    }

}
