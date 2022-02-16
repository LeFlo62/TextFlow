package fr.leflodu62.textflow.packets;

import java.nio.channels.AlreadyBoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import fr.leflodu62.textflow.ClientConnection;
import fr.leflodu62.textflow.TextFlow;
import fr.leflodu62.textflow.packets.connection.DisconnectionPacket;
import fr.leflodu62.textflow.packets.connection.ForcedClosedConnectionPacket;
import fr.leflodu62.textflow.packets.connection.RefreshTokenPacket;
import fr.leflodu62.textflow.packets.login.EncryptionReadyPacket;
import fr.leflodu62.textflow.packets.login.EncryptionRequestPacket;
import fr.leflodu62.textflow.packets.login.EncryptionResponsePacket;
import fr.leflodu62.textflow.packets.login.LoginFailedPacket;
import fr.leflodu62.textflow.packets.login.LoginPacket;
import fr.leflodu62.textflow.packets.login.LoginStartPacket;
import fr.leflodu62.textflow.packets.login.LoginSuccessPacket;
import fr.leflodu62.textflow.packets.message.ClientMessagePacket;
import fr.leflodu62.textflow.packets.message.MessageNotSentPacket;
import fr.leflodu62.textflow.packets.message.MessagePacket;
import fr.leflodu62.textflow.packets.message.ServerMessagePacket;
import fr.leflodu62.textflow.packets.register.RegisterPacket;
import fr.leflodu62.textflow.packets.register.RegisterStartPacket;
import fr.leflodu62.textflow.packets.register.RegistrationFailedPacket;

public class PacketRegistry {
	
	private static final Map<Integer, Class<? extends Packet>> REGISTRY = new HashMap<>(); 
	
	public static void registerPackets() {
		registerPacket(LoginStartPacket.class); // 0x00
		registerPacket(EncryptionRequestPacket.class); // 0x01
		registerPacket(EncryptionResponsePacket.class); // 0x02
		registerPacket(EncryptionReadyPacket.class); // 0x03
		registerPacket(LoginPacket.class); // 0x04
		registerPacket(LoginFailedPacket.class); // 0x05
		registerPacket(LoginSuccessPacket.class); // 0x06
		registerPacket(ClientMessagePacket.class); // 0x07
		registerPacket(ForcedClosedConnectionPacket.class); // 0x08
		registerPacket(MessagePacket.class); // 0x09
		registerPacket(DisconnectionPacket.class); // 0x0A
		registerPacket(ServerMessagePacket.class); // 0x0B
		registerPacket(RefreshTokenPacket.class); // 0x0C
		registerPacket(RegisterStartPacket.class); // 0x0D
		registerPacket(RegisterPacket.class); //0x0E
		registerPacket(RegistrationFailedPacket.class); // 0x0F
		registerPacket(MessageNotSentPacket.class); // 0x10
	}
	
	public static int getId(Class<? extends Packet> class1) {
		if(class1.isAnnotationPresent(PacketInfo.class)) {
			return class1.getAnnotation(PacketInfo.class).id();
		}
		return -1;
	}
	
	public static boolean isServerSide(int id) {
		if(REGISTRY.containsKey(id)) {
			if(REGISTRY.get(id).isAnnotationPresent(PacketInfo.class)) {
				return REGISTRY.get(id).getAnnotation(PacketInfo.class).serverSide();
			}
		}
		return false;
	}
	
	public static boolean isServerSide(Class<? extends Packet> class1) {
		return isServerSide(getId(class1));
	}
	
	public static void processPacket(ClientConnection clientConnection, int id, Object[] data) {
		if(REGISTRY.containsKey(id)) {
			if(!isServerSide(id)) {
				try {
					final Packet packet = (Packet) REGISTRY.get(id).getConstructors()[0].newInstance(data);
					REGISTRY.get(id).cast(packet).processData(clientConnection, packet);
				} catch (final Exception e) {
					TextFlow.LOGGER.log(Level.SEVERE, "Packet(" + Integer.toHexString(id) + ", " + isServerSide(id)+")", e);
					e.printStackTrace();
					for(final Object  o : data) System.out.println(o.toString());
				}
			}
		}
	}

	private static void registerPacket(Class<? extends Packet> class1) {
		if(class1.isAnnotationPresent(PacketInfo.class)) {
			final PacketInfo id = class1.getAnnotation(PacketInfo.class);
			
			if(REGISTRY.containsKey(id.id())) {
				throw new AlreadyBoundException();
			}
			
			REGISTRY.put(id.id(), class1);
		}
	}

}
