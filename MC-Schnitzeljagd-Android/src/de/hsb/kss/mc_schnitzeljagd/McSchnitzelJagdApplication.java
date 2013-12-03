package de.hsb.kss.mc_schnitzeljagd;

import de.hsb.kss.mc_schnitzeljagd.logic.GameCreation;
import de.hsb.kss.mc_schnitzeljagd.logic.GameFactory;
import de.hsb.kss.mc_schnitzeljagd.logic.GameLogic;
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
			  this.currentGameCreation = GameFactory.createGameCreation();
		  }
		  return this.currentGameCreation;
	  }
	  
	  public GameLogic getGameLogic()
	  {
		  if(this.currentGame == null)
		  {
			  this.currentGame = GameFactory.createGameLogic();
		  }
		  return this.currentGame;
	  }
}