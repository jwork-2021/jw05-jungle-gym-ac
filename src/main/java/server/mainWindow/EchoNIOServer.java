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
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;

import javax.sound.sampled.LineUnavailableException;

import server.ui.UIPainter;

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
	public MainWindow mainWindow;
	public static final int PlayerNumber=2;
	private int connectedPlayerNumber=0;


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
		writeBuffer = ByteBuffer.allocate(Character.SIZE/Byte.SIZE+MainWindow.width*MainWindow.height*2*(Character.SIZE/Byte.SIZE)); //chars and effects	

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
		
		new Timer().scheduleAtFixedRate(new UIPainter(this), 500,UIPainter.repaintInterval);

		//read & write mode
		while (true) { 
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

		//TODO:Write and read 1 key at a time,or mutiple?
		numRead=numRead/(Integer.SIZE/Byte.SIZE);
		readBuffer.flip();
		for (int i=0;i<numRead;i++){
			mainWindow.respondToUserInput(readBuffer.getInt(),(Integer)key.attachment());
		}
		readBuffer.clear();
	}
	private void write(SelectionKey key){
		SocketChannel channel = (SocketChannel) key.channel();
		writeBuffer.clear();
		//backgroundImage
		writeBuffer.putChar(mainWindow.getBackgroundImageIndex());
		for(int x=0;x<MainWindow.width;x++)
            for(int y=0;y<MainWindow.height;y++){
				writeBuffer.putChar(mainWindow.getChar(x,y));
			}
		for(int x=0;x<MainWindow.width;x++)
            for(int y=0;y<MainWindow.height;y++){
				writeBuffer.putChar(mainWindow.getEffect(x,y));
			}
		writeBuffer.flip();
		int writeBytes=-1;
		while(writeBuffer.hasRemaining()){
			try {
				writeBytes=channel.write(writeBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writeBuffer.clear();
	}

	public void writeToAllChannels(){
			// process every key...
			Set<SelectionKey> keys = selector.keys();
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();
	
				if (!key.isValid()||key.attachment()==null) { //skip the listening channel
					continue;
				}
				this.write(key);
			}
	}

}
