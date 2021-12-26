package server.mainWindow;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

/**
 * 
 * This is a simple NIO based server.
 *
 */
public class EchoNIOServer {
	private Selector selector;

	private InetSocketAddress listenAddress;
	private final static int PORT = 9093;

	private ByteBuffer readBuffer,writeBuffer;
	//modified
	private MainWindow mainWindow;
	public static final int PlayerNumber=2;
	//private final HashMap<SelectionKey,Integer> KeyPlayerIdMap=new HashMap<>();
	private int connectedPlayerNumber=0;
	//


	public static void main(String[] args) throws Exception {
		try {
			new EchoNIOServer("localhost", 9093).startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public EchoNIOServer(String address, int port) throws IOException {
		listenAddress = new InetSocketAddress(address, PORT);
	}

	/**
	 * Start the server
	 * 
	 * @throws IOException
	 */
	private void startServer() throws IOException {
		readBuffer=ByteBuffer.allocate(64);
		writeBuffer = ByteBuffer.allocate(MainWindow.width*MainWindow.height*2*(Character.SIZE/Byte.SIZE)); //chars and effects	

		this.selector = Selector.open();
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);

		// bind server socket channel to port
		serverChannel.socket().bind(listenAddress);
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

		System.out.println("Server started on port >> " + PORT);

		//accept mode
		while (connectedPlayerNumber<PlayerNumber) {
			// wait for events
			int readyCount = selector.select();
			if (readyCount == 0) {
				continue;
			}

			// process selected keys...
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();

				// Remove key from set so we don't process it twice
				iterator.remove();

				if (!key.isValid()) {
					continue;
				}

				if (key.isAcceptable()) { // Accept client connections
					this.accept(key);
				} 
			}
		}
		System.out.println("finish connecting");
		//notify all,set the Game
		try {
			mainWindow=new MainWindow(this);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		


		//read & write mode
		while (selector.keys().size()>0) { 
			// wait for events
			
			int readyCount = selector.select();
			if (readyCount == 0) {
				continue;
			}

			// process selected keys...
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();

				// Remove key from set so we don't process it twice
				iterator.remove();

				if (!key.isValid()) {
					continue;
				}
				
				if (key.isReadable()) { // Read from client
					this.read(key);
				} else if (key.isWritable()) {
					// write data to client...
				}
			}
		}
	}

	// accept client connection
	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		System.out.println("Connected to: " + remoteAddr);

		/*
		 * Register channel with selector for further IO (record it for read/write
		 * operations, here we have used read operation)
		 */
		channel.register(this.selector, SelectionKey.OP_READ,connectedPlayerNumber);

		connectedPlayerNumber++;
		System.out.println("connectedPlayerNumber: "+connectedPlayerNumber);
		
	}

	// read from the socket channel
	private void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		readBuffer.clear();
		int numRead = -1;
		numRead = channel.read(readBuffer);

		if (numRead == -1) {
			Socket socket = channel.socket();
			SocketAddress remoteAddr = socket.getRemoteSocketAddress();
			System.out.println("Connection closed by client: " + remoteAddr);
			channel.close();
			key.cancel();
			return;
		}

		/*byte[] data = new byte[numRead];
		System.arraycopy(readBuffer.array(), 0, data, 0, numRead);		
		System.out.println("Got: " + new String(data));*/

		//TODO:Write and read 1 key at a time,or mutiple?
		numRead=numRead/(Integer.SIZE/Byte.SIZE);
		readBuffer.flip();
		//System.out.println(numRead);
		for (int i=0;i<numRead;i++){
			mainWindow.respondToUserInput(readBuffer.getInt(),(Integer)key.attachment());
		}
		readBuffer.clear();
	}
}
