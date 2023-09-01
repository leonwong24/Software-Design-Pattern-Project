package javaproject.states;
import java.awt.Graphics;

import javaproject.main.Game;
import javaproject.main.TickAndRender;

/** Most of the class will be using this tick and render to keep updating game logic and render the image*/
public abstract class State implements TickAndRender {

    //Variable
    protected Game game;

    //Constructor
    public State(Game game){
        this.game = game;
    }

    //methods
    public abstract void tick();

    public abstract void render(Graphics g);

    //variable
    private static State currentState = null;

    // old implementation of state pattern
    //public static void setState(State state){
        //currentState = state;
    //}

    // old implementation of state pattern
    //public static State getCurrentState() {
        //return currentState;
    //}

}
