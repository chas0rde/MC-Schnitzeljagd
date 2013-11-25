package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.McSchnitzelJagdApplication;
import de.hsb.kss.mc_schnitzeljagd.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SchnitzelActivity  extends Activity  {
	protected McSchnitzelJagdApplication app = null;
	protected TextView errorLabel = (TextView)findViewById(R.id.error_text);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (McSchnitzelJagdApplication)getApplication();
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
