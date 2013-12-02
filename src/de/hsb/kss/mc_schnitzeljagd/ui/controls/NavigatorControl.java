package de.hsb.kss.mc_schnitzeljagd.ui.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;

public class NavigatorControl extends ImageView{

	private float distance = 100.0f;
	
    public NavigatorControl(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //canvas.drawLine(0, 0, 20, 20, p);
        Rect r = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawRect(r, p);
        canvas.drawText(Float.toString(distance), 10, 10, p);        
    }

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
    
    
}
