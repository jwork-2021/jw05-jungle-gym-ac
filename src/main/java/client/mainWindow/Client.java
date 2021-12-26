package client.mainWindow;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.sound.sampled.LineUnavailableException;

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
	}
	private void startClient() throws IOException{
		client= SocketChannel.open(hostAddress);
        readBuffer = ByteBuffer.allocate(Character.SIZE/Byte.SIZE+MainWindow.width*MainWindow.height*2*(Character.SIZE/Byte.SIZE)); //chars and effects	
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

		mainWindow.setBackgroundImageIndex(readBuffer.getChar());
		for(int x=0;x<MainWindow.width;x++)
            for(int y=0;y<MainWindow.height;y++){
				mainWindow.writeChar(readBuffer.getChar(),x,y);
			}
		for(int x=0;x<MainWindow.width;x++)
            for(int y=0;y<MainWindow.height;y++){
				mainWindow.writeEffect(readBuffer.getChar(),x,y);
			}		
		mainWindow.repaint();

		readBuffer.clear();
	}
}
