package screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import mainWindow.MainWindow;

/**
 *
 * Abstract Class
 * 
 */
public abstract class Screen{
    AsciiPanel terminal;
    MainWindow mainWindow;
    protected int gameStage;
    public static final String[] backgroundStringName={
        "Soil","Grass","Water"
    };

    /**
     * @param terminal
     */
    public Screen(AsciiPanel terminal,MainWindow mainWindow,int gameStage) {
        this.terminal=terminal;
        this.mainWindow=mainWindow;
        this.gameStage=gameStage;
    }

    /**
     * Modify 2D arrays in AsciiPanel
     */
    abstract public void displayOutput();

    abstract public void respondToUserInput(KeyEvent key);

    public int getGameStage(){
        return gameStage;
    }
}
