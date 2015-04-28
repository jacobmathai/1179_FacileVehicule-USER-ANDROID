package com.example.facilevechicule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BusActivity extends Activity implements OnClickListener{

	AutoCompleteTextView edt;
	TextView tv;
	Button b;
	GPSTracker gps;
//	Button bbus,btaxi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus);
		edt=(AutoCompleteTextView) findViewById(R.id.autoComplete_TextView1);
		tv=(TextView)findViewById(R.id.current_place);
		b=(Button)findViewById(R.id.button3);
//		bbus=(Button)findViewById(R.id.butbus);
//        btaxi=(Button)findViewById(R.id.buttxi);
		b.setOnClickListener(this);
	    gps=new GPSTracker(BusActivity.this);
		double lat=gps.getLatitude();
		double lon=gps.getLongitude();
		Geocoder geocoder = new Geocoder(BusActivity.this, Locale.getDefault());
        String result = null;
        try
        {
            List<Address> addressList = geocoder.getFromLocation(
                    lat, lon, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                String location=address.getLocality();
                tv.setText(""+location);
            }
        }
        catch (Exception e)
        {
        	
        }
		for_destination fd=new for_destination();
		fd.execute();
		
	}
	@Override
	public void onClick(View v) {
		startActivity(new Intent(getBaseContext(), BusdeailsActivity.class));
		
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
		String res="";
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			NAMESPACE = "http://facile/";
			URL ="http://10.0.2.2:8080/mainproject/facile_serviceService";
			METHOD_NAME = "getDestination";
			SOAP_ACTION = "http://facile/getDestination";
		
			destination= new ArrayList<String>();
			
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
	   				
	       			request.addProperty("c_location", "Vazhakulam");
	       			
	       			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	       			//envelope.dotNet = true;
	                envelope.setOutputSoapObject(request);
	                
	                Log.d("Exception Bus-----------", "hiq");
	                
	                androidHttpTransport.debug = true;
	                
	                Log.d("Exception Bus -----------", "hiw");
	                
					androidHttpTransport.call(SOAP_ACTION,envelope);
					
					Log.d("count-------------", "working");
			        
					SoapPrimitive so1 = (SoapPrimitive) envelope.getResponse(); 

			        
//	                i=so.getPropertyCount();
	                
//	                for(int j=0;j<i;j++)
//	                {
//	                	destination.add(so.getProperty(j).toString());
//	                }
			        res=so1.toString();
	            
	        }
	        catch(Exception e)
	        {
	        	Log.d("Exception in main try", e.toString());
	        }

			return res;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "First -- "+result, Toast.LENGTH_LONG).show();
			String array[]=result.split("/");
			
			Toast.makeText(getApplicationContext(), "Length-- "+array.length, Toast.LENGTH_LONG).show();
			ArrayAdapter<String> adp=new ArrayAdapter<String>(BusActivity.this,
	        		android.R.layout.simple_dropdown_item_1line,array);
	        
	        edt.setThreshold(1);
	        edt.setAdapter(adp);
			Toast.makeText(getApplicationContext(), "Count -- "+result, Toast.LENGTH_LONG).show();
			
		}
		
	}
}


