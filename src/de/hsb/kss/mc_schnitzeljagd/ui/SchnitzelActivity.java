package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.McSchnitzelJagdApplication;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.gamelogic.GameCreation;
import de.hsb.kss.mc_schnitzeljagd.gamelogic.GameLogic;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SchnitzelActivity  extends Activity  {
	protected McSchnitzelJagdApplication app = null;
	protected GameLogic gameLogic = null;
	protected GameCreation gameCreation = null;
	protected TextView errorLabel = (TextView)findViewById(R.id.error_text);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (McSchnitzelJagdApplication)getApplication();
		if(app != null)
		{
			gameLogic = app.getGameLogic();
			gameCreation = app.getGameCreation();
		}
		initUi();
	}
	
	protected void initUi()
	{
		if(errorLabel != null)
		{
			errorLabel.setText("");
		}
	}	
}
