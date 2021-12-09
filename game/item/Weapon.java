package game.item;

import game.Nothing;
import game.Player;
import game.Thing;
import game.World;

public abstract class Weapon extends Thing{
    //public static final int directionUp=0,directionDown=1,directionLeft=2,directionRight=3;
    //protected int x,y;
    protected int damage;
    protected int range;
    public static final int up=0,down=1,left=2,right=3;
    protected int direction;
    protected Player owner;
    protected char upGlyph,downGlyph,leftGlyph,rightGlyph;
    public Weapon(char glyph,World world,Player owner,int damage,int range){
        super(glyph,world);
        this.direction=right;
        this.damage=damage;
        this.range=range;
        this.owner=owner;
    }

    public boolean isVisible(){
        return getX()>=0 && getX()<world.WIDTH &&  getY()>=0 && getY()<world.HEIGHT &&
                 (world.get(getX(), getY()) instanceof Nothing); 
    }

    abstract public void attack();

    public void directionUp(){
        this.direction=up;
    }   
    public void directionDown(){
        this.direction=down;
    }
    public void directionLeft(){
        this.direction=left;
    }
    public void directionRight(){
        this.direction=right;
    }

    @Override
    public int getX() {
        switch(direction){
            case left:
                return owner.getX()-1;
            case right:
                return owner.getX()+1;
            default:
                return owner.getX();
        }
    }

    @Override
    public int getY() {
        switch(direction){
            case up:
                return owner.getY()-1;
            case down:
                return owner.getY()+1;
            default:
                return owner.getY();
        }
    }

   
    @Override
    public char getGlyph() {
        switch(direction){
            case up:
                return upGlyph;
            case down:
                return downGlyph;
            case left:
                return leftGlyph;
            case right:
                return rightGlyph;
            default:
                return rightGlyph;
        }
    }
}
