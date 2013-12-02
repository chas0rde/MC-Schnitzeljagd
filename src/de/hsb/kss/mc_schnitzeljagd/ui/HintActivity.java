package de.hsb.kss.mc_schnitzeljagd.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.Hint.HintType;
import de.hsb.kss.mc_schnitzeljagd.persistence.Point;

import java.util.List;

public class HintActivity extends SchnitzelActivity {

	ViewFlipper hintFlipper = null;
	Point currentPoint = null;
	List<Hint> listOfHints = null;
	List<Hint> listOfFreeHints = null;
	private float lastX;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hint);
		
		initUi();
	}
		
	protected void initUi() {
		super.initUi();
		hintFlipper = (ViewFlipper) findViewById(R.id.hint_view_flipper_id);
		if(app != null)
		{
			TextView gameInfo = (TextView)findViewById(R.id.current_game_info);
			if(gameInfo != null)
			{
				gameInfo.setText(app.getGameLogic().getPlayer().getName());				
			}
			currentPoint = app.getGameLogic().goToNextPoint();
			listOfHints = app.getGameLogic().getFreeHintsForCurrentPoint();
			listOfFreeHints = app.getGameLogic().getFreeHintsForCurrentPoint();
			
			renderHintFlipper();
		}			
	}

	private void renderHintFlipper()
	{
		if(hintFlipper != null)
		{
			hintFlipper.removeAllViews();
			for(Hint h : listOfFreeHints)
			{
				if(h.getHintType() == HintType.TEXT)
				{
					TextView tv = new TextView(hintFlipper.getContext());
					tv.setText(h.getDescription());
					tv.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
					hintFlipper.addView(tv);
				}
				else if(h.getHintType() == HintType.IMAGE)
				{
					// TODO: Sp�ter
				}
				else if(h.getHintType() == HintType.SOUND)
				{
					// TODO: Sp�ter
				}
				else if(h.getHintType() == HintType.VIDEO)
				{
					// TODO: Sp�ter
				}
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
                       hintFlipper.setInAnimation(this, R.anim.in_from_left);
                       hintFlipper.setOutAnimation(this, R.anim.out_to_left);
                       // Show the next Screen
                       hintFlipper.showNext();
                   }
                  
                   // if right to left swipe on screen
                   if (lastX > currentX)
                   {                       
                       // set the required Animation type to ViewFlipper
                       // The Next screen will come in form Right and current Screen will go OUT from Left
                       hintFlipper.setInAnimation(this, R.anim.in_from_right);
                       hintFlipper.setOutAnimation(this, R.anim.out_to_right);
                       // Show The Previous Screen
                       if (hintFlipper.getDisplayedChild() == 1)
                       {
                    	   
                       }                       
                       hintFlipper.showPrevious();
                   }
                   break;
               }
       }
       return false;
   }
	
	public void clickBuyHint(View view) {
		if( app.getGameLogic().freeNextHintForCurrentPoint() )
		{
			renderHintFlipper();
		}		
		else
		{
			Toast.makeText(this.getBaseContext(), R.string.no_more_hints, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hint, menu);
		return true;
	}

}
