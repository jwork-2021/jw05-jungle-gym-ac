package client.mainWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import client.asciiPanel.AsciiFont;
import client.asciiPanel.AsciiPanel;
import client.music.BGM;
import client.screen.Screen;
import client.screen.StartScreen;
import client.ui.UIPainter;
/**
 * The Main Window class of the game 
 */
public class MainWindow extends JFrame implements KeyListener {  

    private Client client;
    private AsciiPanel terminal;
    //private Screen screen;
    //private UIPainter uiPainter;
    private Timer uiTimer;
    //public BGM bgm;
    public static final int width=55;
    public static final int height=30;
    //TODO:different gameStages Restartscreen()中win了，gameStage++

    public MainWindow(Client client) throws LineUnavailableException {
        super("Roguelike");
        setIconImage(new ImageIcon(MainWindow.class.getClassLoader().getResource("images/icon.png")).getImage());

    //create AsciiPanel,add as a component
        terminal = new AsciiPanel(width, height, AsciiFont.CP437_32x32);   //defaultBackgroundColor = black;defaultForegroundColor = white;
        add(terminal);
        pack();
    

        //Client
        this.client=client;
    //BGM,need to set before setScreen()
    //bgm=new BGM(this);
    //setScreen(new StartScreen(terminal,this,0));  //开始屏幕

    //keyListener
        addKeyListener(this);
        //TODO:implement a KeyListener's SubClass instead of mixing KeyListner and Main Function Together 
        //change addKeyListener(this) to addKeyListener(.....)
    //UIPainter
        /*uiPainter=new UIPainter(this);
        uiTimer=new Timer();
        uiTimer.scheduleAtFixedRate(uiPainter, 0,UIPainter.repaintInterval);  */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    @Override
    public void repaint() {
        //screen.displayOutput();
        super.repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
		client.writeToChannel(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /*public void setScreen(Screen screen){
        this.screen=screen;
        try {
            bgm.setAudioStream();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("BGM Interrupted");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("BGM Interrupted");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("BGM Interrupted");
            e.printStackTrace();
        }
    }
    public Screen getScreen(){
        return screen;
    }*/

    /*public static void main(String[] args) {
        MainWindow app;
        try {
            app = new MainWindow();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
        } catch (LineUnavailableException e) {
            System.err.println("BGM Interrupted");
            e.printStackTrace();
        }   
    }*/

}
