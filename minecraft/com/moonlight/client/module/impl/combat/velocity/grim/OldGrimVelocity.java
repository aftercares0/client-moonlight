package com.moonlight.client.module.impl.combat.velocity.grim;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.component.impl.rotation.MovementCorrection;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.impl.combat.Velocity;
import com.moonlight.client.module.impl.combat.velocity.grim.old.GrimDiggingVelocity;
import com.moonlight.client.module.impl.combat.velocity.grim.old.GrimPingVelocity;
import com.moonlight.client.module.impl.combat.velocity.grim.old.GrimPlacingVelocity;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.impl.ModeValue;

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

public class OldGrimVelocity extends Mode {

	private ModeValue mode = new ModeValue("Mode", getParent(), new GrimDiggingVelocity(getParent(), "Digging"), 
			new GrimPingVelocity(getParent(), "Spoof"), new GrimPlacingVelocity(getParent(), "Placing"));
    
    public OldGrimVelocity(Module parent, String name) {
        super(parent, name);
    }

}
