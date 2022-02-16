package fr.leflodu62.textflow.packets.message;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id=0x09, serverSide = false)
public class MessagePacket extends Packet {

	public MessagePacket(String username, String message) {
		super(new Object[] {username, message});
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		clientConnection.getTextFlowInstance().addMessage(getUsername(), getMessage());
	}
	
	public String getUsername() {
		return (String) data[0];
	}
	
	public String getMessage() {
		return (String) data[1];
	}

}
