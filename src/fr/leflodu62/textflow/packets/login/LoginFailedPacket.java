package fr.leflodu62.textflow.packets.login;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id=0x05, serverSide = false)
public class LoginFailedPacket extends Packet {

	public LoginFailedPacket(String reason) {
		super(new Object[] {reason});
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		clientConnection.getTextFlowInstance().setLoginErrorMessage(getReason());
	}
	
	public String getReason() {
		return (String) data[0];
	}

}
