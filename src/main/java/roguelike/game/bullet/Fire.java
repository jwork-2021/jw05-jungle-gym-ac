package roguelike.game.bullet;

import roguelike.game.Nothing;
import roguelike.game.Thing;
import roguelike.game.World;
import roguelike.game.creature.Monster;

public class Fire extends Bullet{
    public static final int flyInterval=100; //burn every 0.1s
    public static final int fireLifetime=25; //last for 25*0.1s
    public Fire(World world, int damage,int direction,int x,int y){
        super(world, damage, fireLifetime, direction, x, y,flyInterval);
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
            if(thing instanceof Monster)thing.getAttacked(damage*1000/(fireLifetime*flyInterval)); //每秒伤害
        } 
        range-=1;
        //expired?
        if(range<=0){
            x=-1;
            y=-1;
            cancel();
            return;
        }
    }
}
