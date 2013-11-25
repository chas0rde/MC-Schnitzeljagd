package de.hsb.kss.mc_schnitzeljagd.gamelogic;

public class GameLogicFactory {
	
	private static final GameLogicImpl instanceGameLogic = new GameLogicImpl();
    private static final GameCreationImpl instanceCreationLogic = new GameCreationImpl();

    public static GameCreation createGameCreation(){
        return instanceCreationLogic;
    }
	
    public static GameLogic createGameLogic(){
        return instanceGameLogic;
    }
}
