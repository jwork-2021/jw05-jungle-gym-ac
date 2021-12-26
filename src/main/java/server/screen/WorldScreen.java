package server.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import server.asciiPanel.AsciiPanel;
import server.game.World;
import server.game.bullet.Bullet;
import server.game.creature.Player;
import server.mainWindow.MainWindow;

public class WorldScreen extends Screen {

    private World world;
    private Player[] players;
    //private Color worldBackgroundColor=;

    /**
     * @param terminal
     */
    public WorldScreen(AsciiPanel terminal,MainWindow mainWindow,int[] playerType,int gameStage,int archiveNumber) {
        super(terminal,mainWindow,gameStage,archiveNumber);
        terminal.backgroundImageIndex= AsciiPanel.stringCharMap.get(backgroundStringName[gameStage%backgroundStringName.length]);
        world = new World(playerType,gameStage);
        players=world.players;
        checkGameEnded();
    }

    public void checkGameEnded(){
        //TODO:kill all Threads in world
        TimerTask task=new TimerTask() {
            public void run(){
                if(gameLost()){
                    //world.empty(player.getX(), player.getY());
                    try{
                        TimeUnit.MILLISECONDS.sleep(3000);
                    }
                    catch (InterruptedException e) { System.err.println("Interrupted");  }
                    mainWindow.setScreen(new ContinueScreen(terminal,mainWindow,ContinueScreen.lose,gameStage,archiveNumber));
                    cancel();
                }
                else if(gameWin()){
                    mainWindow.setScreen(new ContinueScreen(terminal,mainWindow,ContinueScreen.win,gameStage+1,archiveNumber));
                    cancel();
                }
            }
        };
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(task, 0, 100);
    }

    private boolean gameLost(){
        for(Player player:players)
            if(player.getHp()>0)
                return false;
        return true;
    }
    private boolean gameWin(){
        if(world.monsterNumberLeft>0)return false;
        for(Player player:players){
            if(world.map.getEndX()==player.getX()&& world.map.getEndY()==player.getY())
                return true;
        }
        return false;
    }

    @Override
    public void displayOutput() {
    //The GAME area
        //Things
        for (int x = 0; x < world.WIDTH; x++) {
            for (int y = 0; y < world.HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y, AsciiPanel.white);  //color unnecessary
            }
        }
        //EndPoint and Weapon
        if(world.monsterNumberLeft<=0)
            terminal.write(AsciiPanel.stringCharMap.get("Destination"),world.map.getEndX(),world.map.getEndY());
        else{ //终点出现了就不显示武器了
            for(Player player:players){
                if(player.weapon.isVisible()&&player.getHp()>0){
                    terminal.write(player.weapon.getGlyph(),player.weapon.getX(),player.weapon.getY(),AsciiPanel.white);
                }  
            }
        }
        //Bullets
        for (int x = 0; x < world.WIDTH; x++) {
            for (int y = 0; y < world.HEIGHT; y++) {
                terminal.writeEffect((char)0, x, y);  //color unnecessary
            }
        }
        synchronized(world.bullets){
            Iterator<Bullet> iter=world.bullets.iterator();
            Bullet bullet;
            while(iter.hasNext()){
                bullet=iter.next();
                if(bullet.isVisible())
                    terminal.writeEffect(bullet.getGlyph(), bullet.getX(), bullet.getY());
                else 
                    iter.remove();
            }
        }

    //the DATA area
        for(int x=world.WIDTH;x<mainWindow.width;x++)
            for(int y=0;y<mainWindow.height;y++){
                terminal.write((char)0,x,y,AsciiPanel.white);
            }
        int startLineNumber=0;
        terminal.write("Stage "+Integer.toString(gameStage),world.WIDTH,startLineNumber++);
        terminal.write("Press SPACE to attack!",world.WIDTH,startLineNumber++);
        terminal.write(world.monsterNumberLeft+" Monster(s) Left!",world.WIDTH,startLineNumber++);
        startLineNumber++;

        for(int i=0;i<players.length;i++){
            terminal.write("Player "+i+ " HP: "+players[i].getHp(),world.WIDTH,startLineNumber++);
        }
    }

    int i = 0;

    @Override
    public void respondToUserInput(int KeyCode,int playerNumber) {
        synchronized(players[playerNumber].keyEventBuffer){
            players[playerNumber].keyEventBuffer.add(KeyCode);
        }        
    }
}