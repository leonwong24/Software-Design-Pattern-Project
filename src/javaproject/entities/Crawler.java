package javaproject.entities;

import javaproject.assets.Asset;
import javaproject.entities.strategy.SlowMovementHitBehavior;


import java.awt.*;

/**Fast, agile type enemy with fast movement speed and attack rate.This type of enemy will be slow after getting hit by player*/
public class Crawler extends Creature{

    public Player target;
    private double angle;
    private double diffrX,diffrY;

    /**
     * This is a constructor method that initialise the crawler class method
     *
     * @param x crawler x position in the game panel
     * @param y crawler y postion in the y panael
     * @param player player object from the game
     */
    public Crawler(float x, float y, Player player) {
        super(x, y, 64, 64);
        this.setHealth(30);
        this.setMovementSpeed(2.5f);
        target = player;
        this.setDamage(5f);
        this.setAttackRate(500000000L); //0.sec
        this.setKillScore(15); //get 15 point after killing it
        this.setBulletHitBehavior(new SlowMovementHitBehavior(2,0.20f));
    }

    @Override
    public void tick() {
        checkAlive();
        if(alive){
            chaseTarget();
            move();
        }

    }

    @Override
    public void render(Graphics g) {
        if(alive){
            g.drawImage(Asset.crawler,(int)x,(int)y,width,height,null);
        }

    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    private void chaseTarget(){
        xMove = 0;
        yMove = 0;
        //this is the algorithm of chasing the players

        //first we have to find the distance between the players and the enemies
        diffrX = this.x-target.getX();
        diffrY = this.y-target.getY();

        //calculate the angle between the player and enemies
        angle = Math.atan2(diffrY,diffrX);

        //chase the player using the cos and sin
        //have to put - on Y because Y behave differently (increase while going down)
        yMove += -(movementSpeed*(float)Math.sin(angle));
        xMove += -(movementSpeed*(float)Math.cos(angle));
    }

    //@Override
    //public void hitByBullet() {
        //setHealth(health - Bullet.getDamage());
        /***
         * original implementation of the enemy movement speed slow
        //A crawler will significantly slowed after being hit by bullet
        setMovementSpeed(0.5f);



        //set back the movement speed after 2 sec delay
        Timer t = new Timer();
        TimerTask restoreSpeed = new TimerTask(){
        int count = 0;
            @Override
            public void run() {
                setMovementSpeed(2.5f);
                count++;
                if(count > 1)
                    t.cancel();
                    t.purge();
            }
        };
        t.schedule(restoreSpeed,2000);
         *///
    //}
}
