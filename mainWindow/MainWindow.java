package mainWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ui.UIPainter;
import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import game.World;
import screen.StartScreen;
import screen.Screen;
import ui.UIPainter;

/**
 * The Main Window class of the game 
 */
public class MainWindow extends JFrame implements KeyListener {  

    private AsciiPanel terminal;
    private Screen screen;
    private UIPainter uiPainter;
    private Timer uiTimer;
    public int gameStage;
    public static final int width=50;
    public static final int height=24;
    //TODO:different gameStages Restartscreen()中win了，gameStage++

    //TODO:给mainWindow加上标题 icon等
    public MainWindow() {
        super("Roguelike");
        setIconImage(new ImageIcon(MainWindow.class.getClassLoader().getResource("resources/icon.png")).getImage());

    //create AsciiPanel,add as a component
        terminal = new AsciiPanel(width, height, AsciiFont.CP437_32x32);   //defaultBackgroundColor = black;defaultForegroundColor = white;
        add(terminal);
        pack();

    //Screen
        screen = new StartScreen(terminal,this,1);  //开始屏幕
        //uiPainter.refresh();

    //keyListener
        addKeyListener(this);
        //TODO:implement a KeyListener's SubClass instead of mixing KeyListner and Main Function Together 
        //change addKeyListener(this) to addKeyListener(.....)

    //UIPainter
        uiPainter=new UIPainter(this);
        uiTimer=new Timer();
        uiTimer.scheduleAtFixedRate(uiPainter, 0,UIPainter.repaintInterval);    
    }

    
    @Override
    public void repaint() {
        screen.displayOutput();
        super.repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen.respondToUserInput(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setScreen(Screen screen){
        this.screen=screen;
    }

    public static void main(String[] args) {
        MainWindow app = new MainWindow();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

}
