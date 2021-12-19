package roguelike.game.creature;

import roguelike.game.World;
import roguelike.game.weapon.Wand;

public class Wizard extends Player{
    
    public Wizard(World world){
        super(world);
        weapon=new Wand(world,this,playerDamage,playerRange);
    }
}
