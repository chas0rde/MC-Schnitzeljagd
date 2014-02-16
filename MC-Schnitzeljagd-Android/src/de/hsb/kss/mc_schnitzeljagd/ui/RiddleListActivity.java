package de.hsb.kss.mc_schnitzeljagd.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;

public class RiddleListActivity extends SchnitzelActivity {
	private Riddle currentRiddle = null;
	private List<RadioButton> optionsRadioButton= new ArrayList<RadioButton>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riddle_list);
		initUi();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.riddle_list, menu);
		return true;
	}
	
	@Override
	protected void initUi()
	{		
		super.initUi();		
		optionsRadioButton.add((RadioButton) findViewById(R.id.riddle_radio_button_1));
		optionsRadioButton.add((RadioButton) findViewById(R.id.riddle_radio_button_2));
		optionsRadioButton.add((RadioButton) findViewById(R.id.riddle_radio_button_3));
		optionsRadioButton.add((RadioButton) findViewById(R.id.riddle_radio_button_4));	
	    Button myButton = (Button)findViewById(R.id.skip_button);
	    myButton.setVisibility(View.GONE);

		
		currentRiddle = gameLogic.getNextRiddle();
		if(currentRiddle != null) {
			((TextView)findViewById(R.id.player_riddle_text_field)).setText(currentRiddle.getRiddleText());	    
		    ((TextView)findViewById(R.id.riddle_answer_field_1)).setText(currentRiddle.getAnswer1());
		    ((TextView)findViewById(R.id.riddle_answer_field_2)).setText(currentRiddle.getAnswer2());
		    ((TextView)findViewById(R.id.riddle_answer_field_3)).setText(currentRiddle.getAnswer3());
		    ((TextView)findViewById(R.id.riddle_answer_field_4)).setText(currentRiddle.getAnswer4());		     
		    if(!currentRiddle.getMandatory()) {
		    	myButton.setVisibility(View.VISIBLE);
		    } else {
		    	myButton.setVisibility(View.GONE);
		    }
		} else {
			Toast.makeText(getApplicationContext(), "All Riddles solved!", Toast.LENGTH_LONG).show();
			if (gameLogic.goToNextPoint(true) == null) {
				startActivity(new Intent(getApplicationContext(), FinishActivity.class));
			} else {
				startActivity(new Intent(getApplicationContext(), HintActivity.class));
			}
		}
	
	}
	
	/** 
	 * set all radiobuttons on false except the clicked one
	 * @param view
	 */
	public void radioButtonChanged(View view) {
		
		for(RadioButton radio: optionsRadioButton){
			radio.setChecked(false);
		}
		
		RadioButton myClicked = (RadioButton)view;
		myClicked.setChecked(true);		
	}
	
	// Todo if all pois were reached.. go to finish activity with a overview of the quest. 
	public void goToNextRiddle(View view) {
		boolean solved = false; //currentRiddle.getSolved();
		
		solved = solved?true:((RadioButton)findViewById(R.id.riddle_radio_button_1)).isChecked() && (currentRiddle.getSolution() == 1);			   
		solved = solved?true:((RadioButton)findViewById(R.id.riddle_radio_button_2)).isChecked() && (currentRiddle.getSolution() == 2);			   
		solved = solved?true:((RadioButton)findViewById(R.id.riddle_radio_button_3)).isChecked() && (currentRiddle.getSolution() == 3);			   
		solved = solved?true:((RadioButton)findViewById(R.id.riddle_radio_button_4)).isChecked() && (currentRiddle.getSolution() == 4);			   

		
		if(solved) {
			currentRiddle.setSolved(true);
			startActivity(new Intent(getApplicationContext(), RiddleListActivity.class));	
		} else {
			setErrorMsg("Wrong Answer!");
		}
	}
	
	public void skipRiddle(View view) {
		currentRiddle.setSolved(true);
		startActivity(new Intent(getApplicationContext(), RiddleListActivity.class));	
	}
	
    public void onBackPressed() {
    	// to nothing
   }

}