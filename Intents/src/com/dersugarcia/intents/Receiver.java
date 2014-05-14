package com.dersugarcia.intents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

	private static final String AIRPLANE_MODE = "android.intent.action.AIRPLANE_MODE";
	private static final String PHONE_STATE = "android.intent.action.PHONE_STATE";
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	private static final String CONN_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();
		Log.d("INTENTS", action);
		
		if (action.equals(AIRPLANE_MODE)) {
			airplaneModeChange(context);
		} else if (action.equals(PHONE_STATE)) {
			phoneStateChange(context, intent);
		} else if (action.equals(SMS_RECEIVED)) {
			smsReceived(context,intent);
		} else if (action.equals(CONN_CHANGE)) {
			connectivityChange(context,intent);
		}
	}
	
	private void airplaneModeChange(Context context) {
		if(isAirplaneModeOn(context)) {
			Toast.makeText(context, "AIRPLANE_MODE ENABLED", Toast.LENGTH_LONG).show();
		}
	}
	
	private void phoneStateChange(Context context, Intent intent) {
		if( intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING) ){
			Log.d("INTENTS", "Phone ringing from " + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
		}
	}
	
	private void connectivityChange(Context context, Intent intent) {
		if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
			Toast.makeText(context, "WARNING: THERE IS NO CONNECTIVITY", Toast.LENGTH_LONG).show();
			Log.d("INTENTS", "WARNING: THERE IS NO CONNECTIVITY");
		}
		
	}
	
	private void smsReceived(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		try {
			if (bundle != null) {
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
				Log.d("INTENTS", "Message body: " + currentMessage.getDisplayMessageBody());
			}
		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);
		}
	}

	private static boolean isAirplaneModeOn(Context context) {

		   return Settings.System.getInt(context.getContentResolver(),
				   Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

		}

}
