package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class PlayerTextHintActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_text_hint);
	}
	
	public void startSaveTextHint(View view){
		EditText hintText = (EditText)findViewById(R.id.hint_text_hint_id);
		
		if(gameCreation != null && hintText != null)
		{
			Tip t = new Tip();
			
			t.setDescription(hintText.getText().toString());
			gameCreation.addHint(t);
			Intent intent = new Intent(this, OrganizerCreatePoiActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_text_hint, menu);
		return true;
	}

}
