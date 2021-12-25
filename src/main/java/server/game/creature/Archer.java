package server.game.creature;

import server.game.World;
import server.game.weapon.Bow;

public class Archer extends Player{
    
    public Archer(World world){
        super(world);
        weapon=new Bow(world,this,playerDamage,playerRange);
    }
}
