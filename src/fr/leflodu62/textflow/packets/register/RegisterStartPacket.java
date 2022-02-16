package fr.leflodu62.textflow.packets.register;

import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id=0x0D, serverSide = true)
public class RegisterStartPacket extends Packet {

	public RegisterStartPacket(String username) {
		super(new Object[] {username});
	}

}
