package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PublishGameActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_game);
		boolean gameSaved = false;
		if (app != null) {
			gameSaved = app.getGameCreation().save();
		}
		initUi(gameSaved);
	}

	protected void initUi(boolean gameSaved) {
		super.initUi();
		TextView code = (TextView) findViewById(R.id.share_access_code_id);
		if (code != null && app != null) {
			code.setText( ""+ app.getGameCreation().getCurrentQuest().getKey().getId());
		}
	}

	public void backToMainActivity(View view) {
		Intent goBackToMain = new Intent(this, MainActivity.class);
		startActivity(goBackToMain);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.publish_game, menu);
		return true;
	}

	public void shareCodeTwitter(View view) {
		String tweetUrl = "https://twitter.com/intent/tweet?text=";
		String message= "Spiele eine Schnitzeljagd mit mir ! Zugangscode:";
		String code=app.getGameCreation().getCurrentQuest().getAccessCode();
		Uri uri = Uri.parse(tweetUrl+message+code);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}

	public void shareCodeEmail(View view) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		String code=app.getGameCreation().getCurrentQuest().getAccessCode();
		intent.putExtra(Intent.EXTRA_SUBJECT, "Neue Schnitzeljagd erstellt");
		intent.putExtra(Intent.EXTRA_TEXT, "Spiele eine Schnitzeljagd mit mir ! Zugangscode:"+code);
		startActivity(Intent.createChooser(intent, "Teile per Email"));
	}
	

	public void shareAdminCodeEmail(View view) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		String code=app.getGameCreation().getCurrentQuest().getAccessCode();
		intent.putExtra(Intent.EXTRA_SUBJECT, "Neue Schnitzeljagd erstellt");
		intent.putExtra(Intent.EXTRA_TEXT, "Spiele eine Schnitzeljagd mit mir ! Zugangscode:"+code);
		startActivity(Intent.createChooser(intent, "Teile per Email"));
	}

	
    public void onBackPressed() {
    	// to nothing
   }
}
