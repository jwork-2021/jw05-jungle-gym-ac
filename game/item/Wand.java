package game.item;

import asciiPanel.AsciiPanel;
import game.Player;
import game.World;
import game.bullet.Fireball;

public class Wand extends Weapon{
    public Wand(World world,Player owner,int damage,int range){
        super(AsciiPanel.rightWandIndex,world,owner,damage,range);
        this.upGlyph=AsciiPanel.upWandIndex;
        this.downGlyph=AsciiPanel.downWandIndex;
        this.leftGlyph=AsciiPanel.leftWandIndex;
        this.rightGlyph=AsciiPanel.rightWandIndex;
    }
    
    @Override
    public void attack() {
        world.addBullet(new Fireball(world, damage, range, direction, getX(), getY()));
    }
}
