package com.example.facilevechicule;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParsePush;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TaxidetailsActivity extends Activity {

	TextView tx1,tx2,tx3;
	Button btn;
	SharedPreferences shed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_taxidetails);
		shed=getSharedPreferences("user", Context.MODE_PRIVATE);
		tx1=(TextView) findViewById(R.id.text_view1);
		tx2=(TextView) findViewById(R.id.text_View3);
		tx3=(TextView) findViewById(R.id.text_View4);
		btn=(Button) findViewById(R.id.button_book);
		
		Bundle b=getIntent().getExtras();
			String contact_name=b.getString("taxi_name");
			String taxi_no=b.getString("taxi_no");
			String taxi_cno=b.getString("taxi_contactno");
			
			tx1.setText("Taxi Number -- "+taxi_no);
			tx2.setText("Driver Name -- "+contact_name);
			tx3.setText("Contact Number -- "+taxi_cno);
		
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						JSONObject jsondata=new JSONObject("{\"name\":\"yadhu\"}");

						ParsePush push=new ParsePush();
							push.setChannel("taxi"+tx1.getText().toString());
							push.setData(jsondata);
							push.setMessage("Taxi needed "+shed.getString("phone", "")+" ");
							push.sendInBackground();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					Toast.makeText(getApplicationContext(), "Booked", Toast.LENGTH_LONG).show();
					
				}
			});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.taxidetails, menu);
		return true;
	}

}

