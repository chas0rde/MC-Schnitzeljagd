package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class HintActivity extends SchnitzelActivity {

	ViewFlipper hintFliper = null;
	private float lastX;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hint);
		
		initUi();
	}
		
	protected void initUi() {
		super.initUi();
		hintFliper = (ViewFlipper) findViewById(R.id.hint_view_flipper_id);
		if(app != null)
		{
			TextView gameInfo = (TextView)findViewById(R.id.current_game_info);
			if(gameInfo != null)
			{
				gameInfo.setText(app.getGameLogic().getPlayer().getName());
			}
		}			
	}

   public boolean onTouchEvent(MotionEvent touchevent)
   {
	   switch (touchevent.getAction())
       {
              // when user first touches the screen to swap
               case MotionEvent.ACTION_DOWN:
               {
                   lastX = touchevent.getX();
                   break;
              }
               case MotionEvent.ACTION_UP:
               {
                   float currentX = touchevent.getX();
                  
                   // if left to right swipe on screen
                   if (lastX < currentX)
                   {
                        // If no more View/Child to flip
                       //if (hintFliper.getDisplayedChild() == 0)
                          // break;
                      
                       // set the required Animation type to ViewFlipper
                       // The Next screen will come in form Left and current Screen will go OUT from Right
                       hintFliper.setInAnimation(this, R.anim.in_from_left);
                       hintFliper.setOutAnimation(this, R.anim.out_to_left);
                       // Show the next Screen
                       hintFliper.showNext();
                   }
                  
                   // if right to left swipe on screen
                   if (lastX > currentX)
                   {                       
                       // set the required Animation type to ViewFlipper
                       // The Next screen will come in form Right and current Screen will go OUT from Left
                       hintFliper.setInAnimation(this, R.anim.in_from_right);
                       hintFliper.setOutAnimation(this, R.anim.out_to_right);
                       // Show The Previous Screen
                       if (hintFliper.getDisplayedChild() == 1)
                       {
                    	   
                       }                       
                       hintFliper.showPrevious();
                   }
                   break;
               }
       }
       return false;
   }
	
	public void goToQuestActivity(View view) {
		Intent goToQuest = new Intent(this, QuestActivity.class);
		startActivity(goToQuest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hint, menu);
		return true;
	}

}
