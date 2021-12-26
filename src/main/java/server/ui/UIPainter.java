package server.ui;
import java.util.TimerTask;

import server.mainWindow.EchoNIOServer;
import server.mainWindow.MainWindow;

/**
 * Controlling the UI of the Game,repaint at a certain frequency in another THREAD. 
 * This class is an Abstraction of the UI Controlling function of Class Main in jw04
 * 
 */
public class UIPainter extends TimerTask{ //
    public static final int repaintInterval=90; //in millises
    private EchoNIOServer server;
    public UIPainter(EchoNIOServer server){
        this.server=server;
    }
    
    @Override
    public void run() {
        server.mainWindow.repaint();
        server.writeToAllChannels();
    }

}
