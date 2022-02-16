package fr.leflodu62.textflow.packets.login;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x03, serverSide = false)
public class EncryptionReadyPacket extends Packet {

	public EncryptionReadyPacket() {
		super(null);
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		try {
			clientConnection.enableEcryption();
			clientConnection.getConnectionEstablished().run();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
