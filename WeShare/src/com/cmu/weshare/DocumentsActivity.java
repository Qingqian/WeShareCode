package com.cmu.weshare;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class DocumentsActivity extends Activity {
	private static final String TAG = "CallText";
	File file;
    FileReader rdr;
    EditText writeComment;
    String comment;
    Button verify;
    FileWriter fWriter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_documents);
		//button to write files into device
		verify = (Button)findViewById(R.id.verify_comment);
		verify.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				createFile();
//				Log.e(TAG, "********************************************");
		        String file_contents = readFile();	

		        TextView tv = (TextView)findViewById(R.id.view_comment);
		        //get content from EditText
		        tv.setText(file_contents);
				new Connection().execute(file_contents);


			}
		});
		
        
    }
	private class Connection extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0]);
			return null;
		}
		public void postData(String params)
		{
			
	        try
	        {
	        
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://10.0.22.108:8080/ShareServlet/UploadDocumentServlet");
		        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	             nameValuePairs.add(new BasicNameValuePair("document",params));
//	             Log.e("afdadfadfadfasdfa", params);
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
//	            is = entity.getContent();
//	            Log.v("finished", is.toString());
	        }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	        }		
		}
	}
	//create file and write comments into it 
    protected void createFile(){
        try{
        	file = this.getOutputTextFile();
            fWriter = new FileWriter(file);
            writeComment = (EditText)findViewById(R.id.comment_content);
            comment = writeComment.getText().toString();
             fWriter.write(comment);
             fWriter.flush();
             fWriter.close();
         }catch(Exception e){
                  e.printStackTrace();
         }
    }
    //read file
    protected String readFile(){
        char buf[] = new char[512];
        FileReader rdr;
        String contents = "";
        try {
            rdr = new FileReader(file);
            int s = rdr.read(buf);
            for(int k = 0; k < s; k++){
                contents+=buf[k];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.documents, menu);
		return true;
	}
	//create file and save to device
	private File getOutputTextFile() {
		  File directory = new File(Environment.getExternalStoragePublicDirectory(
		                Environment.DIRECTORY_DOWNLOADS), getPackageName());
		  if (!directory.exists()) {
		    if (!directory.mkdirs()) {
		      Log.e(TAG, "Failed to create storage directory.");
		      return null;
		    }
		  }
		  String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
		  return new File(directory.getPath() + File.separator + "TEXT_"  
		                    + timeStamp + ".txt");
		}

}
