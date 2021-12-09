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
    int gameStage;

    /**
     * @param terminal
     */
    public Screen(AsciiPanel terminal,MainWindow mainWindow,char backgroundImageIndex,int gameStage) {
        this.terminal=terminal;
        this.mainWindow=mainWindow;
        this.gameStage=gameStage;
        terminal.backgroundImageIndex=backgroundImageIndex;
    }

    /**
     * Modify 2D arrays in AsciiPanel
     */
    abstract public void displayOutput();

    abstract public void respondToUserInput(KeyEvent key);
}
