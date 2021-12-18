package game.weapon;

import asciiPanel.AsciiPanel;
import game.World;
import game.bullet.Arrow;
import game.creature.Player;

public class Bow extends Weapon{
    public Bow(World world,Player owner,int damage,int range){
        super(world,owner,damage,range);
    }
    @Override
    public void attack() {
        world.addBullet(new Arrow(world, damage, range, owner.getDirection(), getX(), getY()) );
    }
}