package screen;

import asciiPanel.AsciiPanel;
import game.Player;
import game.World;
import mainWindow.MainWindow;

import java.awt.event.KeyEvent;
import java.util.HashMap;
//import java.util.ArrayList;

public class RestartScreen extends Screen {
    
    protected HashMap<String,String> MessageList=new HashMap<String,String>();
    public static final int win=1;
    public static final int lose=0;
    private int state;
    
    @Override
    public void displayOutput(){
        for(int x=0;x<MainWindow.width;x++)
        for(int y=0;y<MainWindow.height;y++){
            terminal.write((char)0,x,y,AsciiPanel.white);
        }
        //terminal.writeCenter(MessageList.get("icon"),MainWindow.height/2-4,AsciiPanel.green,AsciiPanel.white);
        //erminal.writeCenter(MessageList.get("title"),MainWindow.height/2-3,AsciiPanel.green,AsciiPanel.white);
        if(state==win)
            terminal.writeCenter(MessageList.get("win_message"),MainWindow.height/2-1,AsciiPanel.green,AsciiPanel.white);
        else
            terminal.writeCenter(MessageList.get("lose_message"),MainWindow.height/2-1,AsciiPanel.green,AsciiPanel.white);

        terminal.writeCenter(MessageList.get("archer_message"),MainWindow.height/2-0,AsciiPanel.green,AsciiPanel.white);
        terminal.writeCenter(MessageList.get("wizard_message"),MainWindow.height/2+1,AsciiPanel.green,AsciiPanel.white);
        terminal.writeCenter(MessageList.get("exit_message"),MainWindow.height/2+2,AsciiPanel.green,AsciiPanel.white);

    }
        

        //terminal.write(win_message,(World.WIDTH-win_message.length())/2, (World.HEIGHT-num_of_lines)/2);
        //terminal.write(continue_message,(World.WIDTH-continue_message.length())/2, (World.HEIGHT-num_of_lines)/2+1);
        //terminal.write(exit_message,(World.WIDTH-exit_message.length())/2, (World.HEIGHT-num_of_lines)/2+2);
    
    /**
     * 
     * 
     * 
     * @param terminal
     */
    public RestartScreen(AsciiPanel terminal,MainWindow mainWindow,int state,int gameStage){
        super(terminal,mainWindow,(char)0,gameStage);
        this.state=state;

        String icon="";
        for(int i=0;i<6;i++)  icon+=Character.toString((char)AsciiPanel.archerIndex+i);
        MessageList.put("icon",icon);
        MessageList.put("title", "Roguelike");
        MessageList.put("lose_message", "You DIED!!!Try STAGE "+Integer.toString(gameStage) +" AGAIN!");
        MessageList.put("win_message","You WIN!!! Challenge STAGE "+Integer.toString(gameStage)+"!");
        MessageList.put("archer_message","Choose "+Character.toString(AsciiPanel.archerIndex)+" ->press A");
        MessageList.put("wizard_message","Choose "+Character.toString(AsciiPanel.wizardIndex)+" ->press B");
        MessageList.put("exit_message", "CLOSE the terminal to EXIT...");
        
        //displayOutput();

    }

    @Override
    public void respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_A:
                mainWindow.setScreen(new WorldScreen(terminal,mainWindow,Player.ARCHER,gameStage));
                break;
            case KeyEvent.VK_B:
                mainWindow.setScreen(new WorldScreen(terminal,mainWindow,Player.WIZARD,gameStage));
                break;
            default:
                return;
        }
    }
}
