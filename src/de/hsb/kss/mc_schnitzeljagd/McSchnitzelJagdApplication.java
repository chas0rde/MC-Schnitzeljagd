package de.hsb.kss.mc_schnitzeljagd;

import de.hsb.kss.mc_schnitzeljagd.gamelogic.GameCreation;
import de.hsb.kss.mc_schnitzeljagd.gamelogic.GameLogic;
import de.hsb.kss.mc_schnitzeljagd.gamelogic.GameLogicFactory;
import android.app.Application;
 
public class McSchnitzelJagdApplication extends Application
{	
	private GameLogic currentGame = null;
	private GameCreation currentGameCreation = null;
	
	  @Override
	  public void onCreate()
	  {
	    super.onCreate();	     
	  }
	  
	  public GameCreation getGameCreation()
	  {
		  if(this.currentGameCreation == null)
		  {
			  this.currentGameCreation = GameLogicFactory.createGameCreation(); 		  
		  }
		  return this.currentGameCreation;
	  }
	  
	  public GameLogic getGameLogic()
	  {
		  if(this.currentGame == null)
		  {
			  this.currentGame = GameLogicFactory.createGameLogic(); 		  
		  }
		  return this.currentGame;
	  }
}