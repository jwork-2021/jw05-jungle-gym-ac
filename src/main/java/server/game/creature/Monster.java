package server.game.creature;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

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
        if(getX()==world.players[0].getX()&&getY()<world.players[0].getY()){
            if(world.players[0].getY()-getY()<=range && world.players[0].getHp()>0){
                attackDown();
            }
            else {
                if(!moveDown())randomWalk();
            }
        }
        else if(getX()==world.players[0].getX()&&getY()>world.players[0].getY()){
            if(getY()-world.players[0].getY()<=range && world.players[0].getHp()>0){
               attackUp();
            }
            else{
                if(!moveUp())randomWalk();;
            }
        } 
        else if(getY()==world.players[0].getY()&&getX()<world.players[0].getX()){
            if(world.players[0].getX()-getX()<=range && world.players[0].getHp()>0){
               attackRight();
            }
            else{
                if(!moveRight())randomWalk();;
            }
        }
        else if(getY()==world.players[0].getY()&&getX()>world.players[0].getX()){
            if(getX()-world.players[0].getX()<=range && world.players[0].getHp()>0){
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
