package roguelike.ui;
import java.util.TimerTask;

import roguelike.mainWindow.MainWindow;

/**
 * Controlling the UI of the Game,repaint at a certain frequency in another THREAD. 
 * This class is an Abstraction of the UI Controlling function of Class Main in jw04
 * 
 */
public class UIPainter extends TimerTask{ //
    public static final int repaintInterval=20; //in millises
    private MainWindow mainWindow;
    public UIPainter(MainWindow mainWindow){
        this.mainWindow=mainWindow;
    }
    
    @Override
    public void run() {
        refresh();
    }

    public void refresh() {           //刷新UI,定时调用减少刷新率
        mainWindow.repaint();
        //System.out.println(LocalTime.now());
    }
}
