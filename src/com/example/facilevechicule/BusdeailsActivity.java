package com.example.facilevechicule;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BusdeailsActivity extends Activity {

	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busdeails);
		lv= (ListView) findViewById(R.id.bus_listView);
		
		for_bus_details fbd=new for_bus_details();
		fbd.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.busdeails, menu);
		return true;
	}
	
	class for_bus_details extends AsyncTask<String, String, String>
	{
	
		SoapObject so;
		SoapPrimitive so1=null;
		int i=0;
		String response="";
		ArrayList<String> name,time,start;
		String NAMESPACE; 
		String URL;
		String METHOD_NAME;
		String SOAP_ACTION;
		ArrayList<String> bus_details;
		String res="";
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			NAMESPACE = "http://facile/";
			URL ="http://10.0.2.2:8080/mainproject/facile_serviceService";
			METHOD_NAME = "bus_display_details";
			SOAP_ACTION = "http://facile/bus_display_details";
		
			bus_details= new ArrayList<String>();
			name=new ArrayList<String>();
			time=new ArrayList<String>();
			start=new ArrayList<String>();
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
	       			request.addProperty("destination", "Thodupuzha");
	       			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	       			//envelope.dotNet = true;
	                envelope.setOutputSoapObject(request);
	                
	                Log.d("Exception Bus-----------", "hiq");
	                
	                androidHttpTransport.debug = true;
	                
	                Log.d("Exception Bus -----------", "hiw");
	                
					androidHttpTransport.call(SOAP_ACTION,envelope);
					
					Log.d("count-------------", "working");
			        
					so = (SoapObject) envelope.bodyIn; 

					Log.d("count-------------", "working");
			        
					
//	                i=so.getPropertyCount();
	                
//	                for(int j=0;j<i;j++)
//	                {
//	                	destination.add(so.getProperty(j).toString());
//	                }
//			        res=so1.toString();
					Log.d("soap data in background", so.toString()+"");
					
	        }
	        catch(Exception e)
	        {
	        	Log.d("Exception in main try", e.toString());
	        }

			return so.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			

			
			Log.d("after", result+"");
			String after=result.substring(28,result.length()-1);
			Log.d("after2", after+"");
			 String array[]=after.split(";");
				for(int i=0;i<array.length-1;i++){
//			
//					
//					SoapPrimitive name1=(SoapPrimitive)result.getProperty(i);
////					SoapPrimitive time1=(SoapPrimitive)so.getProperty(1);
////					SoapPrimitive start1=(SoapPrimitive)so.getProperty(2);
//					String array[]=name1.toString().split("/");
//					
//						name.add(array[0].toString());
//						time.add(array[1].toString());
//						start.add(array[2].toString());
//			
					String more[]=array[i].split("=");
					Log.d("soap data", more[1]+"");
					String more1[]=more[1].split("/");
					name.add(more1[0].toString());
					time.add(more1[1].toString());
					start.add(more1[2].toString());
				}
            
				
//				Toast.makeText(getApplicationContext(), "First -- "+name.get(2), Toast.LENGTH_LONG).show();
				
//				ArrayAdapter<String> aa = new ArrayAdapter<String>(BusdeailsActivity.this,android.R.layout.simple_list_item_1,name);
		        lv.setAdapter(new bus_list_adapter(getApplicationContext(), name, time));
			
				
		}
		
	}
	
	
	class bus_list_adapter extends ArrayAdapter<String>{

		List<String> names,times;
		public bus_list_adapter(Context context, List<String> objects,List<String> time) {
			super(context, R.layout.single_bus, objects);
			names=objects;
			times=time;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view=inflater.inflate(R.layout.single_bus, null);
					TextView tx1=(TextView) view.findViewById(R.id.bus_name);
					TextView tx2=(TextView) view.findViewById(R.id.bus_time);
						tx1.setText(names.get(position));
						tx2.setText(times.get(position));
			return view;
		}
		
	}

}
