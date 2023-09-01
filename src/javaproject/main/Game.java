package javaproject.main;

import javaproject.assets.Asset;
import javaproject.inputs.KeyManager;
import javaproject.inputs.MouseManager;
import javaproject.states.GameOverState;
import javaproject.states.GameState;
import javaproject.states.MenuState;
import javaproject.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * This game class implements a runnable interface, which means this is intended to be executed by a thread.
 *
 *
 */
public class Game implements Runnable{

    //using display class
    /**
     * A class that implements JFrame with all setting set. This will create a Jframe and display
     */
    private Display display;

    //variable
    /**
     * Game panel width and height
     */
    public int width,height;

    /**
     * Game panel title
     */
    public String title;

    //graphics render
    /**
     * Use a BufferStrategy for the convenience of dealing with drawing to surfaces and components , from : https://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
     */
    private BufferStrategy bs;
    /**
     * Use it to draw to the game panel
     */
    private Graphics g;

    //creating a thread
    /**
     * game thread
     */
    private Thread thread;
    /**
     * boolean for checking if the game is running
     */
    private boolean running = false;

    //SINGLETON instance
    private static volatile Game single_instance;

    //State
    /**
     * A state instances for gameState
     */
    //public static StategameState;
    /**
     * A state instances for menuState
     */
    //public static State menuState;
    /**
     * A state instances for gameOverState
     */
    //private static State gameOverState;

    // store state property that maintans a reference to an instance of the state classes that represent the current state of the game
    private State currentState;

    //Input
    /**
     * instance of keymanager
     */
    private KeyManager keyManager;
    /**
     * instance of mousemanager
     */
    private MouseManager mouseManager;

    //Constructor

    /**
     * This constructor method take 3 parameter for the game panel title, width and height. It also initialise the keymanager and mousemanager
     * @param title The title for the game panel
     * @param width The width of the game panel
     * @param height The height of the game panel
     */
    private Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;

        //input manager
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    //SINGLETON
    public static Game getGameInstance(){
        //Game result = single_instance;

        if(single_instance == null){
            // To make thread safe
            synchronized (Game.class){
                if(single_instance == null){
                    single_instance = new Game("Shooting game",1600,900);
                }
            }
        }

        return single_instance;

        //if(single_instance == null){
            //single_instance = new Game("Shooting game",1600,900);
        //}
    }
    // setter for overriding current state value
    public void changeCurrentState(State currentState) {
        this.currentState = currentState;
    }

    // getter for current state value
    public State getCurrentState(){
        return currentState;
    }

    /**
     * initiate everything include graphics before the game loop start
     */
    private void init(){
        display = new Display(title, width, height);

        //applying keylistener and mouselistener to the game frame
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addMouseListener(mouseManager);

        //Setting the state
        //gameState = new GameState(this);
        //menuState = new MenuState(this);
        //gameOverState = new GameOverState(this);

        //State.setState(menuState); //old implementation of state pattern
        State menuState = new MenuState(getGameInstance());
        getGameInstance().changeCurrentState(menuState);
        //loading assets
        Asset.init();

    }

    /**
     * Update everything in the game
     */
    private void tick(){
        keyManager.tick();
        mouseManager.tick();

        //old implementation of state pattern
        //if(State.getCurrentState() != null){
            //State.getCurrentState().tick();
        //}
        if(currentState!=null){
            currentState.tick();
        }

    }

    /**
     * render everything in the game
     */
    private void render(){
        //get bufferstrategy from the canvas,bufferstrategy tells the comp how to draw thing
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //Clear the screen before drawing
        g.clearRect(0,0,width,height);

        //old implementation of state pattern
        //if(State.getCurrentState() != null){
            //State.getCurrentState().render(g);
        //}
        if(currentState!=null){
            currentState.render(g);
        }

        //end drawing!
        bs.show();
        g.dispose();
    }

    /**
     * run the game which include FPS algorithm which is going to set the game to run on 60 fps
     */
    public void run(){
        init();


        //Everything below from https://www.youtube.com/watch?v=w1aB5gc38C8
        int fps = 60;
        double timePerTick = 1000000000 / fps; //This return the time that the system should refresh in order to achieve 60fps
        double delta = 0;
        long now;
        long lastTime = System.nanoTime(); //This return the system time in nanosecond
        while(running){
            //fps
            now = System.nanoTime();
            delta += (now-lastTime) / timePerTick; //This return a percentage of time remaining till next refresh
            lastTime = now;

            if(delta >= 1){
                tick();
                render();
                delta --;
            }

            tick();
            render();

        }
    }

    /**
     * Causes this thread to begin execution
     */
    public synchronized void start(){
        if(running)
            return; //if game is running , don't do anything below

        running = true;
        thread = new Thread(getGameInstance()); //this = game class
        thread.start(); //start run() method
    }

    /**
     * Causes this thread to stop execution
     */
    public synchronized void stop(){
        if(!running)
            return; //if game already stop running , don't do anything down below

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * return the keymanager
     * @return KeyManager object
     */
    public KeyManager getKeyManager(){
        return keyManager;
    }

    /**
     * Return the mousemanager
     * @return current Mousemanager object
     */
    public MouseManager getMouseManager(){
        return mouseManager;
    }

    /**
     * return the gamestate
     * @return current GameState object
     */
    //public static GameState getGameState(){
        //return (GameState) gameState;
    //}
}
