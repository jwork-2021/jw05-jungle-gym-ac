package client.game.creature;

import java.awt.Color;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import client.asciiPanel.AsciiPanel;
import client.game.Nothing;
import client.game.Thing;
import client.game.World;

public abstract class Creature extends Thing implements Runnable{

    protected int hp,damage;
    public static final int up=0,down=1,left=2,right=3;
    protected int direction;

    protected char upGlyph,downGlyph,leftGlyph,rightGlyph;

    protected int actionInterval;//in milliseconds
    Random rand=new Random();

    Creature(World world,int hp,int damage,int actionInterval) {
        super(world);
        this.direction=right;
        this.hp=hp;
        this.damage=damage;
        this.actionInterval=actionInterval;
        upGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Up");
        downGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Down");
        leftGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Left");
        rightGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Right");
    }

    public boolean moveUp() {
        direction=up;
        if(getY()>0 && ( world.get(getX(),getY()-1) instanceof Nothing )){
            if(world.put(this,getX(),getY()-1)){
                world.empty(getX(),getY()+1);
                return true;
            }
        }       
        return false;
    }
    public boolean moveDown(){
        direction=down;
        if(getY()<world.HEIGHT-1 && ( world.get(getX(),getY()+1) instanceof Nothing )){
            if(world.put(this,getX(),getY()+1)){
                world.empty(getX(),getY()-1);
                return true;
            }
        }
        return false;
    }
    public boolean moveLeft(){
        direction=left;
        if(getX()>0 && ( world.get(getX()-1,getY()) instanceof Nothing )){
            if(world.put(this,getX()-1,getY())){
                world.empty(getX()+1,getY());
                return true;
            }
        }
        return false;
    }
    public boolean moveRight(){
        direction=right;
        if(getX()<world.WIDTH-1 && ( world.get(getX()+1,getY()) instanceof Nothing )){
            if(world.put(this,getX()+1,getY())){
                world.empty(getX()-1,getY());
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void run() {        
    }

    public int getDamage() {
        return damage;
    }

    public int getHp() {
        return hp;
    }

    public int getDirection(){
        return direction;
    }

    //abstract public void attack(int attack);

    @Override
    public synchronized void getAttacked(int damage){
        hp-=damage;
    }

    /*public void attackCooling(){
        cooling=true;
        TimerTask task = new TimerTask() {
            public void run() {
                cooling=false;
            }
        };
        new Timer().schedule(task, actionInterval);;
    }*/
    @Override
    public char getGlyph() {
        switch(direction){
            case up:
                return upGlyph;
            case down:
                return downGlyph;
            case left:
                return leftGlyph;
            case right:
                return rightGlyph;
            default:
                return rightGlyph;
        }
    }

    protected void randomWalk(){
        switch(rand.nextInt(4)){
            case 0:
                moveDown();
                break; 
            case 1:
                moveRight();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveUp();
                break;
        }
    }
}
