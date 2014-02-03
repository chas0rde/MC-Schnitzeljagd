package de.hsb.kss.mc_schnitzeljagd.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
	
	
	
		// TODO Auto-generated method stub
		public void onReceive(Context context, Intent intent) {
		    Log.w("Tag BROADCAST", "ONRECEIVE");

			Intent service = new Intent(context, RiddleListActivity.class);
			service.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(service);
			
		}
}
