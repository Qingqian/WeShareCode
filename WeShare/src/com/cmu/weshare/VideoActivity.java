package com.cmu.weshare;
import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.content.*;

public class VideoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
	}
    ////menu to select items
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        //item to select video
        case R.id.selectvideoLabel:
        startActivity(new Intent(this, SelectVideoActivity.class));
        return true;
        //item to take video
        case R.id.takevideoLabel:
        startActivity(new Intent(this, TakeVideoActivity.class));
        return true;
        default:
        return super.onOptionsItemSelected(item);
        }
    }


}
