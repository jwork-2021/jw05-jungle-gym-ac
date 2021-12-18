package game.creature;

import asciiPanel.AsciiPanel;
import game.World;
import game.weapon.Wand;

public class Wizard extends Player{
    
    public Wizard(World world){
        super(world);
        weapon=new Wand(world,this,playerDamage,playerRange);
    }
}
