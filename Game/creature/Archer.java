package game.creature;

import asciiPanel.AsciiPanel;
import game.World;
import game.weapon.Bow;

public class Archer extends Player{
    
    public Archer(World world){
        super(world);
        weapon=new Bow(world,this,playerDamage,playerRange);
    }
}
