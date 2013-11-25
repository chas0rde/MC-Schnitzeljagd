package de.hsb.kss.mc_schnitzeljagd.gamelogic;

public class GameLogicFactory {
	
	private static final GameLogicImpl instanceGameLogic = new GameLogicImpl();  
	
	public static GameCreation createGameCreation(){
        return instanceGameLogic;
    }
	
    public static GameLogic createGameLogic(){
        return instanceGameLogic;
    }
}
