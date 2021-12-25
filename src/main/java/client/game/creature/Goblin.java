package client.game.creature;

import client.game.Thing;
import client.game.World;

public class Goblin extends Monster{
    private static int HP= 400;
    private static int Damage=100;
    private static int ActionInterval= 300;//in milliseconds
    public Goblin(World world){
        super(world, HP, Damage, ActionInterval);
        range=1;
    }
    @Override
    protected void attackDown() {
        direction=down;
        Thing thing=world.get(getX(), getY()+1);
        if(thing instanceof Player){
            thing.getAttacked(damage);
        }
    }
    @Override
    protected void attackUp() {
        direction=up;
        Thing thing=world.get(getX(), getY()-1);
        if(thing instanceof Player){
            thing.getAttacked(damage);
        }
    }
    @Override
    protected void attackRight() {
        direction=right;
        Thing thing=world.get(getX()+1, getY());
        if(thing instanceof Player){
            thing.getAttacked(damage);
        }    
    }
    @Override
    protected void attackLeft() {
        direction=left;
        Thing thing=world.get(getX()-1, getY());
        if(thing instanceof Player){
            thing.getAttacked(damage);
        }    
    }
}
