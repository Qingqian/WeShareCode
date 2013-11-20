package com.cmu.weshare;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
public class TakePhotoActivity extends FragmentActivity
{
	private static final String TAG = "CallCamera";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;
	Uri fileUri = null;
	ImageView photoImage = null;
	File file;
	GoogleMap map;
	LocationManager lm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_camera);
		photoImage = (ImageView) findViewById(R.id.photo_taken);
        //button to take photo
	    Button callCameraButton = (Button) findViewById(R.id.button_callcamera);
	    callCameraButton.setOnClickListener(new View.OnClickListener() {
	      public void onClick(View view) {
	        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        file = getOutputPhotoFile();
//	        Log.e(TAG, "&***********************************************");
	        fileUri = Uri.fromFile(getOutputPhotoFile());
	        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	        startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ );
	      }
	    });
	    lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
	            .findFragmentById(R.id.map);
	    map = mapFragment.getMap();

	    map.setMyLocationEnabled(true);

	    LocationListener ll = new LocationListener() {

	        @Override
	        public void onStatusChanged(String provider, int status,
	                Bundle extras) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onProviderEnabled(String provider) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onProviderDisabled(String provider) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onLocationChanged(Location location) {

//	            Toast.makeText(getApplicationContext(),
//	                    location.getLatitude() + " " + location.getLongitude(),
//	                    Toast.LENGTH_LONG).show();

	            map.addMarker(new MarkerOptions()
	                    .position(
	                            new LatLng(round(location.getLatitude(),2), round(location.getLongitude(),2)))
	                            .title("my position")
	                    .icon(BitmapDescriptorFactory
	                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

	            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(round(
	                    location.getLatitude(),2), round(location.getLongitude(),2)), 15.0f));

	        }
	    };
	    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_camera, menu);
		return true;
	}
	//create file in the device
	private File getOutputPhotoFile() {
		  File directory = new File(Environment.getExternalStoragePublicDirectory(
		                Environment.DIRECTORY_PICTURES), getPackageName());
		  if (!directory.exists()) {
		    if (!directory.mkdirs()) {
		      Log.e(TAG, "Failed to create storage directory.");
		      return null;
		    }
		  }
		  String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
		  return new File(directory.getPath() + File.separator + "IMG_"  
		                    + timeStamp + ".jpg");
		}
	//display the action after press the "Take Photo" button
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
		    if (resultCode == RESULT_OK) {
		      Uri photoUri = null;
		      if (data == null) {
		        // A known bug here! The image should have saved in fileUri
		        Toast.makeText(this, "Image saved successfully", 
		                       Toast.LENGTH_LONG).show();
		        photoUri = fileUri;

		      } else {
		        photoUri = data.getData();
		        Toast.makeText(this, "Image saved successfully in: " + data.getData(), 
		                       Toast.LENGTH_LONG).show();

		      }
		       showPhoto(photoUri);

		    } else if (resultCode == RESULT_CANCELED) {
		      Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
		    } else {
		      Toast.makeText(this, "Callout for image capture failed!", 
		                     Toast.LENGTH_LONG).show();
		    }
		  }
		}
	//show photo if already taken
	protected void showPhoto(Uri photoUri)
	{
		
		File imageFile = new File(photoUri.getPath());
		  if (imageFile.exists()){
		     Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		     BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
		     photoImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
		     photoImage.setImageDrawable(drawable);
		     photoImage.getDrawable();
		  }     
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	}
}
