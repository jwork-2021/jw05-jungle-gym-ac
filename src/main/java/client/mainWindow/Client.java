package client.mainWindow;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.sound.sampled.LineUnavailableException;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * 
 * Test client for NIO server
 *
 */
public class Client {

	MainWindow mainWindow;

	private ByteBuffer readBuffer,writeBuffer;
	private InetSocketAddress hostAddress;
	private SocketChannel client;

	public Client(){
		hostAddress= new InetSocketAddress("localhost", 9093);

		//String threadName = Thread.currentThread().getName();

		// Send messages to server
		//String[] messages = new String[] { threadName + ": msg1", threadName + ": msg2", threadName + ": msg3" };

		/*for (int i = 0; i < messages.length; i++) {
			ByteBuffer buffer = ByteBuffer.allocate(74);
			buffer.put(messages[i].getBytes());
			buffer.flip();
			client.write(buffer); //Writes a sequence of bytes to this channel from the given buffer.
			System.out.println(messages[i]);
			buffer.clear();
			Thread.sleep(5000);
		}*/
		//client.close();
	}
	private void startClient() throws IOException{
		client= SocketChannel.open(hostAddress);
        readBuffer = ByteBuffer.allocate(MainWindow.width*MainWindow.height*2*(Character.SIZE/Byte.SIZE)); //chars and effects	
		writeBuffer=ByteBuffer.allocate(64);

		try {
			mainWindow=new MainWindow(this);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		System.out.println("Client... started");
		
		while(client.isOpen()){
			readFromChannel();
		}
	}

	public static void main(String[] args) {
		try {
			new Client().startClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToChannel(int keyCode){
		writeBuffer.clear();
		writeBuffer.putInt(keyCode);
		writeBuffer.flip();
		try {
			client.write(writeBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeBuffer.clear();
	}
	private void readFromChannel(){
		readBuffer.clear();
		int readBytes=-1;
		try {
			while(readBuffer.hasRemaining()){//buffer not full,position()!=capacity or limit
				readBytes=client.read(readBuffer);
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		readBuffer.flip();
		//mainWindow.repaint();
		readBuffer.clear();
	}
}
