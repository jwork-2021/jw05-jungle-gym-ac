package client.mainWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import client.asciiPanel.AsciiFont;
import client.asciiPanel.AsciiPanel;
import client.music.BGM;
/**
 * The Main Window class of the game 
 */
public class MainWindow extends JFrame implements KeyListener {  

    private Client client;
    private AsciiPanel terminal;

    //public BGM bgm;

    public static final int width=55;
    public static final int height=30;

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

        addKeyListener(this);
     
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
		client.writeToChannel(e.getKeyCode());
    }

    public void writeChar(char ch,int x,int y){
        terminal.write(ch,x,y);
    }
    public void writeEffect(char effect,int x,int y){
        terminal.writeEffect(effect, x, y);
    }

    public void setBackgroundImageIndex(char index){
        terminal.backgroundImageIndex=index;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
