package com.cmu.weshare;
import java.io.*;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import com.cmu.weshare.R;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class RegisterActivity extends Activity{
	private Button register;
	EditText andrew_id, password,confirm_password;
	String str = "user name existed";
	private final HttpClient httpclient = new DefaultHttpClient();
    Context c;
	String reEnterPwd="password doesn't match, please enter it again";

    final HttpParams params = httpclient.getParams();
    HttpResponse response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		andrew_id = (EditText)findViewById(R.id.register_andrew_id);
		password = (EditText)findViewById(R.id.register_password);
		confirm_password = (EditText)findViewById(R.id.password_confirm);
		//button to register
		register=(Button)findViewById(R.id.register_button);
        c=this;
	}
	public void readWebpage(View view) {
		final String andrew_id_string=andrew_id.getText().toString();
		final String password_string=password.getText().toString();
		final String confirm_password_string=confirm_password.getText().toString();	
		if(confirm_password_string.equals(password_string))
		{
			new Connection().execute(andrew_id_string,password_string);
		}
		else
		{
			password.setText("");
			confirm_password.setText("");
			Toast.makeText(getApplicationContext(),reEnterPwd,Toast.LENGTH_LONG).show();
		}
		
		}
        private class Connection extends AsyncTask<String, Integer, String>{
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String s=postData(params);
		           return s;
			}
			protected void onPostExecute(String result){
				if(result.equals(str)){
		        	   andrew_id.setText("");
		        	   
		        	    confirm_password.setText("");
	                	password.setText("");
	                	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();	
		        	   
				}		
				          
	       }
	       protected void onProgressUpdate(Integer... progress){
	       }
        	 
     public String postData(String valueIWantToSend[])
        {
        	DefaultHttpClient httpcliet=new DefaultHttpClient();
			HttpPost httppost=new HttpPost("http://10.0.22.108:8080/ShareServlet/RegisterServlet");
            String origresponseText="";
        	try{
        		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
				nameValuePairs.add(new BasicNameValuePair("andrew_id", valueIWantToSend[0])); 
				nameValuePairs.add(new BasicNameValuePair("password", valueIWantToSend[1])); 

    		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
                HttpEntity rp = response.getEntity();
                origresponseText=readContent(response);
        		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
                    
                    if(!text.equals(str)){
	                	
	          			Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
	          			startActivity(intent);
	            }   
                    Log.v("Text$$$$$", text);

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

     return text;
        }

		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
