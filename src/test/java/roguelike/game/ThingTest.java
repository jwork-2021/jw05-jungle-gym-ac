package roguelike.game;


import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import roguelike.asciiPanel.AsciiPanel;
import roguelike.game.bullet.Fire;
import roguelike.game.creature.Creature;

public class ThingTest {
    private static World world;
    private static Random rand=new Random();

    @BeforeClass
    public static void setUpBeforeClass() {
        
        int playerType=rand.nextInt(2);
        int gameStage=rand.nextInt(20);//random int
        world=new World(playerType,gameStage);
    }


    @Test
    public void testGetGlyph() {

        Tree tree=new Tree(world);
        assertTrue("Error in Tree.getGlyph",tree.getGlyph()
                ==AsciiPanel.stringCharMap.get("Tree"));
        Castle castle=new Castle(world);
        assertTrue("Error in Castle.getGlyph",castle.getGlyph()
                ==AsciiPanel.stringCharMap.get("Castle"));
        Rock rock=new Rock(world);
        assertTrue("Error in Rock.getGlyph",rock.getGlyph()
                ==AsciiPanel.stringCharMap.get("Rock"));
        

        Fire fire=new Fire(world, 0, Creature.up, 0, 0);
        assertTrue("Error in Fire.getGlyph",fire.getGlyph()
                ==AsciiPanel.stringCharMap.get("Fire"));       
    }

    @Test
    public void testGetX() {
        Thing thing=new Thing(world);
        world.put(thing,world.map.getStartX(),world.map.getStartY());
        assertTrue("Error in Thing.getX", thing.getX()==world.map.getStartX());        
    }

    @Test
    public void testGetY() {
        Thing thing=new Thing(world);
        world.put(thing,world.map.getStartX(),world.map.getStartY());
        assertTrue("Error in Thing.getY", thing.getY()==world.map.getStartY());        
    }

    @Test
    public void testSetTile() {
        int x=rand.nextInt(world.WIDTH);
        int y=rand.nextInt(world.HEIGHT);

        Tile tile=new Tile<>(x, y);
        Thing thing=new Thing(world);
        thing.setTile(tile);
        assertTrue("Error in Thing.setTile", thing.getX()==tile.getxPos());
    }

}
