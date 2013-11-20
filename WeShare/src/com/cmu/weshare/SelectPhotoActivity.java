package com.cmu.weshare;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.io.*;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
public class SelectPhotoActivity extends Activity{
    InputStream is;
	 private static int RESULT_LOAD_IMAGE = 1;
	 Bitmap bitmapOrg;
	 byte [] ba;
	 byte [] test;
	 String string = "hello";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_select_photo);
		Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                 
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }
     
     
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			bitmapOrg = BitmapFactory.decodeFile(picturePath);

            Button upload_image = (Button) findViewById(R.id.upload_selected_image_button);
            upload_image.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
			        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			        ba = bao.toByteArray();	
					new Connection().execute(ba);
				}
			});
        }
    }
    private class Connection extends AsyncTask<byte[], Integer, String>{
//
    	@Override
		protected String doInBackground(byte[]... params) {
			// TODO Auto-generated method stub
    		postData(params[0]);
			return null;
		}
		public void postData(byte[] params)
		{
			
	        try
	        {
	        
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://10.0.22.108:8080/ShareServlet/SelectPhotoServlet");
		        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	             nameValuePairs.add(new BasicNameValuePair("image",Base64.encodeToString(ba, Base64.DEFAULT)));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	            Log.v("finished", is.toString());
	        }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	        }		
		}
		

		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.select_photo, menu);
		return true;	}

	

}
