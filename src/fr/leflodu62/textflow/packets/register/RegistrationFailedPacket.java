package fr.leflodu62.textflow.packets.register;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;

@PacketInfo(id = 0x0F, serverSide = false)
public class RegistrationFailedPacket extends Packet {

	public RegistrationFailedPacket(String reason) {
		super(new Object[] {reason});
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		clientConnection.getTextFlowInstance().setRegisterErrorMessage(getReason());
	}
	
	public String getReason() {
		return (String) data[0];
	}

}
