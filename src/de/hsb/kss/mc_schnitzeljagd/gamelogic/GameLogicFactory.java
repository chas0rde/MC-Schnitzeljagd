package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;

public class GameLogicFactory {
    public static GameLogic createGameLogic(){
        return new GameLogicImpl();
    }
}
