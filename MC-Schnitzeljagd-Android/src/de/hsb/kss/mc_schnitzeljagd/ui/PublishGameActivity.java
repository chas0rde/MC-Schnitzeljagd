package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
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
			code.setText(gameSaved + " "
					+ app.getGameCreation().getCurrentQuest().getAccessCode());
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

	public void shareCodeFacebook(View view) {
		String facebookurl="https://www.facebook.com/sharer/sharer.php?u=https://github.com/chas0rde/MC-Schnitzeljagd";
		String message= "Spiele eine Schnitzeljagd mit mir ! Zugangscode:";
		String code=app.getGameCreation().getCurrentQuest().getAccessCode();
//		Uri uri = Uri.parse(tweetUrl+message+code);
//		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}

	public void shareCodePinterest(View view) {

	}

	public void shareCodeEmail(View view) {

	}

}
