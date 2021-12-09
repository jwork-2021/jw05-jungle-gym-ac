package game;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Timer;

import asciiPanel.AsciiPanel;
import game.bullet.Bullet;

import java.awt.Color;
import map.MyMap;

public class World {

    public final int WIDTH = 26;
    public final int HEIGHT = 24;
    public int monsterNumberLeft;
    public MyMap map;
    private Tile<Thing>[][] tiles;
    public Player player;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    
    //private Random rand = new Random();
    //private Color wallColor,floorColor;

    public World(int playerType,int gameStage) {
        map=new MyMap(WIDTH,HEIGHT,gameStage);

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                if(map.getType(i,j)==MyMap.tile)
                    tiles[i][j].setThing(new Nothing(this));
                else
                    tiles[i][j].setThing(new Wall(this));
            }
        }
    //player
        player=new Player(this,playerType);
        Thread playerThread=new Thread(player);
        playerThread.start();
        player.run();
    //monsters
        Monster monster;
        monsterNumberLeft=map.monsterPositions.size();
        if(monsterNumberLeft>0){
            ExecutorService exec = Executors.newFixedThreadPool(map.monsterPositions.size());
            for (Map.Entry<Integer,Integer> entry: map.monsterPositions.entrySet()){
                monster=new Monster(this);
                put(monster,entry.getKey(),entry.getValue());
                exec.execute(monster);
            }
            exec.shutdown();
        }
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public boolean put(Thing t, int x, int y) {
        return this.tiles[x][y].setThing(t);
    }

    public void empty(int x,int y){
        this.tiles[x][y].setThing(new Nothing(this));
    }
    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }
}
