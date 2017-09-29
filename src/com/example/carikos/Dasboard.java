package com.example.carikos;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException; 
import com.example.carikos.Daftar.RegisterTask;

import android.os.AsyncTask;

import android.os.Bundle;

import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Intent;

import android.view.Menu;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.EditText;

import android.widget.Toast;


public class Dasboard extends Activity {
	
	JSONArray product = null;
	String [] tampilData = new String[11];
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	EditText username;

	EditText password;

	EditText nama;

	EditText telepon;

	EditText alamat;

	EditText jmlKamar;

	EditText harga;

	EditText lattitude;

	EditText longitude;
	
	RadioGroup status;
	
	RadioGroup jenis;
	
	RadioButton kosong;
	RadioButton penuh;
	RadioButton laki;
	RadioButton perempuan;
	
	String tempUsername,tempPass;

    private ProgressDialog pDialog;

    String hasil;

    GpsService	gps;
	

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.dasboard);

		username = (EditText)findViewById(R.id.txtUsername);

		password = (EditText)findViewById(R.id.txtPassword);

		nama = (EditText)findViewById(R.id.txtNama);

		telepon = (EditText)findViewById(R.id.txtTelp);

		alamat = (EditText)findViewById(R.id.txtAlamat);

		jmlKamar = (EditText)findViewById(R.id.txtJmlKamar);

		harga = (EditText)findViewById(R.id.txtharga);

		lattitude = (EditText)findViewById(R.id.txtLat);

		longitude = (EditText)findViewById(R.id.txtLong);
		
		jenis = (RadioGroup)findViewById(R.id.jenis);
		
		status = (RadioGroup)findViewById(R.id.status);
		
		kosong = (RadioButton)findViewById(R.id.kosong);
		penuh = (RadioButton)findViewById(R.id.penuh);
		laki = (RadioButton)findViewById(R.id.laki);
		perempuan = (RadioButton)findViewById(R.id.perempuan);
		
		Button update = (Button)findViewById(R.id.btUpdate);
		Button btnShowLocation = (Button) findViewById(R.id.btCariLongLat);
		Button logout = (Button)findViewById(R.id.btLogout);
		
		username.requestFocus();
		
		//menerima intent yang dikirim
		 Intent i = this.getIntent();
		 tempUsername = i.getStringExtra("username");
		// username.setText(i.getStringExtra("username"));
		// password.setText(i.getStringExtra("password"));
		 
		 GetDataTask AsyncTampilData = new GetDataTask();
		 AsyncTampilData.execute();

		 //tombol simpan
		 update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 new SaveDataEdit().execute();
			}
		});
		 
		 //cari titik
		 btnShowLocation.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// buat class object dari GpsService

					gps = new GpsService(Dasboard.this);

					// dicek dulu apakah GPSnya idup

					if (gps.canGetLocation())

					{

						// ambil latitude dan longitude

						double getlatitude = gps.getLatitude();

						double getlongitude = gps.getLongitude();

						DecimalFormat df = new DecimalFormat("#.######");

						longitude.setText(String.valueOf(df.format(getlongitude)));

						lattitude.setText(String.valueOf(df.format(getlatitude)));
					// tampilkan make Toast

						Toast.makeText(getApplicationContext(),

								"Lokasi mu latitude: " + getlatitude + " Longitude : " + getlongitude, Toast.LENGTH_LONG).show();
					} else
					{
						// jika GPS tidak aktif
						gps.showSettingAlert();
					}
				}				
			});	 
		 
		 //tombol logout
		 logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				Intent back = new Intent(Dasboard.this,MainActivity.class);
				startActivity(back);
			}
		});
	}
	
	public class GetDataTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        	super.onPreExecute();
        	pDialog = new ProgressDialog(Dasboard.this);
        	pDialog.setMessage("Sedang Menampilkan Data...");
        	pDialog.setIndeterminate(false);
        	pDialog.setCancelable(true);
        	pDialog.show();
        	}

        protected String doInBackground(String... args) {
			//buat object dari json class
			UserFunctions userFunction = new UserFunctions(); 
			 JSONObject json = userFunction.getPemilik(tempUsername);
			 try {
					// products found
	                 // Getting Array of Products
	                 product = json.getJSONArray("product");
	                for (int i = 0; i < product.length(); i++) {
	                	JSONObject c = product.getJSONObject(i);
	                	// Storing each json item in variable
                        tampilData[0] = c.getString("username");
                        tampilData[1]= c.getString("password");
                        tampilData[2]= c.getString("nama");
                        tampilData[3]= c.getString("telepon");
                        tampilData[4]= c.getString("alamat");
                        tampilData[5]= c.getString("jumlah_kamar");
                        tampilData[6]= c.getString("harga");
                        tampilData[7]= c.getString("latitude");
                        tampilData[8]= c.getString("longitude");
                        tampilData[9]= c.getString("status");
                        tampilData[10]= c.getString("jenis");
	                }
			} catch (JSONException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
            return null;
        }

        protected void onPostExecute(String file_url) {

        	pDialog.dismiss();
        	runOnUiThread(new Runnable() {
        	public void run() {
        			username.setText(tampilData[0]);
        			password.setText(tampilData[1]);
        			nama.setText(tampilData[2]);
        			telepon.setText(tampilData[3]);
        			alamat.setText(tampilData[4]);
        			jmlKamar.setText(tampilData[5]);
        			harga.setText(tampilData[6]);
        			lattitude.setText(tampilData[7]);
        			longitude.setText(tampilData[8]);
        			if(tampilData[9].equals("Kosong")){
        				kosong.setChecked(true);
        			}else{
        				penuh.setChecked(true);
        			}
					if(tampilData[10].equals("Laki-laki")){
					        				laki.setChecked(true);
					        			}else{
					        				perempuan.setChecked(true);
					        			}
        			
        	}

        	});

        	}
    }
	
	
	
	//untuk edit
	 class SaveDataEdit extends AsyncTask<String, String, String> {
		 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Dasboard.this);
	            pDialog.setMessage("Sedang Menyimpan Data ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	 
	        /**
	         * Saving product
	         * */
	        protected String doInBackground(String... args) {
	        	String user = username.getText().toString();

				String pass = password.getText().toString();

				String nm = nama.getText().toString();

				String telp = telepon.getText().toString();

				String almt = alamat.getText().toString();

				String jml = jmlKamar.getText().toString();

				String hrg = harga.getText().toString();

				String lat = lattitude.getText().toString();

				String longi = longitude.getText().toString();
	        	
				String jns;
				if(((RadioGroup)findViewById(R.id.jenis)).getCheckedRadioButtonId()==R.id.laki){
					jns="Laki-laki";
				}else{
					jns="Perempuan";
				}
				
				String stat;
				if(((RadioGroup)findViewById(R.id.status)).getCheckedRadioButtonId()==R.id.kosong){
					stat="Kosong";
				}else{
					stat="Penuh";
				}
	        	 UserFunctions userFunction = new UserFunctions(); 
				 JSONObject json = userFunction.editPemilik(user, pass, nm, telp, almt, jml, hrg, lat, longi, stat, jns);
	           
	            // check json success tag
				 
				 try {

						if(json.getString("success")!= null){

							 hasil = json.getString("success"); 	

						}

					} catch (JSONException e) {

						// TODO: handle exception

						e.printStackTrace();
					}
	 
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	        	pDialog.dismiss();

	        	runOnUiThread(new Runnable() {

	        	public void run() {

	        		if(Integer.parseInt(hasil) == 1){ 

	                    Toast.makeText(Dasboard.this, "Data berhasil disimpan", 1).show(); 
	              
	                }else{ 

	                    Toast.makeText(Dasboard.this, "Data gagal disimpan", 1).show(); 

	                } 
	        	}

	        	}); 
	        }
	    }
	 
}
