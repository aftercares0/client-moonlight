/*
 * Decompiled with CFR 0.1.1 (FabricMC 57d88659).
 */
package net.minecraft.network.packet.s2c.play;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

/**
 * A packet sent by the server; the client will reply with a pong packet on the
 * first tick after it receives this packet, with the same {@link #parameter}.
 * 
 * @see net.minecraft.network.packet.c2s.play.PlayPongC2SPacket
 * @see net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket
 * @see net.minecraft.network.packet.s2c.query.QueryPongS2CPacket
 */
public class PlayPingS2CPacket
implements Packet<INetHandlerPlayClient> {
    /**
     * The parameter of this ping packet.
     * 
     * <p>If this number represents a tick, this could measure the network delay in
     * ticks. It is possible to be a tick number given the reply packet is sent on
     * the client on the main thread's tick, and the number is sent as a regular int
     * than a varint.
     */
    private int parameter;

    public PlayPingS2CPacket(int parameter) {
        this.parameter = parameter;
    }

    public int getParameter() {
        return this.parameter;
    }

	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.parameter = buf.getInt(parameter);
	}

	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeInt(parameter);
	}

	@Override
	public void processPacket(INetHandlerPlayClient handler) {
		//Nothing
	}
}

