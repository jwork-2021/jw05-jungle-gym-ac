package client.game.creature;

import client.game.World;
import client.game.weapon.Bow;

public class Archer extends Player{
    
    public Archer(World world){
        super(world);
        weapon=new Bow(world,this,playerDamage,playerRange);
    }
}
