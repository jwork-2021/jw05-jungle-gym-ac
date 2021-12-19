package roguelike.game.creature;

import roguelike.game.World;
import roguelike.game.weapon.Bow;

public class Archer extends Player{
    
    public Archer(World world){
        super(world);
        weapon=new Bow(world,this,playerDamage,playerRange);
    }
}
