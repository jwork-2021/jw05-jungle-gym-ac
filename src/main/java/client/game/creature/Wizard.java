package client.game.creature;

import client.game.World;
import client.game.weapon.Wand;

public class Wizard extends Player{
    
    public Wizard(World world){
        super(world);
        weapon=new Wand(world,this,playerDamage,playerRange);
    }
}
