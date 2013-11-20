package com.cmu.weshare;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
public class ImageActivity extends Activity {
	String test = "hello";
	String photo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		Button send = (Button)findViewById(R.id.button1);
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Connection().execute(test);
				byte[] bytearray = Base64.decode(photo, Base64.DEFAULT);
				ImageView image = (ImageView)findViewById(R.id.imageView1);
				image.setImageBitmap(BitmapFactory.decodeByteArray(bytearray,0,bytearray.length));

			}
		});
	}
    private class Connection extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			photo =postData(params[0]);
			return photo;
		}

		
    	
    }
    public String postData(String params)
	{
        String origresponseText="";
        try
        {
        
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.22.108:8080/ShareServlet/DownloadPhotoServlet");
	        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("test",test));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            origresponseText=readContent(response);
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
        }
        return origresponseText;

	}

    //menu to select items
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        //item to select photo
        case R.id.selectphotoLabel:
        startActivity(new Intent(this, SelectPhotoActivity.class));
        return true;
        //item to take photo
        case R.id.takephotoLabel:
        startActivity(new Intent(this, TakePhotoActivity.class));
        return true;
        default:
        return super.onOptionsItemSelected(item);
        }
    }
	protected String readContent(HttpResponse response)
    {
        String text = "";
        InputStream in =null;
         
        try {
            in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
    
                  sb.append(line);
                }
            text = sb.toString();
        } catch (IllegalStateException e) {
            e.printStackTrace();
           
        } catch (IOException e) {
              e.printStackTrace();
        }
        finally {
            try {

              in.close();
            } catch (Exception ex) {
            }
            }
        Log.i("res", text);

return text;
    }
}

