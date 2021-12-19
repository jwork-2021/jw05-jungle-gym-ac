package roguelike.screen;

import roguelike.asciiPanel.AsciiPanel;
import roguelike.mainWindow.MainWindow;

public class StartScreen extends RestartScreen {

    public StartScreen(AsciiPanel terminal,MainWindow mainWindow,int gameStage){
        super(terminal,mainWindow,RestartScreen.lose,gameStage);
    }
    @Override
    public void displayOutput() {
        //TODO:画大的游戏标题
        //规则说明：图标+文字，不同颜色
        terminal.writeCenter(MessageList.get("icon"),mainWindow.height/2-4,AsciiPanel.green,AsciiPanel.white);
        terminal.writeCenter(MessageList.get("title"),mainWindow.height/2-3,AsciiPanel.green,AsciiPanel.white);

        terminal.writeCenter(MessageList.get("archer_message"),mainWindow.height/2-1,AsciiPanel.green,AsciiPanel.white);
        terminal.writeCenter(MessageList.get("wizard_message"),mainWindow.height/2-0,AsciiPanel.green,AsciiPanel.white);
        terminal.writeCenter(MessageList.get("exit_message"),mainWindow.height/2+1,AsciiPanel.green,AsciiPanel.white);
    }

}
