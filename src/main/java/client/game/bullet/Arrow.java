package client.game.bullet;

import client.asciiPanel.AsciiPanel;
import client.game.Nothing;
import client.game.Thing;
import client.game.World;
import client.game.creature.Creature;
import client.game.creature.Monster;

public class Arrow extends Bullet{
    public static final int flyInterval=200;
    protected char upGlyph,downGlyph,leftGlyph,rightGlyph;

    public Arrow(World world, int damage,int range,int direction,int x,int y){
        super(world, damage, range, direction, x, y,flyInterval);
        upGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Up");
        downGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Down");
        leftGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Left");
        rightGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Right");
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
    @Override
    public char getGlyph() {
            switch(direction){
                case Creature.up:
                    return upGlyph;
                case Creature.down:
                    return downGlyph;
                case Creature.left:
                    return leftGlyph;
                case Creature.right:
                    return rightGlyph;
                default:
                    return rightGlyph;
            }
    }
    
}
