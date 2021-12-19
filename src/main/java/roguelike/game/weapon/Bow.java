package roguelike.game.weapon;

import roguelike.game.World;
import roguelike.game.bullet.Arrow;
import roguelike.game.creature.Player;

public class Bow extends Weapon{
    public Bow(World world,Player owner,int damage,int range){
        super(world,owner,damage,range);
    }
    @Override
    public void attack() {
        world.addBullet(new Arrow(world, damage, range, owner.getDirection(), getX(), getY()) );
    }
}
