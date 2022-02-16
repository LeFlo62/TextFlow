package fr.leflodu62.textflow.packets.register;

import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x0E, serverSide = true)
public class RegisterPacket extends Packet {

	public RegisterPacket(String username, String password) {
		super(new Object[] {username, password});
	}

}
