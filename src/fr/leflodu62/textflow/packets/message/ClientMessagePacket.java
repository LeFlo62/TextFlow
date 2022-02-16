package fr.leflodu62.textflow.packets.message;

import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id=0x07,serverSide = true)
public class ClientMessagePacket extends Packet {

	public ClientMessagePacket(String message) {
		super(new Object[] {message});
	}

}
