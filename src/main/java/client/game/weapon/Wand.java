package client.game.weapon;

import client.game.World;
import client.game.bullet.Fireball;
import client.game.creature.Player;

public class Wand extends Weapon{
    public Wand(World world,Player owner,int damage,int range){
        super(world,owner,damage,range);
    }
    
    @Override
    public void attack() {
        world.addBullet(new Fireball(world, damage, range, owner.getDirection(), getX(), getY()));
    }
}
