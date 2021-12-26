package server.game.creature;


import server.game.Nothing;
import server.game.World;
import server.game.bullet.MagicBall;

public class Vampire extends Monster{
    private static final int HP= 2000;
    private static final int Damage=200;
    private static final int ActionInterval= 500;//in milliseconds
    private static final int AttackCoolingCount=10;//5 actionIntervals
    private int attackCoolingCount=AttackCoolingCount; //4 actionIntervals
    private Player target;

    public Vampire(World world){
        super(world, HP, Damage, ActionInterval);
        range=world.WIDTH;
    }

    @Override
    protected void action() {
        //TODO: randomly select a player and attack
        attackCoolingCount--;
        if(attackCoolingCount<=0){
            target=world.players[rand.nextInt(world.players.length)];
            if(rand.nextInt(2)==0){
                if(tryMoveX()){
                    attackX();
                    attackCoolingCount=AttackCoolingCount; //modify cooling
                }
            }
            else{
                if(tryMoveY()){
                    attackY();
                    attackCoolingCount=AttackCoolingCount;
                }
            }
        }
        else
            randomWalk();
    }
    /**
     * Move,make sure the Vampire and the Player has the same X/Y
     * @return true if succesfully moved and attacked,false otherwise.
     */
    private boolean tryMoveX(){ 
        int oldX=getX(),oldY=getY();
        int x=0;
        for(int i=0;i<world.WIDTH;i++){ //try world.WIDTH number of times
            x=rand.nextInt(world.WIDTH);
            if(world.get(x,target.getY()) instanceof Nothing && target.getHp()>0
                &&world.put(this, x,target.getY())){
                    world.empty(oldX, oldY);
                    return true;
                }
        }
        return false;
    }
    private boolean tryMoveY(){ 
        int oldX=getX(),oldY=getY();
        int y=0;
        for(int i=0;i<world.HEIGHT;i++){ //try world.HEIGHT number of times
            y=rand.nextInt(world.HEIGHT);
            if(world.get(target.getX(),y) instanceof Nothing && target.getHp()>0
                &&world.put(this, target.getX(),y)){
                    world.empty(oldX, oldY);
                    return true;
                }
        }
        return false;
    }
    private void attackX(){
        if(target.getX()<=getX())
            for(int i=-3;i<=3;i++)
                world.addBullet(new MagicBall(world, damage,range ,left, getX(), getY()+i));
        else
            for(int i=-3;i<=3;i++)
                world.addBullet(new MagicBall(world, damage,range ,right, getX(), getY()+i));

    }
    private void attackY(){
        if(target.getY()<=getY())
            for(int i=-3;i<=3;i++)
                world.addBullet(new MagicBall(world, damage,range ,up, getX()+i, getY()));
        else
            for(int i=-3;i<=3;i++)
                world.addBullet(new MagicBall(world, damage,range ,down, getX()+i, getY()));

    }
}
