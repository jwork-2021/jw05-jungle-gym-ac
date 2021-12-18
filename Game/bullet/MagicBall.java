package game.bullet;

import java.util.Random;

import game.Nothing;
import game.Thing;
import game.World;
import game.creature.Creature;
import game.creature.Player;

public class MagicBall extends Bullet{
    public static final int flyInterval=200;
    private final Random rand=new Random();
    public MagicBall(World world, int damage,int range,int direction,int x,int y){
        super(world, damage, range, direction, x, y,flyInterval);
    }
    @Override
    public void run() {
        //out of map?
        if(!isVisible()){ 
            cancel();
            return;
        }
        //HIT Something?
        Thing thing=world.get(x, y);
        if( !(thing instanceof Nothing)){
            if(thing instanceof Player)thing.getAttacked(damage); 
        } 
        //move
        switch(direction){
                case Creature.up:
                    y-=1;
                    break;
                case Creature.down:
                    y+=1;
                    break;
                case Creature.left:
                    x-=1;
                    break;
                case Creature.right:
                    x+=1;
                    break;
        }
        range-=1;
        //out of map?
        if(!isVisible()){ 
            cancel();
            return;
        }
        //expired?
        if(range<=0){
            x=-1;
            y=-1;
            cancel();
            return;
        }
    }
    
}
