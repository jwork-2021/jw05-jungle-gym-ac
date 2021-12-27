package roguelike.screen;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import roguelike.asciiPanel.AsciiPanel;
import roguelike.game.creature.Player;
import roguelike.mainWindow.MainWindow;
/**
 *
 * Abstract Class
 * 
 */
public abstract class Screen{
    AsciiPanel terminal;
    MainWindow mainWindow;
    
    protected int gameStage;
    protected int archiveNumber;

    public static final int archer=0,wizard=1;
    protected int character=archer;

    protected static final String cursorString=Character.toString(AsciiPanel.stringCharMap.get("Cursor"));

    protected static HashMap<String,String> MessageList=new HashMap<String,String>();

    public static final String[] backgroundStringName={
        "Soil","Grass","Water"
    };


    /**
     * @param terminal
     */
    public Screen(AsciiPanel terminal,MainWindow mainWindow,int gameStage,int archiveNumber) {
        this.terminal=terminal;
        this.mainWindow=mainWindow;
        this.gameStage=gameStage;
        this.archiveNumber=archiveNumber;
    }

    /**
     * Modify 2D arrays in AsciiPanel
     */
    abstract public void displayOutput();

    abstract public void respondToUserInput(KeyEvent key);

    public int getGameStage(){
        return gameStage;
    }

    public void clearScreen(){
        for(int x=0;x<MainWindow.width;x++)
            for(int y=0;y<MainWindow.height;y++){
                terminal.write((char)0,x,y);
                terminal.writeEffect((char)0, x, y);
        }
    }

    static{
        MessageList.put("choose_character_message","Choose your HERO!");
        MessageList.put("archer_message","Choose Archer"+Character.toString(AsciiPanel.stringCharMap.get("ArcherDown"))
                            +Character.toString(AsciiPanel.stringCharMap.get("BowRight")));
        MessageList.put("wizard_message","Choose Wizard"+Character.toString(AsciiPanel.stringCharMap.get("WizardDown"))
                            +Character.toString(AsciiPanel.stringCharMap.get("WandRight")));

        MessageList.put("saved_message", "Your game progress has been SAVED!");            
        MessageList.put("exit_message", "CLOSE the terminal to EXIT...");


        MessageList.put("title", "Roguelike");
        String icon="";
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("ArcherDown")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("WizardDown")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("BowRight")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("WandRight")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("DemonDown")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("GoblinDown")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("VampireDown")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("ArrowRight")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("Fire")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("Lightning")));
        icon+=Character.toString((char) (AsciiPanel.stringCharMap.get("Castle")));
        MessageList.put("icon",icon);
    }

    protected void respondToCharacterChossingInput(KeyEvent key){
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                switch(character){
                    case archer:
                        mainWindow.setScreen(new WorldScreen(terminal,mainWindow,Player.ARCHER,gameStage,archiveNumber));
                        break;
                    default:
                        mainWindow.setScreen(new WorldScreen(terminal,mainWindow,Player.WIZARD,gameStage,archiveNumber));
                        break;
                }
                break;
            case KeyEvent.VK_UP:
                character=archer;
                break;
            case KeyEvent.VK_DOWN:
                character=wizard;
                break;
        }
    }

    public void displayCharacterChoosingMessage(){
        int startLineNumber=MainWindow.height/2-2;
        terminal.writeCenter(MessageList.get("choose_character_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);
        if(character==archer){
            terminal.writeCenter(cursorString+MessageList.get("archer_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);
            terminal.writeCenter(MessageList.get("wizard_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);
        }
        else{
            terminal.writeCenter(MessageList.get("archer_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);
            terminal.writeCenter(cursorString+MessageList.get("wizard_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);
        }
        terminal.writeCenter(MessageList.get("exit_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);

    }

  

}
