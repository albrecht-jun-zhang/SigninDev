package cic.myapps.signin.dev;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.view.View;

public class SigninMainActivity extends Activity implements View.OnClickListener {

	private static final String TAG = "MainActivity";
	private static final String URL = "http://junzone.com/loginpost/UserInfoPost.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_main);		
		// Hide keyboard initially
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);				
//		retrieveName();
		
		// Google+ btn is clicked
		findViewById(R.id.imgBtnGoogle).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signin_main, menu);
		return true;
	}
	
	/**
	 * NOTE: not in use
	 * Retrieve given and family name from junzone.com's db
	 */
//	private void retrieveName() {
//		// JSON object to hold the information, which is sent to the server
//			JSONObject jsonObjSend = new JSONObject();
//
//			try {
//				// Add key/value pairs
//				jsonObjSend.put("key_1", "value_1");
//				jsonObjSend.put("key_2", "value_2");
//
//				// Add a nested JSONObject (e.g. for header information)
//				JSONObject header = new JSONObject();
//				header.put("deviceType","Android"); // Device type
//				header.put("deviceVersion","2.0"); // Device OS version
//				header.put("language", "es-es");	// Language of the Android client
//				jsonObjSend.put("header", header);
//				
//				// Output the JSON object we're sending to Logcat:
//				Log.i(TAG, jsonObjSend.toString(2));
//
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//
//			// Send the HttpPostRequest and receive a JSONObject in return
//			HttpClient httpClient = new HttpClient(this);
//			String[] urls = new String[1];
//			urls[0] = URL;
//			
//			httpClient.execute(urls);	
//	}

	/**
	 * Click buttons on the main page, i.e. facebook, twitter, g+ btn, etc
	 */
	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.imgBtnGoogle:
			Intent intent;
			intent = new Intent(this, GPlusActivity.class);
			startActivity(intent);
			break;
		}		
	}

}
