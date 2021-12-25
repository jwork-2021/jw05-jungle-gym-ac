package server.mainWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import server.asciiPanel.AsciiFont;
import server.asciiPanel.AsciiPanel;
import server.music.BGM;
import server.screen.Screen;
import server.screen.StartScreen;
import server.ui.UIPainter;
/**
 * The Main Window class of the game 
 */
public class MainWindow{ //extends JFrame implements KeyListener {  

    private AsciiPanel terminal;
    private Screen screen;
    private UIPainter uiPainter;
    private Timer uiTimer;
    //public BGM bgm;
    public static final int width=55;
    public static final int height=30;
    //TODO:different gameStages Restartscreen()中win了，gameStage++

    public MainWindow(){ //throws LineUnavailableException {
        //super("Roguelike");
        //setIconImage(new ImageIcon(MainWindow.class.getClassLoader().getResource("images/icon.png")).getImage());

    //create AsciiPanel,add as a component
        terminal = new AsciiPanel(width, height, AsciiFont.CP437_32x32);   //defaultBackgroundColor = black;defaultForegroundColor = white;
        //add(terminal);
        //pack();
    
    //BGM,need to set before setScreen()
    //bgm=new BGM(this);

    //Screen
        setScreen(new StartScreen(terminal,this,0));  //开始屏幕

    //keyListener
        //addKeyListener(this);
        //TODO:implement a KeyListener's SubClass instead of mixing KeyListner and Main Function Together 
        //change addKeyListener(this) to addKeyListener(.....)
    //UIPainter
        uiPainter=new UIPainter(this);
        uiTimer=new Timer();
        uiTimer.scheduleAtFixedRate(uiPainter, 0,UIPainter.repaintInterval);    
    }

    
    /*@Override
    public void repaint() {
        screen.displayOutput();
        super.repaint();
    }*/


    public void refresh(){
        screen.displayOutput();
    }

    public void respondToUserInput(int KeyCode,int playerNumber){
        screen.respondToUserInput(KeyCode, playerNumber);
    }

    public void setScreen(Screen screen){
        this.screen=screen;
        /*try {
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
        }*/
    }
    public Screen getScreen(){
        return screen;
    }

}
