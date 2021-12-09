package game.bullet;

import asciiPanel.AsciiPanel;
import game.Monster;
import game.Nothing;
import game.Thing;
import game.World;
import game.item.Weapon;

public class Arrow extends Bullet{
    public static final int flyInterval=200;
    public Arrow(World world, int damage,int range,int direction,int x,int y){
        super(world, damage, range, direction, x, y,flyInterval);
        this.upGlyph=AsciiPanel.upArrowIndex;
        this.downGlyph=AsciiPanel.downArrowIndex;
        this.leftGlyph=AsciiPanel.leftArrowIndex;
        this.rightGlyph=AsciiPanel.rightArrowIndex;
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
            x=-1;
            y=-1;
            cancel();
            return;
        } 
        //move
        switch(direction){
            case Weapon.up:
                y-=1;
                break;
            case Weapon.down:
                y+=1;
                break;
            case Weapon.left:
                x-=1;
                break;
            case Weapon.right:
                x+=1;
                break;
        }
        range-=1;
        //out of map?
        if(!isVisible()){ 
            cancel();
            return;
        }
        //HIT something?
        thing=world.get(x, y);
        if( !(thing instanceof Nothing)){
            if(thing instanceof Monster)thing.getAttacked(damage);
            x=-1;
            y=-1;
            cancel();
            return;
        } 
        //expired?
        if(range<=0){
            x=-1;
            y=-1;
            cancel();
            return;
        }
    }
}
