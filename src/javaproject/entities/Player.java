package javaproject.entities;

import javaproject.assets.Asset;
import javaproject.main.Game;

import java.awt.*;
/**Character that player plays . It contains the method to control a player movement and all the calculation while is damaged by the enemies*/
public class Player extends Creature{
    private Game game;
    private long lastAttack;


    //SINGLETON PATTERN
    // 1. private constructor
    // 2. private static reference
    // 3. public static method to get the object reference & exception handling
    private Player(Game game,float x, float y) {
        super(x,y,64,64);
        this.game = game;
        this.setHealth(100);
        this.setMovementSpeed(2f);

    }
    private static Player single_instance = null;

    public static Player getPlayerInstance(Game game){
        try{
            if(single_instance == null){
                single_instance = new Player(game,800,450);
            }
        }catch(Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton Player instance");
        }

        return single_instance;
    }

    @Override
    public void tick() {
       checkAlive();
        if(alive){
           getInput();
           move();
       }

    }

    @Override
    public void render(Graphics g) {
        if(alive){
            g.drawImage(Asset.player,(int)x,(int)y,width,height,null);
        }
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if(game.getKeyManager().up){
            //prevent player walks out of screen
            if(y >= 0f){
                yMove += -movementSpeed;
            }
        }

        if(game.getKeyManager().down){
            if( y <= (900f-this.getHeight())){
                yMove += movementSpeed;
            }
        }

        if(game.getKeyManager().left){
            if(x >= 0f ){
                xMove -= movementSpeed;
            }
        }

        if(game.getKeyManager().right){
            if(x <= 1600f-this.getWidth()){
                xMove += movementSpeed;
            }
        }
    }

    @Override
    public void damage(float damage,long attackRate){
        if(System.nanoTime() - lastAttack >= attackRate) {
            setHealth((health) - damage);
            lastAttack=System.nanoTime();
        }
    }

}
