package javaproject.states;



import javaproject.main.Game;

import java.awt.*;

public class MenuState extends State {
    Game game = Game.getGameInstance();
    //draw a start button rectangular border
    public Rectangle playButton;
    public MenuState(Game game) {
        super(game);
        playButton = new Rectangle(650,500,200,100);
    }

    @Override
    public void tick() {

        // new implementation of the State design pattern
        // for starting the game in menu , check if player clickc on the area of the start button
        if(game.getMouseManager().leftPressed){
            if(game.getMouseManager().mouseClickedX>=650 && game.getMouseManager().mouseClickedX <=850 && game.getMouseManager().mouseClickedY>=500 && game.getMouseManager().mouseClickedY <= 600){
            //start button pressed
            //State.setState(Game.getGameState());
                game.changeCurrentState(new GameState(game));
            }
        }
    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,1600,900);
        Font font = new Font(Font.MONOSPACED,Font.PLAIN,50);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Shooting Game",550,200);

        Graphics2D g2d = (Graphics2D)g;
        g2d.draw(playButton);

        g.drawString("Play",playButton.x+35,playButton.y+65);

    }

}
