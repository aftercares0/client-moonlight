package net.minecraft.network.protocol.game;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class ServerboundPongPacket implements Packet<INetHandlerPlayClient>
{
    private int id;

    public ServerboundPongPacket(int p_179723_)
    {
        this.id = p_179723_;
    }

    public ServerboundPongPacket(PacketBuffer p_179725_)
    {
        this.id = p_179725_.readInt();
    }
    
    public int getId()
    {
        return this.id;
    }

	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
        this.id = buf.readInt();
	}

	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeInt(this.id);
	}

	@Override
	public void processPacket(INetHandlerPlayClient handler) {
	}
}
