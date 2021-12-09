package game.item;

import asciiPanel.AsciiPanel;
import game.Player;
import game.World;
import game.bullet.Arrow;

public class Bow extends Weapon{
    public Bow(World world,Player owner,int damage,int range){
        super(AsciiPanel.rightBowIndex,world,owner,damage,range);
        this.upGlyph=AsciiPanel.upBowIndex;
        this.downGlyph=AsciiPanel.downBowIndex;
        this.leftGlyph=AsciiPanel.leftBowIndex;
        this.rightGlyph=AsciiPanel.rightBowIndex;
    }
    @Override
    public void attack() {
        world.addBullet(new Arrow(world, damage, range, direction, getX(), getY()) );
    }
}
