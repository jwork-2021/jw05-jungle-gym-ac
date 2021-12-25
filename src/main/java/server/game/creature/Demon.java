package server.game.creature;

import server.game.World;
import server.game.bullet.Lightning;

public class Demon extends Monster{
    private static int HP= 200;
    private static int Damage=100;
    private static int ActionInterval= 500;//in milliseconds
    public Demon(World world) {
        super(world, HP, Damage, ActionInterval);
        range=6;
    }
    @Override
    protected void attackDown() {
        direction=down;
        world.addBullet(new Lightning(world, damage, range, down, getX(), getY()+1));
    }
    @Override
    protected void attackUp() {
        direction=up;
        world.addBullet(new Lightning(world, damage, range, up, getX(), getY()-1));
    }
    @Override
    protected void attackRight() {
        direction=right;
        world.addBullet(new Lightning(world, damage, range, right, getX()+1, getY()));
    }
    @Override
    protected void attackLeft() {
        direction=left;
        world.addBullet(new Lightning(world, damage, range, left, getX()-1, getY()));
    }
}
