package com.example.androidreadcallhistory;

import java.sql.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView textView = null;
	Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mHandler = new Handler();

		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textview_call);
		//getCallDetails();
		m_Runnable.run();


	}


	private final Runnable m_Runnable = new Runnable()
	{
		public void run()

		{

			getCallDetails();
			Toast.makeText(MainActivity.this,"Tracker is running", Toast.LENGTH_SHORT).show();
			MainActivity.this.mHandler.postDelayed(m_Runnable,10000);
		}

	};




	private void getCallDetails() {
		StringBuffer sb = new StringBuffer();
		String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
		/* Query the CallLog Content Provider */
		Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
				null, null, strOrder);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		sb.append("Call Log :");
		while (managedCursor.moveToNext()) {
			String phNum = managedCursor.getString(number);
			String callTypeCode = managedCursor.getString(type);
			String strcallDate = managedCursor.getString(date);
			Date callDate = new Date(Long.valueOf(strcallDate));
			String callDuration = managedCursor.getString(duration);
			String callType = null;
			int callcode = Integer.parseInt(callTypeCode);

			if (callcode == CallLog.Calls.MISSED_TYPE) {
				//incoming call
				callType = " Missed call";
			}
//
//
//

			if (callcode == CallLog.Calls.MISSED_TYPE) {
				sb.append("\nPhone Number:--- " + phNum + " \nCall Type:--- "
						+ callType + " \nCall Date:--- " + callDate
						+ " \nCall duration in sec :--- " + callDuration);
				sb.append("\n----------------------------------");



				String method = "register";
				Databaseconn databaseconn = new Databaseconn(this);
				databaseconn.execute(method, phNum);
			//	finish();


			}




		}
		//managedCursor.close();

		textView.setText(sb);
	}
}
