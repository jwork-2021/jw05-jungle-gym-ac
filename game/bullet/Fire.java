package game.bullet;

import asciiPanel.AsciiPanel;
import game.Monster;
import game.Nothing;
import game.Thing;
import game.World;

public class Fire extends Bullet{
    public static final int flyInterval=100; //burn every 0.1s
    public static final int fireLifetime=40; //last for 4s
    public Fire(World world, int damage,int direction,int x,int y){
        super(world, damage, fireLifetime, direction, x, y,flyInterval);
        this.upGlyph=AsciiPanel.fireIndex;
        this.downGlyph=AsciiPanel.fireIndex;
        this.leftGlyph=AsciiPanel.fireIndex;
        this.rightGlyph=AsciiPanel.fireIndex;
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
            if(thing instanceof Monster)thing.getAttacked(damage);
        } 
        range-=1;
        //expired?
        if(range<=0){
            x=-1;
            y=-1;
            cancel();
            return;
        }
    }
}
