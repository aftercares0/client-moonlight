package com.moonlight.client.scripting.bindings;

import com.moonlight.client.scripting.bindings.impl.PacketBindings;
import com.moonlight.client.scripting.objects.packet.Packet;
import com.moonlight.client.scripting.objects.packet.client.DiggingPacket;
import com.moonlight.client.scripting.objects.packet.client.InteractPacket;
import com.moonlight.client.scripting.objects.packet.client.KeepAlivePacket;
import com.moonlight.client.scripting.objects.packet.client.PlayerPacket;
import com.moonlight.client.scripting.objects.packet.client.SwingPacket;
import com.moonlight.client.scripting.utils.BlockPos;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.world.PacketUtil;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.EnumFacing;

public class PacketBindingsExtends implements PacketBindings {

	@Override
	public void sendPacket(Packet arg0) {
		net.minecraft.network.Packet backPortedPacket = portPacket(arg0);
		if(backPortedPacket != null) {
			PacketUtil.send(backPortedPacket);
		}
	}

	@Override
	public void sendPacketNoEvent(Packet arg0) {
		net.minecraft.network.Packet backPortedPacket = portPacket(arg0);
		if(backPortedPacket != null) {
			PacketUtil.sendNoEvent(backPortedPacket);
		}
	}

	private net.minecraft.network.Packet portPacket(Packet packet) {
		net.minecraft.network.Packet portedPacket = null;
		
		if(packet instanceof PlayerPacket) {
			if(packet instanceof PlayerPacket.PlayerMovePacket) {
				PlayerPacket.PlayerMovePacket wrapper = (PlayerPacket.PlayerMovePacket) packet;
				portedPacket = new C03PacketPlayer.C04PacketPlayerPosition(wrapper.getPositionX(), wrapper.getPositionY(), wrapper.getPositionZ(), wrapper.isOnGround());
			}else if(packet instanceof PlayerPacket.PlayerLookPacket) {
				PlayerPacket.PlayerLookPacket wrapper = (PlayerPacket.PlayerLookPacket) packet;
				
				portedPacket = new C03PacketPlayer.C05PacketPlayerLook(wrapper.yaw, wrapper.pitch, wrapper.isOnGround());
			}else if(packet instanceof PlayerPacket.PlayerMoveAndLookPacket) {
				PlayerPacket.PlayerMoveAndLookPacket wrapper = (PlayerPacket.PlayerMoveAndLookPacket) packet;
				
				portedPacket = new C03PacketPlayer.C06PacketPlayerPosLook(wrapper.getPositionX()
						, wrapper.getPositionY(), wrapper.getPositionZ(), wrapper.yaw, wrapper.pitch, wrapper.isOnGround());
			}else {
				portedPacket = new C03PacketPlayer();
			}
		}
		
		if(packet instanceof KeepAlivePacket) {
			KeepAlivePacket wrapper = (KeepAlivePacket) packet;
			portedPacket = new C00PacketKeepAlive(wrapper.getId());
		}
		
		if(packet instanceof DiggingPacket) {
			DiggingPacket wrapper = (DiggingPacket) packet;

			portedPacket = new C07PacketPlayerDigging(convertDigging(wrapper.getStatus()), 
					new net.minecraft.util.BlockPos(wrapper.getPosition().getX(), wrapper.getPosition().getY(), wrapper.getPosition().getZ()), convertFacing(wrapper.getFacing()));
		}
		
		if(packet instanceof SwingPacket) {
			portedPacket = new C0APacketAnimation();
		}
		
		if(packet instanceof InteractPacket) {
			InteractPacket wrapper = (InteractPacket) packet;
			
			portedPacket = new C02PacketUseEntity(MinecraftInstance.mc.theWorld.getEntityByID(wrapper.entityId), convertInteract(wrapper.getAction()));
		}
		
		return portedPacket;
	}
	
	private C07PacketPlayerDigging.Action convertDigging(com.moonlight.client.scripting.objects.packet.client.DiggingPacket.Action action) {
		for(C07PacketPlayerDigging.Action a : C07PacketPlayerDigging.Action.values()) {
			if(a.name().equalsIgnoreCase(action.name())) {
				return a;
			}
		}
		
		return null;
	}
	
	private EnumFacing convertFacing(com.moonlight.client.scripting.utils.EnumFacing action) {
		for(EnumFacing a : EnumFacing.values()) {
			if(a.name().equalsIgnoreCase(action.name())) {
				return a;
			}
		}
		
		return null;
	}
	
	private C02PacketUseEntity.Action convertInteract(com.moonlight.client.scripting.objects.packet.client.InteractPacket.Action action) {
		for(C02PacketUseEntity.Action a : C02PacketUseEntity.Action.values()) {
			if(a.name().equalsIgnoreCase(action.name())) {
				return a;
			}
		}
		
		return null;
	}
	
}
