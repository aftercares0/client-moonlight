package com.moonlight.client.module.impl.combat.velocity;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class LegitVelocity extends Mode {

    private NumberValue<Integer> chance = new NumberValue("Chance", 100, 1, 100);

    private boolean jump;

    public LegitVelocity(Module parent, String name) {
        super(parent, name);
    }

    @Override
    public void onDisabled() {
        jump = false;
    }

    @EventLink
    public void onPacket(PacketReceiveEvent event) {
        if(mc.thePlayer == null) return;

        if(!mc.thePlayer.onGround) return;

        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()
                && MoveUtil.isMoving() && Math.random() * 100.0D < this.chance.getValue()) {
            jump = true;
        }
    }

    @EventLink
    public void onMoveInput(MoveInputEvent event) {
        if(jump) {
            event.setJump(true);
            jump = false;
        }
    }

}
