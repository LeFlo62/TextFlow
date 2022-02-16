package fr.leflodu62.textflow.packets.login;

import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id=0x00, serverSide = true)
public class LoginStartPacket extends Packet {

	public LoginStartPacket(String username) {
		super(new Object[] {username});
	}

}
