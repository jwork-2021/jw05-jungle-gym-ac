package game.bullet;
import java.util.Timer;
import java.util.TimerTask;

import game.Nothing;
import game.Thing;
import game.World;
import game.item.Weapon;


public abstract class Bullet extends TimerTask{
    protected World world;
    protected int damage,range,direction,flyInterval;
    protected int x,y;
    protected char upGlyph,downGlyph,leftGlyph,rightGlyph;
    public Bullet(World world, int damage,int range,int direction,int x,int y,int flyInterval){
        this.world=world;
        this.damage=damage;
        this.range=range;
        this.direction=direction; 
        this.x=x;
        this.y=y;    
        this.flyInterval=flyInterval;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, flyInterval);;
    }


    public boolean isVisible(){
        return getX()>=0 && getX()<world.WIDTH &&  getY()>=0 && getY()<world.HEIGHT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRange(){
        return range;
    }

    public char getGlyph() {
        switch(direction){
            case Weapon.up:
                return upGlyph;
            case Weapon.down:
                return downGlyph;
            case Weapon.left:
                return leftGlyph;
            case Weapon.right:
                return rightGlyph;
            default:
                return rightGlyph;
        }
    }
}
