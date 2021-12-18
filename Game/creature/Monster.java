package game.creature;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import asciiPanel.AsciiPanel;
import game.Nothing;
import game.World;
import game.bullet.Lightning;

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
        if(getX()==world.player.getX()&&getY()<world.player.getY()){
            if(world.player.getY()-getY()<=range && world.player.getHp()>0){
                attackDown();
            }
            else {
                if(!moveDown())randomWalk();
            }
        }
        else if(getX()==world.player.getX()&&getY()>world.player.getY()){
            if(getY()-world.player.getY()<=range && world.player.getHp()>0){
               attackUp();
            }
            else{
                if(!moveUp())randomWalk();;
            }
        } 
        else if(getY()==world.player.getY()&&getX()<world.player.getX()){
            if(world.player.getX()-getX()<=range && world.player.getHp()>0){
               attackRight();
            }
            else{
                if(!moveRight())randomWalk();;
            }
        }
        else if(getY()==world.player.getY()&&getX()>world.player.getX()){
            if(getX()-world.player.getX()<=range && world.player.getHp()>0){
                attackLeft();
            }
            else{
                if(!moveLeft())randomWalk();
            }
        }
        else{
            randomWalk();
        }
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
