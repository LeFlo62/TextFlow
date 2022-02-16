package fr.leflodu62.textflow.packets.connection;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;
import fr.leflodu62.textflow.ui.DisconnectPopup;

@PacketInfo(id=0x08, serverSide = false)
public class ForcedClosedConnectionPacket extends Packet {

	public ForcedClosedConnectionPacket(String reason) {
		super(new Object[] {reason});
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		clientConnection.getTextFlowInstance().openFrame(new DisconnectPopup(clientConnection.getTextFlowInstance(), getReason()));
		clientConnection.getTextFlowInstance().killConnection();
	}

	private String getReason() {
		return (String) data[0];
	}

}
