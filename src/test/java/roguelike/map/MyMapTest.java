package roguelike.map;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import roguelike.game.World;

public class MyMapTest {
    @Test
    public void testGenerateMap() {
        MyMap myMap=new MyMap(World.WIDTH,World.HEIGHT, (new Random()).nextInt(20));
        assertTrue("Error in MyMap.generateMap",myMap.getType(myMap.getStartX(), myMap.getStartY())==MyMap.tile );
        
        assertTrue("Error in MyMap.generateMap",myMap.getType(myMap.getEndX(), myMap.getEndY())==MyMap.tile );
    }

    @Test
    public void testGenerateMonsters() {
        for(int i=0;i<20;i++){
            MyMap myMap=new MyMap(World.WIDTH,World.HEIGHT, i);
            assertTrue("Error in MyMap.generateMap",myMap.generateMonsters(i)>=0 && myMap.generateMonsters(i)<=15+5*i);
        }

    }

    @Test
    public void testGetEndX() {
        MyMap myMap=new MyMap(World.WIDTH,World.HEIGHT, (new Random()).nextInt(20));
        assertTrue("Error in MyMap.getEndX",myMap.getEndX()>=0 && myMap.getEndX()<World.WIDTH);

    }

    @Test
    public void testGetEndY() {
        MyMap myMap=new MyMap(World.WIDTH,World.HEIGHT, (new Random()).nextInt(20));
        assertTrue("Error in MyMap.getEndY",myMap.getEndY()>=0 && myMap.getEndX()<World.HEIGHT);

    }

    @Test
    public void testGetStartX() {
        MyMap myMap=new MyMap(World.WIDTH,World.HEIGHT, (new Random()).nextInt(20));
        assertTrue("Error in MyMap.getEndX",myMap.getStartX()>=0 && myMap.getStartX()<World.WIDTH);
    }

    @Test
    public void testGetStartY() {
        MyMap myMap=new MyMap(World.WIDTH,World.HEIGHT, (new Random()).nextInt(20));
        assertTrue("Error in MyMap.getEndX",myMap.getStartY()>=0 && myMap.getStartY()<World.WIDTH);
    }
   
}
