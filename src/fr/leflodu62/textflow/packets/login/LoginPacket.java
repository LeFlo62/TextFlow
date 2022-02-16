package fr.leflodu62.textflow.packets.login;

import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x04, serverSide = true)
public class LoginPacket extends Packet {

	public LoginPacket(String username, String password) {
		super(new Object[] {username, password});
	}

}
