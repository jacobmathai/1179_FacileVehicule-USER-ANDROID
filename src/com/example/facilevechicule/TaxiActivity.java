package com.example.facilevechicule;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class TaxiActivity extends Activity implements OnItemClickListener{
	
	
	
	
	ArrayList<String> tname,tphno,tcno;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_taxi);
        lv = (ListView) findViewById(R.id.taxi_listView);
		tname=new ArrayList<String>();
		tphno=new ArrayList<String>();
		tcno=new ArrayList<String>();

        
        taxi_list_Task tlt=new taxi_list_Task();
        tlt.execute();
        
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(TaxiActivity.this, TaxidetailsActivity.class);
					intent.putExtra("taxi_no", tphno.get(arg2));
					intent.putExtra("taxi_contactno", tcno.get(arg2));
					intent.putExtra("taxi_name", tname.get(arg2));
				startActivity(intent);
				
			}
		});
      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.taxi, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	
	

	}

	
	private class taxi_list_Task extends AsyncTask<String, Void, String> {
		// ProgressDialog progress;

		String NAMESPACE; 
		String URL;
		String METHOD_NAME;
		String SOAP_ACTION;
		
		SoapObject so;
		
		public void onPreExecute() {

			super.onPreExecute();
			
			NAMESPACE = "http://facile/";
			URL ="http://10.0.2.2:8080/mainproject/facile_serviceService";
			METHOD_NAME = "near_taxi_list";
			SOAP_ACTION = "http://facile/near_taxi_list";
			
		
		}

		@Override
		protected String doInBackground(String... urls) {
			
			try{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
   			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				
   			request.addProperty("c_locaation", "Muvattupuzha");
   			
   			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
   			//envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            
            Log.d("Exception Bus-----------", "hiq");
            
            androidHttpTransport.debug = true;
            
            Log.d("Exception Bus -----------", "hiw");
            
			androidHttpTransport.call(SOAP_ACTION,envelope);
			
			Log.d("count-------------", "working");
	        
			so = (SoapObject) envelope.bodyIn;
			
			}
			catch(Exception e){
				
				Log.d("Exception in backgroud ", e.toString());
				
			}
			
			return so.toString();
		}
		
		
    @Override
		protected void onPostExecute(String result) {
    		
    		Log.d("Result---", ""+result);
    		
    		String after=result.substring(23,result.length()-1);
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
					tname.add(more1[0].toString());
					tphno.add(more1[1].toString());
					tcno.add(more1[2].toString());
				}
				
			lv.setAdapter(new taxi_list_adapter(getApplicationContext(), tname, tcno));
		}
	}

	class taxi_list_adapter extends ArrayAdapter<String>{

		List<String> names,cno;
		public taxi_list_adapter(Context context, List<String> objects,List<String> time) {
			super(context, R.layout.single_bus, objects);
			names=objects;
			cno=time;
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
						tx2.setText(cno.get(position));
					view.setId(position);
			return view;
		}
		
	}

}
	

