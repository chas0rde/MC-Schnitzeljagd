package de.hsb.kss.mc_schnitzeljagd.ui.controls;

import java.util.Enumeration;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.ui.ListHintsActivity;
import de.hsb.kss.mc_schnitzeljagd.ui.PlayerTextRiddleActivity;
import de.hsb.kss.mc_schnitzeljagd.ui.PlayerTextHintActivity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class HintsControl extends LinearLayout implements Button.OnClickListener{
	private Spinner hintType;
	private Button numberOfHints;
	private Button addHint;
	
	public enum HintMode{HINT, RIDDLE}
	private HintMode hintmode;
	
	public HintMode getHintmode() {
		return hintmode;
	}

	public void setHintmode(HintMode hintmode) {
		this.hintmode = hintmode;
	}

	public HintsControl (Context context, AttributeSet attributeSet){
		super(context, attributeSet);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.control_hints, this);
        initUi();
	}
	
    public HintsControl(Context context) {
        super(context);
        initUi();
    }
    
    public void initUi(){
    	 hintType = (Spinner)findViewById(R.id.record_hint_spinner_id);
    	 numberOfHints = (Button)findViewById(R.id.number_of_hints_button);
    	 numberOfHints.setOnClickListener(this); // use existing onclicklistener
    	 
    	 // implement new onclick listener
    	 addHint = (Button)findViewById(R.id.add_hint);
    	 addHint.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// add onclick listener that will show list of hints/riddles
				Spinner hintType = (Spinner)findViewById(R.id.record_hint_spinner_id);
				
				if(hintType != null)
				{	
					Intent intent = null;
					
					if(hintmode == HintMode.HINT){
						intent = new Intent(view.getContext(), PlayerTextHintActivity.class);
						
						intent.putExtra("HintType", (int)hintType.getSelectedItemId());
						view.getContext().startActivity(intent);
					} else if(hintmode == HintMode.RIDDLE) {
						intent = new Intent(view.getContext(), PlayerTextRiddleActivity.class);	
						intent.putExtra("HintType", hintType.getSelectedItemId());
						view.getContext().startActivity(intent);
					}
					
				}
			}
    		 
    	 });
    }
    
    
    
    public void setHintNumber(int hintNumber){
    	numberOfHints.setText(Integer.toString(hintNumber));
    }
    
    public void setHintButtonText(String buttonAddHintText){
    	addHint.setText("Add "+buttonAddHintText);
    } 

	@Override
	public void onClick(View v) {
		Intent showListOfHints = new Intent(this.getContext(), ListHintsActivity.class);
		showListOfHints.putExtra("hintmode", hintmode.name());
		this.getContext().startActivity(showListOfHints);
	}
}
