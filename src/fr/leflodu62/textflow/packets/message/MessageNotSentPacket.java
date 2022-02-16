package fr.leflodu62.textflow.packets.message;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x10, serverSide = false)
public class MessageNotSentPacket extends Packet {

	public MessageNotSentPacket() {
		super(null);
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		clientConnection.getTextFlowInstance().appendMessageWithColors("§cLe serveur n'a pas reçu votre message.");
	}

}
