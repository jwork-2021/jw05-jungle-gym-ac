package roguelike.game.creature;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import roguelike.game.World;
import roguelike.game.weapon.Weapon;

public abstract class Player extends Creature{

    public static final int ARCHER=0,WIZARD=1;

    public Weapon weapon;
    private static int playerHP= 1000;
    protected static int playerDamage=100;
    protected static int playerRange=8;
    private static int ActionInterval= 100;//in milliseconds    
    public ArrayList<KeyEvent> keyEventBuffer=new ArrayList<KeyEvent>();

    public Player(World world) {
        super(world,playerHP,playerDamage,ActionInterval);
        world.put(this,world.map.getStartX(),world.map.getStartY());
    }
    
    public void attack(){
        weapon.attack();
    }


    @Override 
    public void run(){
        //TODO:每格一定时间查看是否有键盘送来的信息，并处理？
        try{
            while(hp>0){
                TimeUnit.MILLISECONDS.sleep(actionInterval);
                synchronized(this){
                    if(keyEventBuffer.size()>0){
                            switch(keyEventBuffer.get(0).getKeyCode()){
                                case KeyEvent.VK_LEFT:
                                    moveLeft();;
                                    break;
                                case KeyEvent.VK_RIGHT:
                                    moveRight();
                                    break;
                                case KeyEvent.VK_UP:
                                    moveUp();
                                    break;
                                case KeyEvent.VK_DOWN:
                                    moveDown();
                                    break;
                                case KeyEvent.VK_SPACE:
                                    attack();
                                    break;
                                default:
                                    break;
                            }
                        }
                        keyEventBuffer.clear();
                    }
            }
            world.empty(getX(), getY());
        }
        catch (InterruptedException e) { System.err.println("Interrupted");  }
    }
}
