package de.hsb.kss.mc_schnitzeljagd.ui;

import java.util.ArrayList;
import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;

import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;
import de.hsb.kss.mc_schnitzeljagd.ui.controls.HintsControl.HintMode;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListHintsActivity extends SchnitzelActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_hints);
		
		initUi();
	}

	
	
	@Override
	protected void initUi()
	{
		HintMode hintmode = HintMode.HINT;
		super.initUi();
		if(this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("hintmode")) {
			 hintmode = HintMode.valueOf(this.getIntent().getExtras().getString("hintmode"));
		}
		List<String> pointHints = new ArrayList<String>();
		
		if(hintmode == HintMode.HINT){
			for (Hint hint : gameCreation.getCurrentPoint().getHintList()){
				pointHints.add(hint.getHintType() + ": " +hint.getDescription());
			}
		} else if(hintmode == HintMode.RIDDLE){
			for (Riddle riddle : gameCreation.getCurrentPoint().getRiddles()){
				pointHints.add(riddle.getSolution() + (riddle.getMandatory()? " is man" : "is opt"));
			}
		}
		

		
		ArrayAdapter<String> hintListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_view_text_template, pointHints);
		ListView listView = (ListView)findViewById(R.id.list_of_hints_id);
		if(listView != null){
			listView.setAdapter(hintListAdapter);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
					// TODO Auto-generated method stub
					Intent goToHintActivity = new Intent (parentView.getContext(), PlayerTextHintActivity.class);
					goToHintActivity.putExtra("hintId", position);
					startActivity(goToHintActivity);
				}
			});
		}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_hints, menu);
		return true;
	}

}
