package screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import asciiPanel.AsciiPanel;
import game.Nothing;
import game.Player;
import game.World;
import game.bullet.Bullet;
import mainWindow.MainWindow;

public class WorldScreen extends Screen {

    private World world;
    private Player player;
    //private Color worldBackgroundColor=;

    /**
     * @param terminal
     */
    public WorldScreen(AsciiPanel terminal,MainWindow mainWindow,int playerType,int gameStage) {
        super(terminal,mainWindow,AsciiPanel.floorIndex,gameStage);

        world = new World(playerType,gameStage);
        player=world.player;
        checkGameEnded();
    }

    public void checkGameEnded(){
        TimerTask task=new TimerTask() {
            public void run(){
                if(player.getHp()<=0){
                    world.empty(player.getX(), player.getY());
                    try{
                        TimeUnit.MILLISECONDS.sleep(4000);
                    }
                    catch (InterruptedException e) { System.err.println("Interrupted");  }
                    mainWindow.setScreen(new RestartScreen(terminal,mainWindow,RestartScreen.lose,gameStage));
                    cancel();
                }
                else if(world.map.getEndX()==player.getX()&& world.map.getEndY()==player.getY() && world.monsterNumberLeft<=0){
                    mainWindow.setScreen(new RestartScreen(terminal,mainWindow,RestartScreen.win,gameStage+1));
                    cancel();
                }
            }
        };
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(task, 0, 100);
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
            terminal.write(AsciiPanel.endPointOpenIndex,world.map.getEndX(),world.map.getEndY());
        else{ //终点出现了就不显示武器了
            if(player.weapon.isVisible()){
                terminal.write(player.weapon.getGlyph(),player.weapon.getX(),player.weapon.getY(),AsciiPanel.white);
            }  
        }
        //Bullets
        for (int x = 0; x < world.WIDTH; x++) {
            for (int y = 0; y < world.HEIGHT; y++) {
                terminal.writeEffect((char)0, x, y);  //color unnecessary
            }
        }
        Iterator<Bullet> iter=world.bullets.iterator();
        Bullet bullet;
        while(iter.hasNext()){
            bullet=iter.next();
            if(bullet.isVisible())
                terminal.writeEffect(bullet.getGlyph(), bullet.getX(), bullet.getY());
            else 
                iter.remove();
        }

    //the DATA area
        for(int x=world.WIDTH;x<MainWindow.width;x++)
            for(int y=0;y<MainWindow.height;y++){
                terminal.write((char)0,x,y,AsciiPanel.white);
            }
        terminal.write("Stage "+Integer.toString(gameStage),world.WIDTH,0);
        terminal.write("Press SPACE to attack!",world.WIDTH,1);
        terminal.write("Your HP: "+Integer.toString(world.player.getHp()),world.WIDTH,2);
        terminal.write(Integer.toString(world.monsterNumberLeft)+" Monster(s) Left!",world.WIDTH,3);
    }

    int i = 0;

    @Override
    public void respondToUserInput(KeyEvent key) {
        //System.out.println("a");
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.moveLeft();;
                break;
            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;
            case KeyEvent.VK_UP:
                player.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown();
                break;
            case KeyEvent.VK_SPACE:
                player.attack();
                break;
            default:
                break;
        }
        
    }
}