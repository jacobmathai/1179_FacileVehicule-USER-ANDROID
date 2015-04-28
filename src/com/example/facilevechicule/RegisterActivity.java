package com.example.facilevechicule;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{

		SharedPreferences shed;
		SharedPreferences.Editor editor;
		Button bt;
		EditText edt,edt1,edt2;
		Ksoap sop;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_register);
			
			shed=getSharedPreferences("user", Context.MODE_PRIVATE);
			editor=shed.edit();
			bt=(Button) findViewById(R.id.register_submit);
			edt=(EditText) findViewById(R.id.u_name);
			edt1=(EditText) findViewById(R.id.u_email);
			edt2=(EditText) findViewById(R.id.u_phone);
			
			sop=new Ksoap();
			
			
			
			if(shed.getString("user_id", "").equals("")){
				
			}
			else{
				Intent intent=new Intent(RegisterActivity.this, HomeActivity.class);
					startActivity(intent);
					finish();
			}
			bt.setOnClickListener(this);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.register, menu);
			return true;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v==bt)
			{
			if(edt.equals("") || edt1.equals("") || edt2.equals("")){
			
				Toast.makeText(RegisterActivity.this, "Fill all fields", Toast.LENGTH_LONG).show();
			}
			else
			{
				ParseUser user=new ParseUser();
				user.setUsername(edt.getText().toString());
				user.setPassword(edt2.getText().toString());
				
			user.signUpInBackground(new SignUpCallback() {
				
				@Override
				public void done(ParseException e) {
					// TODO Auto-generated method stub
					editor.putString("phone",edt2.getText().toString());
					editor.commit();
					PushService.subscribe(RegisterActivity.this, "user"+edt2.getText().toString(), NotificationActivity.class);
					Toast.makeText(getApplicationContext(), "hiiiiiiiiii", Toast.LENGTH_LONG).show();
				}
			});
				for_destination fd=new for_destination();
				fd.execute();
				
			}
			
			}
			
		}
		
		
		class for_destination extends AsyncTask<String, String, String>
		{
		
			SoapObject so=null;
			SoapPrimitive so1=null;
			int i=0;
			String response="";
			
			String NAMESPACE; 
			String URL;
			String METHOD_NAME;
			String SOAP_ACTION;
			ArrayList<String> destination;
			String resp="";
			String result=null;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				
				NAMESPACE = "http://facile/";
				URL ="http://10.0.2.2:8080/mainproject/facile_serviceService";
				METHOD_NAME = "user_registration";
				SOAP_ACTION = "http://facile/user_registration";
			
				super.onPreExecute();
				
			}
			

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				 
			
		        try
		        {
		        		Log.d("nameeee",URL);
		        		
		        		
		        		
		        		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		       			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		       			
		       			
		       			request.addProperty("u_name",edt.getText().toString());
		       			request.addProperty("u_email_id",edt1.getText().toString());
		       			request.addProperty("u_phone",edt2.getText().toString());
		       			result=sop.getResponseData(URL,SOAP_ACTION,request);
		       			
//		       			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
////		       			//envelope.dotNet = true;
//		                envelope.setOutputSoapObject(request);
		                Log.d("Exception-----------", "hiqa");
//		                androidHttpTransport.debug = true;
		                Log.d("Exception-----------", "hiwa");
//						androidHttpTransport.call(SOAP_ACTION,envelope);
////						
//				        SoapPrimitive so1 = (SoapPrimitive)envelope.getResponse(); 
////				        Log.d("after so", "gettingit");
//	//
//		       			return result;
		       			
		                
		            
		        }
		        catch(Exception e)
		        {
		        	Log.d("Exception-----------", ""+e.toString());
		        }
		        Log.d("Exception-----------", "hiasdas");
				return result;
			}
			
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				Log.d("Exception-----------", "result");
				Toast.makeText(RegisterActivity.this, ""+result, Toast.LENGTH_LONG).show();
				if(result.equals("failed"))
					Toast.makeText(RegisterActivity.this, "failed "+result, Toast.LENGTH_LONG).show();
				else
				{
					Toast.makeText(RegisterActivity.this, "Successfully registered"+result, Toast.LENGTH_LONG).show();
					editor.putString("user_id", result);
					editor.commit();
					startActivity(new Intent(getBaseContext(), HomeActivity.class));
					finish();
				}
			}
			
		}


		
		
	}
