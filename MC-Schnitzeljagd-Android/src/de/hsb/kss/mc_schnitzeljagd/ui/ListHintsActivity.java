package de.hsb.kss.mc_schnitzeljagd.ui;

import java.util.ArrayList;
import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import de.hsb.kss.mc_schnitzeljagd.logic.GameCreation;
import de.hsb.kss.mc_schnitzeljagd.logic.LogicHelper;

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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListHintsActivity extends SchnitzelActivity {
	private HintMode hintmode;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_hints);
		
		initUi();
	}

	
	
	@Override
	protected void initUi()
	{
		hintmode = HintMode.HINT;
		
		super.initUi();
		if(this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("hintmode")) {
			 hintmode = HintMode.valueOf(this.getIntent().getExtras().getString("hintmode"));
		}
		
		List<String> pointHints = new ArrayList<String>();
		BaseAdapter hintListAdapter = null;
		
		if(hintmode == HintMode.HINT)
		{
			hintListAdapter = new LazyAdapter(this, gameCreation.getCurrentPoint().getHintList());
		} else if (hintmode == HintMode.RIDDLE) {
			 hintListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_view_text_template, pointHints);

			for (Riddle riddle : gameCreation.getCurrentPoint().getRiddles()){	
				// Todo set Riddle type
				pointHints.add(riddle.getRiddleText() + (riddle.getMandatory()? "(mandatory)" : "(optional)"));
			}
		}
		

		
		ListView listView = (ListView)findViewById(R.id.list_of_hints_id);
		if(listView != null){
			listView.setAdapter(hintListAdapter);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
					// TODO Auto-generated method stub
					if(hintmode == HintMode.HINT){
						Intent goToHintActivity = new Intent (parentView.getContext(), PlayerTextHintActivity.class);
						goToHintActivity.putExtra("hintId", position);
						goToHintActivity.putExtra("HintType", LogicHelper.TranslateType(gameCreation.getCurrentPoint().getHintList().get(position).getHintType()));
						startActivity(goToHintActivity);
					} else if(hintmode == HintMode.RIDDLE){
						Intent goToHintActivity = new Intent (parentView.getContext(), PlayerTextRiddleActivity.class);
						goToHintActivity.putExtra("hintId", position);
						startActivity(goToHintActivity);
					}
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
