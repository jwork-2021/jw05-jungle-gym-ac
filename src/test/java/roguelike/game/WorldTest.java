package roguelike.game;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import roguelike.game.bullet.Arrow;


public class WorldTest {
    @Test
    public void testAddBullet() {
        Random rand=new Random();
        int playerType=rand.nextInt(2);
        int gameStage=rand.nextInt(20);//random int
        World world=new World(playerType,gameStage);
        world.addBullet(new Arrow(world,0 , 1, 0, 1, 1));
        assertTrue("Error in world,AddBullets", world.bullets.size()==1);
    }

    @Test
    public void testEmpty() {
        Random rand=new Random();
        int playerType=rand.nextInt(2);
        int gameStage=rand.nextInt(20);//random int
        World world=new World(playerType,gameStage);

        world.empty(1, 1);
        assertTrue("Error in World.Empty Method", world.get(1,1) instanceof Nothing);

    }



    @Test
    public void testGetGameStage() {
        Random rand=new Random();
        int playerType=rand.nextInt(2);
        int gameStage=rand.nextInt(20);//random int
        World world=new World(playerType,gameStage);

        assertTrue("Wrong gamestage!", world.getGameStage()==gameStage);
    }

    @Test
    public void testPutGet() {
        Random rand=new Random();
        int playerType=rand.nextInt(2);
        int gameStage=rand.nextInt(20);//random int
        World world=new World(playerType,gameStage);

        Nothing nothing= new Nothing(world);
        world.put(nothing, 1, 1);
        
        assertTrue("Error in World.Put or Get Method", world.get(1,1)==nothing);
    }
}
