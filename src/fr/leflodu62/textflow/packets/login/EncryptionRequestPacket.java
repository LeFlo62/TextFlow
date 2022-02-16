package fr.leflodu62.textflow.packets.login;

import java.security.PublicKey;

import javax.crypto.SecretKey;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.ClientConnection.State;
import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketInfo;
import fr.leflodu62.textflow.secure.AESHelper;
import fr.leflodu62.textflow.secure.RSAHelper;

@PacketInfo(id = 0x01, serverSide = false)
public class EncryptionRequestPacket extends Packet {

	public EncryptionRequestPacket(PublicKey publicKey, String verifyToken) {
		super(new Object[] {publicKey, verifyToken});
	}
	
	@Override
	public void processData(ClientConnection clientConnection, Packet packet) {
		try {
			final SecretKey key = AESHelper.genKey();
			final PublicKey publicKey = getPublicKey();
			clientConnection.setState(State.ENCRYPTION);
			clientConnection.sendPacket(new EncryptionResponsePacket(RSAHelper.encrypt(key, publicKey), RSAHelper.encrypt(getVerifyToken(), publicKey)));
			clientConnection.setSecretKey(key);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public PublicKey getPublicKey() {
		return (PublicKey) data[0];
	}
	
	public String getVerifyToken() {
		return (String) data[1];
	}

}
