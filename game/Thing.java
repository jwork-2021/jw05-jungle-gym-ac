package game;

import java.awt.Color;

public class Thing {

    protected World world;

    public Tile<? extends Thing> tile;

    public int getX() {
        return this.tile.getxPos();
    }

    public int getY() {
        return this.tile.getyPos();
    }

    public void setTile(Tile<? extends Thing> tile) {
        this.tile = tile;
    }

    protected Thing(char glyph, World world) {
        //this.color = color;
        this.glyph = glyph;
        this.world = world;
    }

    //private final Color color;

    /*public Color getColor() {
        return this.color;
    }*/

    protected char glyph;

    public char getGlyph() {
        return this.glyph;
    }

    public void getAttacked(int damage){
    }

}