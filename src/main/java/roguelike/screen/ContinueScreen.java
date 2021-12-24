package roguelike.screen;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
//import java.util.ArrayList;

import roguelike.asciiPanel.AsciiPanel;
import roguelike.game.creature.Player;
import roguelike.mainWindow.MainWindow;

public class ContinueScreen extends Screen {
    
    public static final int win=1,lose=0;
    protected int winLoseState;
    
    //TODO:replay
    private static final int replayOrContinue=0,chooseCharacter=1;
    protected int userInterfaceState;

    /**
     * 
     * 
     * 
     * @param terminal
     */
    public ContinueScreen(AsciiPanel terminal,MainWindow mainWindow,int winLoseState,int gameStage,int archiveNumber){
        super(terminal,mainWindow,gameStage,archiveNumber);
        terminal.backgroundImageIndex=(char)0;
        this.winLoseState=winLoseState;
        this.userInterfaceState=replayOrContinue;

        MessageList.put("lose_message", "You DIED! Press ENTER try STAGE "+Integer.toString(gameStage) +" AGAIN!");
        MessageList.put("win_message","You WIN! Press ENTER to challenge STAGE "+Integer.toString(gameStage)+"!");

        //write Archive
            try {
                writeArchive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
    }

    @Override
    public void displayOutput(){
        clearScreen();
        if(userInterfaceState==replayOrContinue)
            displayReplayOrContinueMessage();
        else
            displayCharacterChoosingMessage();
    }
        

        //terminal.write(win_message,(World.WIDTH-win_message.length())/2, (World.HEIGHT-num_of_lines)/2);
        //terminal.write(continue_message,(World.WIDTH-continue_message.length())/2, (World.HEIGHT-num_of_lines)/2+1);
        //terminal.write(exit_message,(World.WIDTH-exit_message.length())/2, (World.HEIGHT-num_of_lines)/2+2);
    
    @Override
    public void respondToUserInput(KeyEvent key) {
        if(userInterfaceState==replayOrContinue)
            respondToReplayOrContinueInput(key);
        else
            respondToCharacterChossingInput(key);
    }

    

    public void displayReplayOrContinueMessage(){    
        int startLineNumber=MainWindow.height/2-3;
        if(winLoseState==win){
            terminal.writeCenter(MessageList.get("win_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);
        }
        else
            terminal.writeCenter(MessageList.get("lose_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);
        
        startLineNumber++;//empty Line
        terminal.writeCenter(MessageList.get("saved_message"),startLineNumber++);
        terminal.writeCenter(MessageList.get("exit_message"),startLineNumber++);
   }

    

    public void respondToReplayOrContinueInput(KeyEvent key){
        switch (key.getKeyCode()){
            case KeyEvent.VK_ENTER:
                userInterfaceState=chooseCharacter;
                break;
        }    
    }

    public void writeArchive() throws IOException{
        String fileName="Archive"+Integer.toString(archiveNumber)+".txt";
        BufferedWriter writer=null;
        try{
            writer=new BufferedWriter(new FileWriter(fileName,false));
            writer.write(Integer.toString(gameStage));
        }
        finally{
            if(writer!=null)writer.close();
        }
    }
}
