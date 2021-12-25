package client.game.weapon;

import client.game.World;
import client.game.bullet.Arrow;
import client.game.creature.Player;

public class Bow extends Weapon{
    public Bow(World world,Player owner,int damage,int range){
        super(world,owner,damage,range);
    }
    @Override
    public void attack() {
        world.addBullet(new Arrow(world, damage, range, owner.getDirection(), getX(), getY()) );
    }
}
