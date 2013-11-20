package com.cmu.weshare;
import android.os.*;
import android.app.Activity;
import android.content.*;
import android.view.*;
import android.widget.*;

public class SelectCourseActivity extends Activity {
	private Button javaCourse;
	private Button mobileCourse;
	private Button machineCourse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_course);
	    javaCourse = (Button)findViewById(R.id.javaButton);
		mobileCourse = (Button)findViewById(R.id.mobileButton);
		machineCourse = (Button)findViewById(R.id.machineButton);
		javaCourse.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v){
				Intent javaIntent = new Intent(SelectCourseActivity.this,JavaCourseChosenActivity.class);
				startActivity(javaIntent);
			}
			
		});
		
		mobileCourse.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v){
				Intent mobileIntent = new Intent(SelectCourseActivity.this,MobileCourseChosenActivity.class);
				startActivity(mobileIntent);
			}
			
		});
		
		machineCourse.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v){
				Intent machineIntent = new Intent(SelectCourseActivity.this,MachineCourseChosenActivity.class);
				startActivity(machineIntent);
			}
			
		});
		
		
	}
	//menu to select items
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_course, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        //download item
        case R.id.downloadHis:
        startActivity(new Intent(this, DownloadHistory.class));
        return true;
        //upload item
        case R.id.uploadHis:
        startActivity(new Intent(this, UploadHistory.class));
        return true;
        default:
        return super.onOptionsItemSelected(item);
        }
    }
}
