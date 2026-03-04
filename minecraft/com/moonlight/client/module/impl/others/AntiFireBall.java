package com.moonlight.client.module.impl.others;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.component.impl.rotation.MovementCorrection;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.util.rotation.RotationUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.C02PacketUseEntity;

@ModuleInfo(name = "Anti Fire Ball", description = "no more balls" , category = Category.PLAYER, key = 0)
public class AntiFireBall extends Module {

	private Entity target;
	
    private Vector2f rotations;

    private NumberValue<Float> range = new NumberValue<Float>("Range", 3.4f, 1f, 6f);
    private BooleanValue rotation = new BooleanValue("Rotation", true);
    private BooleanValue keepSprint = new BooleanValue("Keep Sprint", false);

    @Override
    public void onDisable() {
        
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        for(Entity e : mc.theWorld.loadedEntityList) {
            if(e instanceof EntityFireball) {
                if(mc.thePlayer.getDistanceToEntity(e) > range.getValue()) continue;

                if(rotation.getState()) {
                    Vector2f calculatedRotation = RotationUtil.getRotations(e, false);
                    RotationComponent.setRotations(calculatedRotation, 90, MovementCorrection.SILENT);
                }

                if(keepSprint.getState()) {
                    PacketUtil.sendNoEvent(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
                }else {
                    mc.playerController.attackEntity(mc.thePlayer, e);
                }

//                if(e != target) {
////                	mc.thePlayer.sendChatMessage("LOL, re.tard thinks that he/she/bruh can fireball me f.u.c.k off.");
//                	target = e;
//                }
                
                PacketUtil.sendNoEvent(new net.minecraft.network.play.client.C0APacketAnimation());
            }
        }
    }


}