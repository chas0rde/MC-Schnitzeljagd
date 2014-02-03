package de.hsb.kss.mc_schnitzeljagd.ui.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

public class NavigatorControl extends ImageView{

	private float distance = 100.0f;
	private float[] hsv= {1.0f, 1.0f, 1.0f};
	private float amountOfColors = 88f;
	private float startColor = 1.0f;
	private float startDistance = 1000f;
	// kann geaendert werden, 100=Abstand, 88=farbschritte
	 private float distanceSteps = amountOfColors / startDistance;  
	
	

	
    public NavigatorControl(Context context) {
        super(context);
    }
    
    public NavigatorControl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
    


    @Override
    protected void onDraw(Canvas canvas) {
    	
        super.onDraw(canvas);
        Color positionColor = new Color();
        
        //distance = (float) (Math.random() * 1200); 
        
        hsv[0] = amountOfColors - (distance * distanceSteps);
        
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //canvas.drawLine(0, 0, 20, 20, p);
        Rect r = new Rect(0, 0, getWidth(), getHeight());
        p.setColor(positionColor.HSVToColor(255, hsv));
        
        p.setStyle(Paint.Style.FILL);
        
        canvas.drawRect(r, p);
        Paint p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2.setColor(Color.BLUE);
        p2.setAlpha(0);
        p2.setTextSize(10);
        canvas.drawText("test text", 0, 0, p2);
    }

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
		Toast.makeText(getContext(), "Distance:" + distance, Toast.LENGTH_SHORT).show();
	}
    
    
}
