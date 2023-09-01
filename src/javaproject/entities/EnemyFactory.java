package javaproject.entities;

import javaproject.main.Game;


//Simple factory implentation for enemy creation
public class EnemyFactory {
    public Creature getCreature(String creatureType, float x , float y) throws Exception {
        Creature returnCreature;
        switch(creatureType){
            case "crawler":
                returnCreature =  new Crawler(x, y, Player.getPlayerInstance(Game.getGameInstance()));
                break;
            case "walker":
                returnCreature =  new Walker(x,y,Player.getPlayerInstance(Game.getGameInstance()));
                break;

            case "tank":
                returnCreature =  new Tank(x,y,Player.getPlayerInstance(Game.getGameInstance()));
                break;

            default:
                throw new Exception("Invalid creature type");
        }
        return returnCreature;
    }
}
