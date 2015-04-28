//package com.example.facilevechicule;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.ksoap2.serialization.SoapObject;
//
//
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.app.Activity;
//import android.content.Intent;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class LoginActivity extends Activity {
//
//	
//	
//	Button blogin;
//	EditText edtusername,edtpassword;
//	String usrname,pasword;
//	Ksoap soap;
//
//	final String NAMESPACE = "http://adminpack/";
//	final String URL = "http://192.168.1.104:8080/onlineforest/getdatabase";
//	final String METHOD_NAME = "getsession";
//	final String SOAP_ACTION = "http://adminpack/getsession";
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_login);
//		
//		edtusername=(EditText)findViewById(R.id.edtusername);
//		edtpassword=(EditText)findViewById(R.id.edtpass);
//		
//		blogin=(Button)findViewById(R.id.butlogin);
//		
//		blogin.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				usrname=edtusername.getText().toString();
//				pasword=edtpassword.getText().toString();
//				if(usrname.equals("")||pasword.equals(" "))
//				{
//					
//				Toast.makeText(getBaseContext(), "Invalid data", 10).show();	
//				}
//				else
//				{
//					
//					
//					 soap=new Ksoap();
//					 Toast.makeText(getApplicationContext(), "create:-", Toast.LENGTH_LONG).show();
//					 new SoapAccessTask().execute();
//					
//					
//				}
//				
//				
//			}
//		});
//		
//		
//	}
//
//	private class SoapAccessTask extends AsyncTask<String, Void, String> {
//		// ProgressDialog progress;
//
//		public void onPreExecute() {
//
//			super.onPreExecute();
//		
//		}
//
//		@Override
//		protected String doInBackground(String... urls) {
//			
//			String response = "";	
//			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//	
//			
//			
//			request.addProperty("username",usrname);
//			request.addProperty("password",pasword);
//			
//			
//			
//
//			String result=soap.getResponseData(URL,SOAP_ACTION,request);	
//			//Toast.makeText(secondActivity.this,""+result, Toast.LENGTH_LONG).show();
//			   try 
//			   {
//				response = result.toString();
//				//tw.setText(response);
//				Log.d("login", ""+response);
//			   }
//			   catch (Exception e)
//			   {
//                response = "" + e;
//               // tw.setText("e:"+e);
//			   }
//				
//			return response;
//		}
//		
//		
//    @Override
//		protected void onPostExecute(String result) {
//			// if you started progress dialog dismiss it her	
//			Toast.makeText(getBaseContext(),""+result, Toast.LENGTH_LONG).show();
//			
//			
//	       if(result.equalsIgnoreCase("true"))
//	        {
//	    	   LoginActivity.this.finish();
//	    	   startActivity(new Intent(getBaseContext(), FirstActivity.class));
//	    	   
//		
//	        }
//	       else
//	       {
//	    	   
//	    	   Toast.makeText(getBaseContext(),"check Your password and Username", Toast.LENGTH_LONG).show();
//	      
//	       }
//			
////		if(result.contentEquals("[]"))
////		{
//////		
////			Toast.makeText(getBaseContext(),""+result, Toast.LENGTH_LONG).show();
////		}
////		else
////		{
////			try {
////				JSONArray response = new JSONArray(result.toString());
//////				int n = response.length();
//////				Toast.makeText(getBaseContext(),""+n, Toast.LENGTH_LONG).show();
//////				Division = new String[n];
//////				for (int i = 0; i < n; i++)
//////				{
//////					HashMap<String,String> hashtemp = new HashMap<String,String>();
//////					JSONObject object = new JSONObject(response.get(i).toString());
//////					hashtemp.put("AccountDescription", "" + object.getString("AccountDescription"));
//////					hashtemp.put("Credit", object.getString("Credit"));
//////					hashtemp.put("FromDate", "" + object.getString("FromDate"));
//////					hashtemp.put("VoucherNo", object.getString("VoucherNo"));					
//////					Log.d("data", ""+object);
//////						
//////					listfordivision.add(hashtemp);
//////				}
//////				Toast.makeText(getBaseContext(),""+listfordivision, Toast.LENGTH_LONG).show();
//////				ArrayAdapter<HashMap<String, String>> are=new ArrayAdapter<HashMap<String,String>>(getBaseContext(), android.R.layout.simple_list_item_1,listfordivision);
//////				lw.setAdapter(are);
////			} catch (JSONException e1) {
////				// TODO Auto-generated catch block
////				e1.printStackTrace();
////				//tw.setText("e2:"+e1);
////			}
//					
////	    Toast.makeText(getBaseContext(),""+result, Toast.LENGTH_LONG).show();	
//		}
//	}
//	
//
//}
