package de.hsb.kss.mc_schnitzeljagd;

import de.hsb.kss.mc_schnitzeljagd.gamelogic.GameLogic;
import de.hsb.kss.mc_schnitzeljagd.gamelogic.GameLogicFactory;
import android.app.Application;
 
public class McSchnitzelJagdApplication extends Application
{	
	private GameLogic currentGame = null;
	
	  @Override
	  public void onCreate()
	  {
	    super.onCreate();	     
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