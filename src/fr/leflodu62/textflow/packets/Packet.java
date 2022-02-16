package fr.leflodu62.textflow.packets;

import fr.leflodu62.textflow.ClientConnection;

public abstract class Packet {

	protected final Object[] data;
	
	public Packet(Object[] data) {
		this.data = data;
	}
	
	public Object[] getData() {
		return data;
	}
	
	public void processData(ClientConnection clientConnection, Packet packet) {}

}
