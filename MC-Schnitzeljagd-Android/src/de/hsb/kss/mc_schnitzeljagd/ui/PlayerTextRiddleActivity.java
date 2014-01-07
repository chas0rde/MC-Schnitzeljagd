package de.hsb.kss.mc_schnitzeljagd.ui;

import java.util.ArrayList;
import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class PlayerTextRiddleActivity extends SchnitzelActivity {
	private Riddle currentRiddle = null;
	private List<RadioButton> optionsRadioButton= new ArrayList<RadioButton>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_text_riddle);
		
		initUi();		
	}
	
	@Override
	protected void initUi()
	{		
		super.initUi();		
		optionsRadioButton.add((RadioButton) findViewById(R.id.riddle_radio_button_1));
		optionsRadioButton.add((RadioButton) findViewById(R.id.riddle_radio_button_2));
		optionsRadioButton.add((RadioButton) findViewById(R.id.riddle_radio_button_3));
		optionsRadioButton.add((RadioButton) findViewById(R.id.riddle_radio_button_4));
		
		// If Activity was started by ListHintsActivity 		
		if(this.getIntent() != null && this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("hintId") ){
					
			int riddleId = this.getIntent().getExtras().getInt("hintId");	
			currentRiddle = gameCreation.getRiddle(riddleId);
					
			
		    if(currentRiddle != null) {		    
				((EditText)findViewById(R.id.player_riddle_text_field)).setText(currentRiddle.getRiddleText());	    
			    ((EditText)findViewById(R.id.riddle_answer_field_1)).setText(currentRiddle.getAnswer1());
			    ((EditText)findViewById(R.id.riddle_answer_field_2)).setText(currentRiddle.getAnswer2());
			    ((EditText)findViewById(R.id.riddle_answer_field_3)).setText(currentRiddle.getAnswer3());
			    ((EditText)findViewById(R.id.riddle_answer_field_4)).setText(currentRiddle.getAnswer4());		    
			    ((CheckBox)findViewById(R.id.riddle_is_mandatory_id)).setChecked(currentRiddle.getMandatory());
			    
			    if(currentRiddle.getSolution() == 1) {			   
			    	((RadioButton)findViewById(R.id.riddle_radio_button_1)).setChecked(true);
			    	
			    } else if(currentRiddle.getSolution() == 2) {			   
			    	((RadioButton)findViewById(R.id.riddle_radio_button_2)).setChecked(true);
			    } else if(currentRiddle.getSolution() == 3) {			   
			    	((RadioButton)findViewById(R.id.riddle_radio_button_3)).setChecked(true);
			    } else if(currentRiddle.getSolution() == 4) {			   
			    	((RadioButton)findViewById(R.id.riddle_radio_button_4)).setChecked(true);
			    }
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
	
	public void goToCreateRiddle(View view){
		
		// if new Hint create it
		if(currentRiddle == null) {
			 currentRiddle = new Riddle();
			 gameCreation.addRiddle(currentRiddle);
		}
		
		String errorMessage="";
		
		// Check if mandatory Riddle
		CheckBox riddleIsMandatory = (CheckBox)findViewById(R.id.riddle_is_mandatory_id);
		currentRiddle.setMandatory(riddleIsMandatory.isChecked());
		
		// set riddle text
		String riddleHintText = ((EditText)findViewById(R.id.player_riddle_text_field)).getText().toString();
		if(riddleHintText != null && !riddleHintText.isEmpty() ) {			
			currentRiddle.setRiddleText(riddleHintText);
		} else {
			errorMessage += ("\nPlease provide a Hinttext");
		}
		
		// set riddle answer options
		String riddleAnswerOption = ((EditText)findViewById(R.id.riddle_answer_field_1)).getText().toString();
		if(riddleAnswerOption != null && !riddleAnswerOption.isEmpty()) {			
			currentRiddle.setAnswer1(riddleAnswerOption);
		} else {
			errorMessage += ("\nPlease provide a Answertext for Option 1");
		}
		
	    riddleAnswerOption = ((EditText)findViewById(R.id.riddle_answer_field_2)).getText().toString();
		if(riddleAnswerOption != null && !riddleAnswerOption.isEmpty()) {			
			currentRiddle.setAnswer2(riddleAnswerOption);
		} else {
			errorMessage += ("\nPlease provide a Answertext for Option 2");
		}
		
		riddleAnswerOption = ((EditText)findViewById(R.id.riddle_answer_field_3)).getText().toString();
		if(riddleAnswerOption != null && !riddleAnswerOption.isEmpty()) {			
			currentRiddle.setAnswer3(riddleAnswerOption);
		} else {
			errorMessage += ("\nPlease provide a Answertext for Option 3");
		}
		
		riddleAnswerOption = ((EditText)findViewById(R.id.riddle_answer_field_4)).getText().toString();
		if(riddleAnswerOption != null && !riddleAnswerOption.isEmpty()) {			
			currentRiddle.setAnswer4(riddleAnswerOption);
		} else {
			errorMessage += ("\nPlease provide a Answertext for Option 4");
		}
		
		// get checked answer option and set it as riddle answer
		if(((RadioButton)findViewById(R.id.riddle_radio_button_1)).isChecked()) {
			currentRiddle.setSolution(1);
		}else if(((RadioButton)findViewById(R.id.riddle_radio_button_2)).isChecked()) {
			currentRiddle.setSolution(2);
		}else if(((RadioButton)findViewById(R.id.riddle_radio_button_3)).isChecked()) {
			currentRiddle.setSolution(3);
		} else if(((RadioButton)findViewById(R.id.riddle_radio_button_4)).isChecked()) {
			currentRiddle.setSolution(4);
		} else {
			errorMessage += ("\nPlease Check correct Answeroption");
		}
			
				
		if(errorMessage.isEmpty()){
			Intent intent = new Intent(this, OrganizerCreatePoiActivity.class);
			startActivity(intent);
		} else {
			setErrorMsg(errorMessage);
		}
	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_riddle, menu);
		return true;
	}

}
