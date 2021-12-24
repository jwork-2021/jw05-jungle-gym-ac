package roguelike.screen;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import roguelike.asciiPanel.AsciiPanel;
import roguelike.mainWindow.MainWindow;

public class StartScreen extends Screen {
    private static final int oldOrNewGame=0,chooseHero=1;
    protected int userInterfaceState;

    private int[] gameArchive=new int[6];
    private int cursor=0;

    

    public StartScreen(AsciiPanel terminal,MainWindow mainWindow,int gameStage){
        super(terminal,mainWindow,gameStage,-1);

        //load Archive
        try {
            readArchive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //MessageList.put("OldOrNewGame", "Choose an Archive to Continue or Restart");

    }
    @Override
    public void displayOutput() {
        //TODO:画大号的游戏标题 或者通过把艺术字截成几行？
        //规则说明：图标+文字，不同颜色

        clearScreen();
        terminal.writeCenter(MessageList.get("icon"),MainWindow.height/2-10,AsciiPanel.green,AsciiPanel.white);
        terminal.writeCenter(MessageList.get("title"),MainWindow.height/2-9,AsciiPanel.green,AsciiPanel.white);
        if(userInterfaceState==oldOrNewGame){
            //读出文件中 上一次的Stage？ 考虑第一次玩游戏的情况
            displayOldOrNewGameMessage();
        }
        else{
            displayCharacterChoosingMessage();
        }
    }

    public void displayOldOrNewGameMessage(){
        int startLineNumber=MainWindow.height/2-4;
        String message;
        for(int i=0;i<gameArchive.length;i+=2){
            message="Archive "+Integer.toString(i/2)+": Stage "+Integer.toString(gameArchive[i]);
            if(cursor==i)message=cursorString+message;
            terminal.writeCenter(message, startLineNumber++);

            message="Cover Archive "+Integer.toString(i/2)+" with new Game!";
            if(cursor==i+1)message=cursorString+message;
            terminal.writeCenter(message, startLineNumber++);

            startLineNumber++; //an empty line
        }
        terminal.writeCenter(MessageList.get("exit_message"),startLineNumber++,AsciiPanel.green,AsciiPanel.white);
    }

    @Override
    public void respondToUserInput(KeyEvent key) {
        if(userInterfaceState==oldOrNewGame)
            respondOldOrNewGame(key);
        else
            respondToCharacterChossingInput(key);
    }

    public void respondOldOrNewGame(KeyEvent key){
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                gameStage=gameArchive[cursor];
                userInterfaceState=chooseHero;
                archiveNumber=cursor/2;
                break;
            case KeyEvent.VK_DOWN:
                if(cursor<gameArchive.length-1)cursor++;
                break;
            case KeyEvent.VK_UP:
                if(cursor>0)cursor--;
                break;
        }
    }

    public void readArchive() throws IOException{
        for(int i=0;i<gameArchive.length;i++)
            gameArchive[i]=0;
        
        String fileName,line;
        File file;
        BufferedReader reader=null;
        for(int i=0;i<gameArchive.length;i+=2){
            fileName="Archive"+Integer.toString(i/2)+".txt";
            file=new File(fileName);
            if(file.exists()){
                try{
                    reader=new BufferedReader(new FileReader(file));
                    line=reader.readLine();
                    if(line!=null)gameArchive[i]=Integer.parseInt(line);
                }
                finally{
                    if(reader!=null)reader.close();
                }
            }
        }
    }

}
