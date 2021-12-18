package game;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import game.bullet.Bullet;
import game.creature.Archer;
import game.creature.Demon;
import game.creature.Goblin;
import game.creature.Monster;
import game.creature.Player;
import game.creature.Vampire;
import game.creature.Wizard;
import map.MyMap;
import screen.Screen;

public class World {

    public final int WIDTH = 26;
    public final int HEIGHT = 24;
    protected int gameStage;
    public int monsterNumberLeft;
    public MyMap map;
    private Tile<Thing>[][] tiles;
    public Player player;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private Random rand=new Random();
    
    //private Random rand = new Random();
    //private Color wallColor,floorColor;

    public World(int playerType,int gameStage) {
        map=new MyMap(WIDTH,HEIGHT,gameStage);
        this.gameStage=gameStage;


        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                if(map.getType(i,j)==MyMap.tile)
                    tiles[i][j].setThing(new Nothing(this));
                else{
                    switch(this.gameStage%Screen.backgroundStringName.length){
                        case 0:
                            tiles[i][j].setThing(new Rock(this));
                            break;
                        case 1:
                            tiles[i][j].setThing(new Tree(this));
                            break;
                        case 2:
                            tiles[i][j].setThing(new Castle(this));
                            break;
                        default:
                            tiles[i][j].setThing(new Castle(this));
                            break;
                    }
                }
            }
        }
    //player
        switch(playerType){
            case Player.ARCHER:
                player=new Archer(this);
                break;
            case Player.WIZARD:
                player=new Wizard(this);
                break;
        }
        
        Thread playerThread=new Thread(player);
        playerThread.start();
        
    //monsters
        addMonsters();
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
        synchronized(bullets){
            bullets.add(bullet);
        }
    }
    public int getGameStage(){
        return gameStage;
    }
    public void addMonsters(){
        //monsters
        Monster monster;
        monsterNumberLeft=map.monsterPositions.size();
        if(map.monsterPositions.size()<=0)return;

        boolean bossCreated=false;
        
        ExecutorService exec = Executors.newFixedThreadPool(map.monsterPositions.size());
        for (Map.Entry<Integer,Integer> entry: map.monsterPositions.entrySet()){
            switch(gameStage%Screen.backgroundStringName.length){
                case 0:
                    switch(rand.nextInt(2)){
                        case 0:
                            monster=new Demon(this);
                            break;
                        case 1:
                            monster=new Goblin(this);
                            break;
                        default:
                            monster=new Demon(this);
                            break;
                        }
                    break;
                case 1:
                    switch(rand.nextInt(2)){
                        case 0:
                            monster=new Demon(this);
                            break;
                        case 1:
                            monster=new Goblin(this);
                            break;
                        default:
                            monster=new Demon(this);
                            break;
                        }
                        break;
                case 2:{
                    if(!bossCreated){
                        monster=new Vampire(this);
                        bossCreated=true;
                    }
                    else{
                        switch(rand.nextInt(2)){
                            case 0:
                                monster=new Demon(this);
                                break;
                            case 1:
                                monster=new Goblin(this);
                                break;
                            default:
                                monster=new Demon(this);
                                break;
                        }
                    }
                    break;
                }
                default:
                    monster=new Demon(this);
                    break;
                }
            put(monster,entry.getKey(),entry.getValue());
            exec.execute(monster);
            }
        exec.shutdown();
    }
}
