package com.cmu.weshare;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class TakeVideoActivity extends Activity implements SurfaceHolder.Callback {
	private MediaRecorder recorder = null;
	private static final String TAG = "RecordVideo";
	private VideoView videoView = null;
	private Button startBtn = null;
	private Uri fileUri;
	File file;
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_video);
		startBtn = (Button) findViewById(R.id.bgnBtn);
		Button endBtn = (Button) findViewById(R.id.stpBtn);
		Button playRecordingBtn = (Button) findViewById(R.id.playRecordingBtn);
		Button stpPlayingRecordingBtn = (Button) findViewById(R.id.stpPlayingRecordingBtn);
		videoView = (VideoView)this.findViewById(R.id.videoView);
		final SurfaceHolder holder = videoView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    //click the begin recording button
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) 
			{
				try 
				{
					 file = getOutputVideoFile();
					fileUri = Uri.fromFile(getOutputVideoFile());
					beginRecording(holder);
				} catch (Exception e) 
				{
					Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});
	    //click the stop recording button
		endBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				try 
				{
					stopRecording();
				} 
				catch (Exception e) 
				{
					Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});
	    //click the play recording button
		playRecordingBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				try 
				{
					playRecording();
				} 
				catch (Exception e) 
				{
					Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});
		//click the stop playing recording button
		stpPlayingRecordingBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				try 
				{
					stopPlayingRecording();
				} 
				catch (Exception e) 
				{
					Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});
    }
	
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
				startBtn.setEnabled(true);
		}
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) 
		{
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
		{
			Log.v(TAG, "Width x Height = " + width + "x" + height);
		}
		
		private void playRecording() {
			MediaController mc = new MediaController(this);
			videoView.setMediaController(mc);
			videoView.setVideoPath(fileUri.getPath());
			videoView.start();
		}
		
		private void stopPlayingRecording() {
			videoView.stopPlayback();
		}
		
		private void stopRecording() throws Exception {
			if (recorder != null) {
				recorder.stop();
			}
		}
		
		@Override
		protected void onDestroy() {
			super.onDestroy();
			if (recorder != null) 
			{
				recorder.release();
			}
		}
		//begin recording
		private void beginRecording(SurfaceHolder holder) throws Exception 
		{
			if(recorder!=null)
			{
				recorder.stop();
				recorder.release();
			}
			File outFile = new File(fileUri.getPath());
			if(outFile.exists())
			{
				outFile.delete();
			}
			
			try 
			{
				recorder = new MediaRecorder();
				recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
				recorder.setVideoSize(176, 144);
				recorder.setVideoFrameRate(15);
				recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				recorder.setMaxDuration(30000); // limit to 30 seconds
				recorder.setPreviewDisplay(holder.getSurface());
				recorder.setOutputFile(fileUri.getPath());
				recorder.prepare();
				recorder.start();
			}
			catch(Exception e) 
			{
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
		}
		//create file in the divice
		private File getOutputVideoFile() {
			  File directory = new File(Environment.getExternalStoragePublicDirectory(
			                Environment.DIRECTORY_MOVIES), getPackageName());
			  if (!directory.exists()) {
			    if (!directory.mkdirs()) {
			      Log.e(TAG, "Failed to create storage directory.");
			      return null;
			    }
			  }
			  String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
			  return new File(directory.getPath() + File.separator + "VIDEO_"  
			                    + timeStamp + ".mp4");
			}
		//keep the activity even if the screen orienctation changed
		@Override
		public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
		// land do nothing is ok
			Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT).show();
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
		// port do nothing is ok
			 Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT).show();
		}
		}

	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_video, menu);
		return true;
	}

}
