package fr.leflodu62.textflow.packets.login;

import javax.crypto.SealedObject;

import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x02, serverSide = true)
public class EncryptionResponsePacket extends Packet {

	public EncryptionResponsePacket(SealedObject secretKey, SealedObject verifyToken) {
		super(new Object[] {secretKey, verifyToken});
	}

}
