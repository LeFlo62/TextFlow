package fr.leflodu62.textflow;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;

import fr.leflodu62.textflow.packets.Packet;
import fr.leflodu62.textflow.packets.PacketRegistry;
import fr.leflodu62.textflow.packets.login.LoginPacket;
import fr.leflodu62.textflow.packets.login.LoginStartPacket;
import fr.leflodu62.textflow.packets.register.RegisterPacket;
import fr.leflodu62.textflow.packets.register.RegisterStartPacket;
import fr.leflodu62.textflow.secure.AESHelper;
import fr.leflodu62.textflow.ui.LoginFrame;
import fr.leflodu62.textflow.ui.RegisterFrame;

public class ClientConnection implements Runnable {

	private final TextFlow instance;
	private final String username;
	private String remoteAddress;
	
	private String token;

	private Socket client;
	private Thread clientThread;
	
	private State state = State.NONE;

	private final ConcurrentLinkedQueue<Pair<Packet, Runnable>> data;

	private SecretKey secretKey;

	private boolean encryption;

	private Runnable connectionEstablished;
	
	//TODO: verify client hash / md5 -> server

	public ClientConnection(TextFlow login, String username) {
		this.instance = login;
		this.username = username;
		this.data = new ConcurrentLinkedQueue<>();
	}
	
	public void attemptLogin(String address, Runnable r) {
		createSecureConnection(address, new LoginStartPacket(username), r);
	}
	
	public void attemptRegister(String address, Runnable r) {
		createSecureConnection(address, new RegisterStartPacket(username), r);
	}

	public void createSecureConnection(String address, Packet packet, Runnable r) {
		try {
			int port = 19842;
			if(address.contains(":") && NumberUtils.isDigits(address.split(":")[1])) {
				port = Integer.parseInt(address.split(":")[1]);
			}
			remoteAddress = address.split(":")[0] + ":" + port;
			client = new Socket(address.split(":")[0], port);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		clientThread = new Thread(this);
		clientThread.start();
		sendPacket(packet);

		this.connectionEstablished = r;
	}

	public void connect(LoginFrame frame, String username, String password) {
		try {
			if(encryption) {
				sendPacket(new LoginPacket(username, password));
			} else {
				frame.setErrorMessage("Connexion non sécurisée");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public void register(RegisterFrame frame, String username, String password) {
		try {
			if(encryption) {
				sendPacket(new RegisterPacket(username, password));
			} else {
				frame.setErrorMessage("Connexion non sécurisée");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			final OutputStream out = client.getOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(out);

			final InputStream in = client.getInputStream();
			final ObjectInputStream ois = new ObjectInputStream(in);
			
			while (!client.isClosed()) {
				for (byte i = 0; i < 8; i++) {
					final Pair<Packet, Runnable> bundle = data.poll();

					//TODO: voir le serveur pour copier le try-catch.
					//TODO: faire un système d'anti spam
					if (!client.isClosed() && bundle != null) {
						final Packet packetToSend = bundle.getLeft();
						out.write(PacketRegistry.getId(packetToSend.getClass()));
						out.flush();

						final Object[] packetData = packetToSend.getData();
						
						if (null != packetData) {
							if (encryption) {
								Object[] dataToSend = packetData;
								if(token != null && !token.isEmpty()) {
									dataToSend = new Object[packetData.length+1];
									dataToSend[0] = token;
									System.arraycopy(packetData, 0, dataToSend, 1, packetData.length);
								}
								final SealedObject data = AESHelper.encrypt(dataToSend, secretKey);
								oos.writeObject(data);
							} else {
								oos.writeObject(packetData);
							}
						}
						oos.flush();
						if(bundle.getRight() != null) {
							bundle.getRight().run();
						}
					}
					
					if (!client.isClosed() && in.available() > 0) {
						final int id = in.read();
						Object[] data = null;
						if(in.available() > 0) {
							ois.skip(Integer.BYTES);
							if (encryption) {
								data = (Object[]) AESHelper.decrypt((SealedObject)ois.readObject(), secretKey);
							} else {
									data = (Object[]) ois.readObject();
							}
						}
						PacketRegistry.processPacket(this, id, data);
					}
				}
				Thread.sleep(125);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			client.close();
			data.clear();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void sendPacket(Packet packet) {
		sendPacket(packet, null);
	}

	public void sendPacket(Packet packet, Runnable runnable) {
		this.data.add(Pair.of(packet, runnable));
	}
	
	public TextFlow getTextFlowInstance() {
		return instance;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}

	public void enableEcryption() {
		this.encryption = true;
	}
	
	public Runnable getConnectionEstablished() {
		return connectionEstablished;
	}
	
	public static enum State {
		NONE, ENCRYPTION, LOGGED;
	}

}
