package cic.myapps.signin.dev;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;



public class GPlusActivity extends Activity implements ConnectionCallbacks, 
	OnConnectionFailedListener {
	
	private static final String TAG = "GPlusActivity";
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	
	private ProgressDialog mConnectionProgressDialog;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPlusClient = new PlusClient.Builder(this, this, this)
		        .setVisibleActivities("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
		        .build();
		// Progress bar to be displayed if the connection failure is not resolved.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");
	}
	
	/**
	 * When the G+ btn is clicked, or when this Activity is resumed
	 */
	private void startConnecting() {		
		if (!mPlusClient.isConnected()) {
	        if (mConnectionResult == null) {
	            mConnectionProgressDialog.show();
	        } else {
	            try {
	                mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
	            } catch (SendIntentException e) {
	                // Try connecting again.
	                mConnectionResult = null;
	                mPlusClient.connect();
	            }
	        }
	    }
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mPlusClient.connect();
		
		startConnecting();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mPlusClient.disconnect();
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mConnectionProgressDialog.isShowing()) {
			// The user clicked the sign-in/G+ button already. Start to resolve
			// connection errors. Wait until onConnected() to dismiss the
			// connection dialog.
			if (result.hasResolution()) {
			  try {
			           result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
			   } catch (SendIntentException e) {
			           mPlusClient.connect();
			   }
			}
		}
		// Save the result and resolve the connection failure upon a user click.
		mConnectionResult = result;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK) {
		    mConnectionResult = null;
		    mPlusClient.connect();
		}
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {
		String accountName = mPlusClient.getAccountName();
		Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_LONG).show();
		mConnectionProgressDialog.dismiss();
	}
	
	@Override
	public void onDisconnected() {
		Log.d(TAG, "disconnected");
	}

}