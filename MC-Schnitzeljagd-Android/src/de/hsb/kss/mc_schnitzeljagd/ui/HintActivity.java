package de.hsb.kss.mc_schnitzeljagd.ui;

import java.util.List;

import com.google.android.gms.common.data.Freezable;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class HintActivity extends SchnitzelActivity {
	ViewFlipper hintFlipper = null;
	Point currentPoint = null;
	List<Hint> listOfHints = null;
	List<Riddle> listOfRiddles = null;
	List<Hint> listOfFreeHints = null;
	private float lastX;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hint);
		
		initUi();
	}
	

    public void changeColor(View view) {
    	
    	view.invalidate();
    	
    }
		
	protected void initUi() {
		super.initUi();
		hintFlipper = (ViewFlipper) findViewById(R.id.hint_view_flipper_id);			
		
		if(app != null)
		{
			TextView gameInfo = (TextView)findViewById(R.id.current_game_info);
			if(gameInfo != null)
			{
				gameInfo.setText(app.getGameLogic().getCurrentGameInfo());				
				
			}
			currentPoint = app.getGameLogic().goToNextPoint();
			listOfHints = app.getGameLogic().getFreeHintsForCurrentPoint();
			listOfFreeHints = app.getGameLogic().getFreeHintsForCurrentPoint();
			listOfRiddles = currentPoint.getRiddles();

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
				if(h.getHintType().equals("TEXT"))
				{
					TextView tv = new TextView(hintFlipper.getContext());
					tv.setText(h.getDescription());
					tv.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
					hintFlipper.addView(tv);
				}
				else if(h.getHintType().equals("IMAGE"))
				{
					// -----------------------------------------------------------------------
					ImageView preview = new ImageView(hintFlipper.getContext());					
			        byte[] imageAsBytes = Base64.decode(h.getDescription().getBytes(), 0);
			        preview.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
					preview.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
					hintFlipper.addView(preview);
					// -----------------------------------------------------------------------
				}
				else if(h.getHintType().equals("SOUND"))
				{
					// TODO: Spaeter
				}
				else if(h.getHintType().equals("VIDEO"))
				{
					// TODO: Spaeter
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
	
   
   public void startRiddlesActivity(View view) {
	  startActivity(new Intent(getApplicationContext(), RiddleListActivity.class)); 
   }
   
	public void clickBuyHint(View view) {
		if( app.getGameLogic().freeNextHint() )
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
