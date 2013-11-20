package com.cmu.weshare;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
	private TextView reg;
	private Button login;
	private EditText  andrewID, password;
	private String andrID, passwd;
	private final String right = "correct username and password";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		andrewID = (EditText)findViewById(R.id.login_andrew_id);
		password = (EditText)findViewById(R.id.login_password);

//		//button to sign in
//		login=(Button)findViewById(R.id.btn_login);
//		login.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v){
//				
//				Intent intent=new Intent(MainActivity.this,SelectCourseActivity.class);
//				startActivity(intent);
//			
//			}
//			
//		});
		//link to create new account
		reg=(TextView)findViewById(R.id.reglabel);
		reg.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//textview with underline
		reg.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				
				Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
				startActivity(intent);
			
			}
			
		});
		
		
	}
	// activity_main.xml contains onclick=readWebpage;	       
		public void readWebpage(View view) {
			     andrID=andrewID.getText().toString();
			     passwd=password.getText().toString();
			     new MyAsyncTask().execute(andrID,passwd);
		}

			   
			    private class MyAsyncTask extends AsyncTask<String, Integer, String>{
			   
			        @Override
			        protected String doInBackground(String... params) {
			            // TODO Auto-generated method stub
			            String s=postData(params);
			            return s;
			        }
			        
			        protected void onPostExecute(String result){
			           Log.i("result", result);
			           if(!result.equals(right)){
			        	   andrewID.setText("");
		                	password.setText("");
		                	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();	
			        	   
			           }
			            
			        }



			        public String postData(String valueIWantToSend[]) {
			            // Create a new HttpClient and Post Header
			            HttpClient httpclient = new DefaultHttpClient();
			            HttpPost httppost = new HttpPost("http://10.0.22.108:8080/ShareServlet/LoginServlet");
			            String origresponseText="";
			            try {
			                // Add your data
			                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			                nameValuePairs.add(new BasicNameValuePair("AndrewId",valueIWantToSend[0]));
			                nameValuePairs.add(new BasicNameValuePair("Password", valueIWantToSend[1]));
			                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			         /* execute */
			                HttpResponse response = httpclient.execute(httppost);
			                HttpEntity rp = response.getEntity();
			                origresponseText=readContent(response);
			          // Log.i("origresponse", origresponseText);
			            } 
			      catch (ClientProtocolException e) {
			                // TODO Auto-generated catch block
			            } 
			      catch (IOException e) {
			                // TODO Auto-generated catch block
			            }
			         
			            return origresponseText;
			        }
			       

			    }
			    String readContent(HttpResponse response)
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
			                   
			            if(text.equals(right)){
			                	
			          			Intent intent=new Intent(MainActivity.this,SelectCourseActivity.class);
			          			startActivity(intent);
			            }   
			             //   Log.i("text", text);
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
