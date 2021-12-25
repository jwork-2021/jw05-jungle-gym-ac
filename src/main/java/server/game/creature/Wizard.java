package server.game.creature;

import server.game.World;
import server.game.weapon.Wand;

public class Wizard extends Player{
    
    public Wizard(World world){
        super(world);
        weapon=new Wand(world,this,playerDamage,playerRange);
    }
}
