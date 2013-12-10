package de.hsb.kss.mc_schnitzeljagd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;

public class PlayerTextHintActivity extends SchnitzelActivity {
	Hint currentHint = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_text_hint);
		initUi();
	}
	
	
	
	protected void initUi()
	{
		super.initUi();
		// If Activity was started by ListHintsActivity 
		
		if(this.getIntent() != null && this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("hintId") ){

			EditText editText = (EditText)findViewById(R.id.hint_text_hint_id);
			
			
			int hintId = this.getIntent().getExtras().getInt("hintId");	
			currentHint = gameCreation.getHint(hintId);
			editText.setText(currentHint.getDescription());
		}

		
	}
	
	// adds hint to current Point
	public void startSaveTextHint(View view){
		EditText hintText = (EditText)findViewById(R.id.hint_text_hint_id);
		
		if(gameCreation != null && hintText != null)
		{
			if (hintText.getText().toString().isEmpty()){
				setErrorMsg("Please set hinttext");
			} else {
				
				// if new Hint create it
				if(currentHint == null) {
					 currentHint = new Hint();
					 gameCreation.addHint(currentHint);
				}
			
				// if save new values for hint 
				currentHint.setDescription(hintText.getText().toString());
				currentHint.setHintType("TEXT");

				Intent intent = new Intent(this, OrganizerCreatePoiActivity.class);
				startActivity(intent);
			}
		} 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_text_hint, menu);
		return true;
	}

}
