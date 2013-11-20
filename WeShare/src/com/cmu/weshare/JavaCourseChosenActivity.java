package com.cmu.weshare;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class JavaCourseChosenActivity extends Activity {
	private Button images;
	private Button documents;
	private Button videos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_chosen);
	    images = (Button)findViewById(R.id.imagesButton);
		documents = (Button)findViewById(R.id.documentsButton);
		videos = (Button)findViewById(R.id.videosButton);
		images.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v){
				Intent javaIntent = new Intent(JavaCourseChosenActivity.this,ImageActivity.class);
				startActivity(javaIntent);
			}
			
		});
		
		documents.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v){
				Intent mobileIntent = new Intent(JavaCourseChosenActivity.this,DocumentsActivity.class);
				startActivity(mobileIntent);
			}
			
		});
		
		videos.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v){
				Intent machineIntent = new Intent(JavaCourseChosenActivity.this,VideoActivity.class);
				startActivity(machineIntent);
			}
			
		});
		
		
	}
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.java_course_chosen, menu);
		return true;
	}

}
