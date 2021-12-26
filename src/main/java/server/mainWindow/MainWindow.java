package server.mainWindow;

import javax.sound.sampled.LineUnavailableException;


import server.asciiPanel.AsciiFont;
import server.asciiPanel.AsciiPanel;
import server.music.BGM;
import server.screen.Screen;
import server.screen.StartScreen;
/**
 * The Main Window class of the game 
 */
public class MainWindow{  
    private EchoNIOServer server;
    private AsciiPanel terminal;
    private Screen screen;
    //private UIPainter uiPainter;
    //private Timer uiTimer;
    public static final int width=55;
    public static final int height=30;

    public MainWindow(EchoNIOServer server) throws LineUnavailableException {

    //create AsciiPanel,add as a component
        terminal = new AsciiPanel(width, height, AsciiFont.CP437_32x32);   //defaultBackgroundColor = black;defaultForegroundColor = white;
    
    //Screen
    setScreen(new StartScreen(terminal,this,0));  //开始屏幕

    //Server
    this.server=server;
  
    }

    
    public void repaint() {
        screen.displayOutput();
    }

    public void respondToUserInput(int KeyCode,int playerNumber){
        screen.respondToUserInput(KeyCode, playerNumber);
    }
    

    public void setScreen(Screen screen){
        this.screen=screen;
    }
    public Screen getScreen(){
        return screen;
    }
    public char getChar(int x,int y){
        return terminal.getChar(x,y);
    }
    public char getEffect(int x,int y){
        return terminal.getEffect(x,y);
    }
    public char getBackgroundImageIndex(){
        return terminal.backgroundImageIndex;
    }
}
