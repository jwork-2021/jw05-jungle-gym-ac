package server.game.weapon;

import server.game.World;
import server.game.bullet.Fireball;
import server.game.creature.Player;

public class Wand extends Weapon{
    public Wand(World world,Player owner,int damage,int range){
        super(world,owner,damage,range);
    }
    
    @Override
    public void attack() {
        world.addBullet(new Fireball(world, damage, range, owner.getDirection(), getX(), getY()));
    }
}