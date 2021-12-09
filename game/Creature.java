package game;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Creature extends Thing implements Runnable{

    protected int hp,damage;
    private boolean cooling=false;
    private int attackInterval;//in milliseconds

    Creature(char glyph, World world,int hp,int damage,int attackInterval) {
        super(glyph, world);
        this.hp=hp;
        this.damage=damage;
        this.attackInterval=attackInterval;
    }

    public void moveUp() {
        if(getY()>0 && ( world.get(getX(),getY()-1) instanceof Nothing )){
            if(world.put(this,getX(),getY()-1)){
                world.empty(getX(),getY()+1);
            }
        }
    }
    public void moveDown(){
        if(getY()<world.HEIGHT-1 && ( world.get(getX(),getY()+1) instanceof Nothing )){
            if(world.put(this,getX(),getY()+1)){
                world.empty(getX(),getY()-1);
            }
        }
    }
    public void moveLeft(){
        if(getX()>0 && ( world.get(getX()-1,getY()) instanceof Nothing )){
            if(world.put(this,getX()-1,getY())){
                world.empty(getX()+1,getY());
            }
        }
    }
    public void moveRight(){
        if(getX()<world.WIDTH-1 && ( world.get(getX()+1,getY()) instanceof Nothing )){
            if(world.put(this,getX()+1,getY())){
                world.empty(getX()-1,getY());
            }
        }
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

    //abstract public void attack(int attack);

    @Override
    public void getAttacked(int damage){
        hp-=damage;
    }

    public void attackCooling(){
        cooling=true;
        TimerTask task = new TimerTask() {
            public void run() {
                cooling=false;
            }
        };
        new Timer().schedule(task, attackInterval);;
    }
    //TODO:cooling
}
