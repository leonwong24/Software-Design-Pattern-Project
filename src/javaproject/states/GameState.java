package javaproject.states;

import javaproject.assets.Asset;
import javaproject.entities.*;
import javaproject.inputs.BulletController;
import javaproject.main.Game;

import java.awt.*;
import java.util.*;


/**
 * This class will only be run if the gamestate is set to running. This class contains most of the logic/algorithm in this game such as enemySpawn, collision
 */
public class GameState extends State {

    private Player player;
    private BulletController bc;
    private static int score = 0;
    public int enemyCount = 5;
    private EnemyFactory enemyFactory;
    //timer
    Timer timer;


    //create an array list that stores enemies,bullet
    private LinkedList<Creature> enemies = new LinkedList<Creature>();
    public LinkedList<Wall> walls = new LinkedList<>();
    public LinkedList<Bullet> bullets = new LinkedList<>();


    /**
     * This is the gameState constructor to initialise gamestate object
     * @param game Current running game thread
     */
    public GameState(Game game) {
        super(game);
        //player =  new Player(game, 800, 450);
        player = Player.getPlayerInstance(game);
        bc = new BulletController(game, player);
        enemyFactory = new EnemyFactory();
               //enemies

            //a holder for spawnSpotx
            /*int spawnX = spawnSpotX();
            if(enemyPicker == 1){
                //spawn walker
                enemies.add(new Walker(game,spawnX,spawnSpotY(spawnX),player));
            }

            else if(enemyPicker == 2){
                //spawn crawler
                enemies.add(new Crawler(game,spawnX,spawnSpotY(spawnX),player));
            }

            else{
                //spawn tanker
                enemies.add(new Tank(game,spawnX,spawnSpotY(spawnX),player));
            }*/

            //timer
            //This is going to spawn enemies every 2 second
            timer = new Timer();
            //for(int i = 0; i < enemyCount ;i++){
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        enemySpawn();
                    }
                }, 0,2000);
            //}


        //4 walls
        walls.add(new Wall(0, 0, 300, 100));//top left block
        walls.add(new Wall(0, 800, 300, 100));//bot left block
        walls.add(new Wall(1300, 0, 300, 100)); //top right block
        walls.add(new Wall(1300, 800, 300, 100));//bot right block*/

    }


    @Override
    public void tick() {
        //player tick
        player.tick();

        //enemy tick
        for (Creature enemy : enemies) {
            enemy.tick();
        }

        //bulletcontroller tick
        bc.tick();

        //walls tick
        for (Wall wall : walls) {
            wall.tick();
        }

        bulletCheckCollisions(bc.getBullets(),enemies,walls);
        playerCheckCollisions(player,enemies);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Asset.floor, 0, 0, 1600, 900, null);//floor*/
        player.render(g);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }

        bc.render(g);

        //walls render
        for (Wall wall : walls) {
            wall.render(g);
        }
        g.drawString("Player health:" + player.getHealth(), 1300, 50);
        g.drawString("Score: "  + score, 1300,70);
        g.drawString("High score: " +GameOverState.highScore,1300,100 );

    }

    private void enemySpawn() {
            //pick random enemy, 3 is the maximum number and 1 is the minimum
            int enemyPicker = (int) (Math.random() * 3) + 1;

            //randomize 1 -3 to pick enemy spawn position
            int x =(int)(Math.random()*3)+1;

            //a holder for enemies spawn X position and Y position
            int spawnX,spawnY;
            switch(x){
                case 1:
                    spawnX =800;
                    int y = (int)(Math.random()*2) + 1;
                    switch(y){
                        case 1:
                            spawnY = 0;
                            break;
                        default:
                            spawnY = 900;
                            break;
                    }
                    break;

                case 2:
                    spawnX = 0;
                    spawnY = 450;
                    break;
                default:
                    spawnX = 1600;
                    spawnY = 450;
                    break;
            }

            if (enemyPicker == 1) {
                //spawn walker
                //enemies.add(new Walker(spawnX,spawnY, player)); - before implementing simple factory
                try{
                    Creature enemy = enemyFactory.getCreature("walker",spawnX,spawnY);
                    //Creature slowedEnemy = new reduceMovementSpeedDecorator(enemy,1,0.25f);
                    //enemies.add(slowedEnemy);
                    //enemies.add(enemyFactory.getCreature("walker",spawnX,spawnY));
                    enemies.add(enemy);
                } catch(Exception e){
                }

            } else if (enemyPicker == 2) {
                //spawn crawler
                //enemies.add(new Crawler(spawnX,spawnY, player));
                try{
                    Creature enemy = enemyFactory.getCreature("crawler",spawnX,spawnY);
                    //enemy = new reduceMovementSpeedDecorator(enemy.getX(),enemy.getY(),enemy.getHeight(),enemy.getWidth(),enemy,2,0.80f);
                    enemies.add(enemy);
                    //enemies.add(enemyFactory.getCreature("crawler",spawnX,spawnY));
                } catch(Exception e){
                }
            } else {
                //spawn tanker
                //enemies.add(new Tank(spawnX,spawnY, player));
                try{
                    enemies.add(enemyFactory.getCreature("tank",spawnX,spawnY));
                } catch(Exception e){
                }
            }
    }

     /*private int spawnSpotX(){
        //this return 1 - 3 randomize number
        int x = (int)(Math.random()*3)+1;
        int result = 0;

        if(x == 1){
            //return spawning spot
            result = 800;
        }

        else if(x == 2){
           result = 0;
        }

        else{
            result = 1600;
        }
        return result;

    }*/

    /*private int spawnSpotY(int spawnSpotX) {

        int result = 0;
        if (spawnSpotX == 800){
            //this return only two randomize number
            int y = (int)(Math.random()*2) +1;
            if( y == 1){
                result = 0;
            }
            else if (y == 2)
                result = 900;
        }
        else
            result = 450;

        return result;
    }*/

    //check bullet and enemy collision using rectangle intersect method
    private void bulletCheckCollisions(LinkedList<Bullet> bullets , LinkedList<Creature> enemies,LinkedList<Wall> walls) {
        //use an iterator to go through my list
        //https://stackoverflow.com/questions/223918/iterating-through-a-collection-avoiding-concurrentmodificationexception-when-mo?noredirect=1&lq=1

        for (Iterator<Bullet> bulletIterator = bullets.iterator(); bulletIterator.hasNext();) {
            Bullet bullet = bulletIterator.next();
            Rectangle bulletRect = new Rectangle((int)bullet.getX(),(int)bullet.getY(),bullet.getWidth(),bullet.getHeight());
            Rectangle bulletBound = bulletRect.getBounds();

            //enemy iterator
            for(Iterator<Creature> enemyIterator = enemies.iterator();enemyIterator.hasNext();){
                Creature enemy = enemyIterator.next();
                Rectangle enemyRect = new Rectangle((int)enemy.getX(),(int)enemy.getY(),enemy.getWidth(),enemy.getHeight());
                Rectangle enemyBound = enemyRect.getBounds();

                if(bulletBound.intersects(enemyBound)){
                    //remove bullet
                    bulletIterator.remove();

                    //Bullet hit enemy
                    enemy.hitByBullet();

                    //if enemy died
                    if(enemy.isAlive() == false){
                        //remove enemy
                        enemyIterator.remove();
                        score +=enemy.getKillScore();
                    }
                    break;
                }
            }

            //wall iterator
            for(Iterator<Wall> wallIterator = walls.iterator();wallIterator.hasNext();){
                Wall wall = wallIterator.next();
                Rectangle wallRect = new Rectangle((int)wall.getX(),(int)wall.getY(),wall.getWidth(),wall.getHeight());
                Rectangle wallBound = wallRect.getBounds();

                if(bulletBound.intersects(wallBound)){
                    //Bullet hit the wall
                    //remove bullet
                    System.out.println("bullet hit the wall");
                    bulletIterator.remove();
                }
            }
        }
    }

    private void playerCheckCollisions(Player player,LinkedList<Creature> enemies){
        Rectangle playerRect = new Rectangle((int)player.getX(),(int)player.getY(),player.getWidth(),player.getHeight());
        Rectangle playerBound = playerRect.getBounds();

        //enemies iterator
        for(Iterator<Creature> enemyIterator = enemies.iterator();enemyIterator.hasNext();){
            Creature enemy = enemyIterator.next();
            Rectangle enemyRect = new Rectangle((int)enemy.getX(),(int)enemy.getY(),enemy.getWidth(),enemy.getHeight());
            Rectangle enemyBound = enemyRect.getBounds();

            if(playerBound.intersects(enemyBound)){
                //enemy hit enemy
                player.damage(enemy.getDamage(),enemy.getAttackRate());

                if(player.isAlive() == false){
                    //GAME OVER
                    // State.setState(game.gameOverState); //old implementation of state pattern
                    game.changeCurrentState(new GameOverState(game));
                }
            }
        }
    }

    public static int getScore() {
        return score;
    }


}
