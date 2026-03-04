package com.moonlight.client.module.impl.others;

import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.WorldChangeEvent;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.value.impl.BooleanValue;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.util.world.PacketUtil;

@ModuleInfo(name = "AutoHeroMC", description = "fuck heromc checks", category = Category.OTHERS)
public class AutoHeroMC extends Module {

    private BooleanValue viaVersion = new BooleanValue("SwapHand Spoof", true);
    private BooleanValue s12spam = new BooleanValue("Anti Spam Velocity", false);
    private boolean canCancel;
    private boolean canSpoof;

    private boolean sended;

    @Override
    public void onEnabled() {
        sended = false;
        canCancel = false;
        canSpoof = false;
    }

    @EventLink
    public void onChangeWorld(WorldChangeEvent event) {
        sended = false;
        canCancel = false;
        canSpoof = false;
    }

    @EventLink
    public void onPacketSend(PacketSendEvent event) {

    }

    @EventLink
    public void onUpdate(PreUpdateEvent event) {
        if(canSpoof) {
            PacketUtil.sendNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                    new BlockPos(mc.thePlayer), EnumFacing.DOWN));
            canSpoof = false;
            canCancel = false;
        }
    }

    @EventLink
    public void onPacketReceive(PacketReceiveEvent event) {
        if(viaVersion.getState()) {
            if(event.getPacket() instanceof S45PacketTitle && !sended) {
                S45PacketTitle wrapper = (S45PacketTitle) event.getPacket();

                if(wrapper.getMessage() == null) return;

                if(wrapper.getMessage().getUnformattedText().contains("F (Swap hand)")) {
                    if(mc.thePlayer.getHeldItem() != null) {
                        PacketUtil.sendNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.SWAP_HELD_ITEMS
                                , new BlockPos(mc.thePlayer), EnumFacing.UP));
                        sended = true;
                    }
                }
            }
        }

        if(s12spam.getState()) {
            if (event.getPacket() instanceof S19PacketEntityStatus) {
                S19PacketEntityStatus packet = (S19PacketEntityStatus)event.getPacket();
                if (packet.getEntity(mc.theWorld) != mc.thePlayer || packet.getOpCode() != 2)
                    return;

                this.canCancel = true;
            }

            if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId() && !this.canCancel) {
                event.setCancelled(true);

                this.canSpoof = true;
            }
        }
    }

}
