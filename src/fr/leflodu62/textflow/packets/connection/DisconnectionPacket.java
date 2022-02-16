package fr.leflodu62.textflow.packets.connection;

import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x0A, serverSide = true)
public class DisconnectionPacket extends Packet {

	public DisconnectionPacket() {
		this("closed connection");
	}
	
	public DisconnectionPacket(String reason) {
		super(new Object[] {reason});
	}

}
