package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.McSchnitzelJagdApplication;
import de.hsb.kss.mc_schnitzeljagd.R;
import android.app.Activity;
import android.os.Bundle;

public class SchnitzelActivity  extends Activity  {
	protected McSchnitzelJagdApplication app = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (McSchnitzelJagdApplication)getApplication();
	}
	
}
