package game.bullet;

import asciiPanel.AsciiPanel;
import game.Nothing;
import game.Thing;
import game.World;
import game.creature.Creature;

public class Fireball extends Bullet{
    public static final int flyInterval=300;
    public Fireball(World world, int damage,int range,int direction,int x,int y){
        super(world, damage, range, direction, x, y,flyInterval);
    }
    
    @Override
    public void run() {
        //out of map?
        if(!isVisible()){ 
            cancel();
            return;
        }
        //HIT Something?
        Thing thing=world.get(x, y);
        if( !(thing instanceof Nothing)){
            addFire();
            x=-1;
            y=-1;
            cancel();
            return;
        } 
        //move
        switch(direction){
            case Creature.up:
                y-=1;
                break;
            case Creature.down:
                y+=1;
                break;
            case Creature.left:
                x-=1;
                break;
            case Creature.right:
                x+=1;
                break;
        }
        range--;
        //out of map?
        if(!isVisible()){ 
            cancel();
            return;
        }
        //HIT something or expired
        thing=world.get(x, y);
        if( !(thing instanceof Nothing) || range<=0){
            addFire();
            x=-1;
            y=-1;
            cancel();
            return;
        } 
    }
    private void addFire(){
        for(int i=-1;i<2;i++)
            for(int j=-1;j<2;j++){
                world.addBullet(new Fire(world, damage, direction, x+i, y+j));
            }
    }
}
