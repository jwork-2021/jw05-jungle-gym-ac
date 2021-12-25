package client.game.bullet;
import java.util.Timer;
import java.util.TimerTask;

import client.asciiPanel.AsciiPanel;
import client.game.World;

public abstract class Bullet extends TimerTask{
    protected World world;
    protected int damage,range,direction,flyInterval;
    protected int x,y;
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

    public char getGlyph(){
        return AsciiPanel.stringCharMap.get(this.getClass().getSimpleName());
    }
}
