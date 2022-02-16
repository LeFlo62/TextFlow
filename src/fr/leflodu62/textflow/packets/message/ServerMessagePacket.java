package fr.leflodu62.textflow.packets.message;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x0B, serverSide = false)
public class ServerMessagePacket extends Packet {

	public ServerMessagePacket(String message) {
		super(new Object[] {message});
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		clientConnection.getTextFlowInstance().appendMessageWithColors(getMessage());
	}
	
	public String getMessage() {
		return (String) data[0];
	}

}
