package com.cmu.weshare;
import java.io.*;

import java.net.URISyntaxException;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.net.Uri;
import android.os.*;
import android.app.*;
import android.content.*;
import android.database.Cursor;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class SelectVideoActivity extends Activity {
	private static final int SELECT_VIDEO = 2;
//    String selectedPath = "/sdcard";
    byte[] byteBinaryData;
    String path;
    InputStream is;
    VideoView myVideoView;
//    private static final int SELECT_AUDIO = 2;
    String selectedPath = "";
    Button upload;
    String string = "hello";
    byte[]test;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_video);
		myVideoView = (VideoView)findViewById(R.id.selectVideoView);
		
		openGalleryAudio();
    }

    public void openGalleryAudio(){

    Intent intent = new Intent();
    intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video "), SELECT_VIDEO);
   }

    public static String getPathNew(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
                cursor.close();
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
   
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    	if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                Uri selectedVideoUri = data.getData();
            
             // Get the Uri of the selected file 
                Log.d("Debugging", "File Uri: " + selectedVideoUri.toString());
                // Get the path
				try {
					path = getPathNew(this, selectedVideoUri );
					Log.d("Debugging", "File Path: " + path);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myVideoView.setMediaController(new MediaController(this));
				myVideoView.setVideoPath(path);
				myVideoView.requestFocus();
				upload = (Button)findViewById(R.id.select_video_upload_button);
				upload.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new Connection().execute();
//						
					}
				});

              
            }      
        }
    }
  private class Connection extends AsyncTask<Void, Void, Void>
  {

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		try {
			uploadVideo(path);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
  }
  

    private void uploadVideo(String videoPath) throws ParseException, IOException {
    	
    	        HttpClient httpclient = new DefaultHttpClient();
    	        HttpPost httppost = new HttpPost("http://10.0.22.108:8080/ShareServlet/SelectVideoServlet");
    	
    	        FileBody filebodyVideo = new FileBody(new File(videoPath));
    	        StringBody title = new StringBody("Filename: " + videoPath);
    	        StringBody description = new StringBody("This is a description of the video");
    	
    	        MultipartEntity reqEntity = new MultipartEntity();
    	        reqEntity.addPart("videoFile", filebodyVideo);
    	        reqEntity.addPart("title", title);
    	        reqEntity.addPart("description", description);
    	        httppost.setEntity(reqEntity);
    	        Log.e("reqEntity", reqEntity.toString());
    	        // DEBUG
    	        System.out.println( "executing request " + httppost.getRequestLine());
    	        HttpResponse response = httpclient.execute(httppost);
    	        HttpEntity resEntity = response.getEntity();
    	        Log.e("entity", resEntity.toString());
    	        // DEBUG
    	        System.out.println( response.getStatusLine() );
    	        if (resEntity != null) {
    	          System.out.println( EntityUtils.toString(resEntity));
    	          Log.e("notnull", "notnull");
    	        } // end if
    	
    	        if (resEntity != null) {
    	          resEntity.consumeContent();
    	        } // end if
    	        
    	        httpclient.getConnectionManager().shutdown();
    	        Log.e("adfasdfasdfasdfasdfa", "asdfasdfadfasdfasdfasdfasfdas");
    	    } // end of uploadVideo()
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_video, menu);
		return true;
	}

}
