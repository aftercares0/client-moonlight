package com.moonlight.client.component.impl.viamcp;

import org.apache.commons.lang3.RandomUtils;

import com.moonlight.client.component.Component;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.scripting.bindings.impl.MinecraftBindings;
import com.moonlight.client.util.MinecraftInstance;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.world.PacketUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.ViaMCP;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.server.S14PacketEntity.S15PacketEntityRelMove;
import net.minecraft.network.play.server.S14PacketEntity.S17PacketEntityLookMove;

public class ViaMCPPatch implements Component {
	
	private int order = -1;
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		if(event.getPacket() instanceof C03PacketPlayer) {
			final C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
			
			if(ViaLoadingBase.getInstance().getTargetVersion().getVersion() > ViaLoadingBase.getInstance().getNativeVersion()) {
				if(!packet.isMoving() && !packet.getRotating()) {
					event.setCancelled(true);
				}
			}
		}
		
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {

	}
	
}
