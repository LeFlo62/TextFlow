package fr.leflodu62.textflow.packets.connection;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x0C, serverSide = false)
public class RefreshTokenPacket extends Packet {

	public RefreshTokenPacket(String oldToken, String newToken) {
		super(new Object[] {oldToken, newToken});
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		if(clientConnection.getToken().equals(getOldToken())) {
			clientConnection.setToken(getNewToken());
		} else {
			clientConnection.getTextFlowInstance().disconnect(true, "Le renouvellement du Token a échoué.");
		}
	}
	
	public String getOldToken() {
		return (String) data[0];
	}
	
	public String getNewToken() {
		return (String) data[1];
	}

}
