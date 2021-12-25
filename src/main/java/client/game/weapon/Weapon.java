package client.game.weapon;

import client.asciiPanel.AsciiPanel;
import client.game.Nothing;
import client.game.Thing;
import client.game.World;
import client.game.creature.Creature;
import client.game.creature.Player;

public abstract class Weapon extends Thing{
    //public static final int directionUp=0,directionDown=1,directionLeft=2,directionRight=3;
    //protected int x,y;
    protected int damage;
    protected int range;
    protected Player owner;
    protected char upGlyph,downGlyph,leftGlyph,rightGlyph;
    public Weapon(World world,Player owner,int damage,int range){
        super(world);
        this.damage=damage;
        this.range=range;
        this.owner=owner;
        upGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Up");
        downGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Down");
        leftGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Left");
        rightGlyph=AsciiPanel.stringCharMap.get(this.getClass().getSimpleName()+"Right");
    }

    public boolean isVisible(){
        return getX()>=0 && getX()<world.WIDTH &&  getY()>=0 && getY()<world.HEIGHT &&
                 (world.get(getX(), getY()) instanceof Nothing); 
    }

    abstract public void attack();

    @Override
    public int getX() {
        switch(owner.getDirection()){
            case Creature.left:
                return owner.getX()-1;
            case Creature.right:
                return owner.getX()+1;
            default:
                return owner.getX();
        }
    }

    @Override
    public int getY() {
        switch(owner.getDirection()){
            case Creature.up:
                return owner.getY()-1;
            case Creature.down:
                return owner.getY()+1;
            default:
                return owner.getY();
        }
    }

   
    @Override
    public char getGlyph() {
        switch(owner.getDirection()){
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
