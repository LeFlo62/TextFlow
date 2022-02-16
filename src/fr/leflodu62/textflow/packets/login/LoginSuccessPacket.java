package fr.leflodu62.textflow.packets.login;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.ClientConnection.State;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;
import fr.leflodu62.textflow.ui.ChatFrame;

@PacketInfo(id = 0x06, serverSide = false)
public class LoginSuccessPacket extends Packet {

	public LoginSuccessPacket(String token) {
		super(new Object[] {token});
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		clientConnection.setState(State.LOGGED);
		clientConnection.setToken(getToken());
		clientConnection.getTextFlowInstance().openFrame(new ChatFrame(clientConnection.getTextFlowInstance()));
		clientConnection.getTextFlowInstance().appendMessageWithColors("§dConnecté au serveur " + clientConnection.getRemoteAddress() + " en tant que " + clientConnection.getUsername() + ".");
	}
	
	public String getToken() {
		return (String) data[0];
	}
	
}
