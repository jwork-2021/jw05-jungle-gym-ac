package server.game.creature;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

import javax.lang.model.util.ElementScanner6;

import server.game.World;

public abstract class Monster extends Creature{
    
    protected int range;

    
    public Monster(World world,int hp,int damage,int actionInterval) {
        super(world,hp,damage,actionInterval);
    }
    
    @Override
    public void run() {
        try{
            while(hp>0){
                TimeUnit.MILLISECONDS.sleep(actionInterval);
                action();
            }
            world.empty(getX(), getY());
            synchronized(world){
                world.monsterNumberLeft--;
            }
        }
        catch (InterruptedException e) { System.err.println("Interrupted");  }
    }

    protected void action(){
        //TODO:上下左右扫，得到最近距离的player及方向，再攻击/move
        //get the distance and dirction to the CLOSEST Player
        int minDistance=world.WIDTH+world.HEIGHT;
        int minDirection=left;

        for(int i=0;i<getX();i++)
            if((world.get(i, getY()) instanceof Player)&&getX()-i<minDistance){
                minDistance=getX()-i;
                minDirection=left;
            }
        for(int i=getX()+1;i<world.WIDTH;i++)
            if((world.get(i, getY()) instanceof Player)&&i-getX()<minDistance){
                minDistance=i-getX();
                minDirection=right;
            }
        for(int j=0;j<getY();j++)
            if((world.get(getX(), j) instanceof Player)&&getY()-j<minDistance){
                minDistance=getY()-j;
                minDirection=up;
            }
        for(int j=getY()+1;j<world.HEIGHT;j++)
            if((world.get(getX(), j) instanceof Player)&&j-getY()<minDistance){
                minDistance=j-getY();
                minDirection=down;
            }
        if(minDistance<=range){
            switch (minDirection) {
                case up:
                    attackUp();
                    break;
                case down:
                    attackDown();
                    break;
                case left:
                    attackLeft();
                    break;
                case right:
                    attackRight();
                    break;
            }
        }
        else if(minDistance<world.HEIGHT&&minDistance<world.WIDTH){
            switch (minDirection) {
                case up:
                    if(!moveUp())randomWalk();
                    break;
                case down:
                    if(!moveDown())randomWalk();                    
                    break;
                case left:
                    if(!moveLeft())randomWalk();
                    break;
                case right:
                    if(!moveRight())randomWalk();
                    break;
            }
        }
        else randomWalk();
    }
    
    protected void attackDown(){
        direction=down;
    }
    protected void attackUp(){
        direction=up;
    }
    protected void attackLeft(){
        direction=left;
    }
    protected void attackRight(){
        direction=right;
    }

}
